package com.belmontrobotics17.commands.vision;

import org.opencv.core.Mat;

import org.opencv.imgcodecs.Imgcodecs;

import com.belmontrobotics17.Robot;
import com.belmontrobotics17.RobotConstants;
import com.belmontrobotics17.subsystems.Drivetrain.DriveDistanceSource;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class MoveToTape2 extends Command {

	private double cx = 0.0;
	private double cy = 0.0;
	private double a1 = 0.0;
	private double a2 = 0.0;
	private double mVel = 0.0;
	private boolean lastForward = false;
	private boolean finished = false;
	private double startDistance = 0.0;
	private double startAngle = 0.0;
	
	private PIDController PIDControl = new PIDController(RobotConstants.DRIVE_PID_P, RobotConstants.DRIVE_PID_I, RobotConstants.DRIVE_PID_D, Robot.drivetrain.leftDriveEncoder, new MovePIDOutput());

	public MoveToTape2() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.vision);
		requires(Robot.drivetrain);
	}
	
	private void stopDrive() {
		this.PIDControl.reset();
		Robot.drivetrain.stop();
	}
	
	private void getValues()
	{
		NetworkTable table = NetworkTable.getTable("vision");
		
		this.cx = table.getNumber("centerX", RobotConstants.CAMERA_WIDTH/2.0);
		this.cy = table.getNumber("centerY", RobotConstants.CAMERA_HEIGHT/2.0);
		this.a1 = table.getNumber("area1", 0.0);
		this.a2 = table.getNumber("area2", 0.0);
	}
	
	private void driveDistance(double d)
	{
		Robot.drivetrain.leftDriveEncoder.reset();
		//this.rightDriveEncoder.reset();
		
		this.PIDControl.setSetpoint(d);
		this.PIDControl.setAbsoluteTolerance(RobotConstants.DRIVE_PID_TOLERANCE);
		this.PIDControl.enable();
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		this.stopDrive();

		// turn on lights
		Robot.vision.lights.en();
		
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.startDistance = 2.0 * NetworkTable.getTable("vision").getNumber("distance", 0.0);
		
		this.startAngle = Robot.drivetrain.gyro.getAngle();
		
		if(this.startDistance > 0.1)
			this.driveDistance(this.startDistance);
		else
			this.finished = true;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		//if(this.lastForward)
		//	this.finished = this.PIDControl.onTarget();
		//else
		//{
			this.getValues();
			
			//System.out.println("Left area: " + this.a1 + "  Right area: " + this.a2);
		
			if((this.PIDControl.onTarget()) || this.cy < RobotConstants.GEAR_PLACE_Y_THRESHOLD || (this.a1 + this.a2) > RobotConstants.GEAR_PLACE_A_THRESHOLD)
			{
				this.finished = true;
				//this.lastForward = true;
				
				//this.driveDistance(RobotConstants.LAST_MOVE_DISTANCE);
			}
		//}
	}
	
	 private class MovePIDOutput implements PIDOutput {
	    	
	    	@Override
	    	public void pidWrite(double output) {
	    		//drivePID(-output, -rightPIDFactor*output);
	    		getValues();
	    		
	    		double d1 = RobotConstants.TAPE_MOVE_ADJUST_K * cx;
	    		
	    		double d2 = 0.2 * RobotConstants.DRIVE_ADJUST_ANGLE_K * (Robot.drivetrain.gyro.getAngle() - startAngle);
	    		
	    		if(output > RobotConstants.MAX_PID_MVEL)
	    			output = RobotConstants.MAX_PID_MVEL;
	    		else if(output < -RobotConstants.MAX_PID_MVEL)
	    			output = -RobotConstants.MAX_PID_MVEL;
	    		
	    		Robot.drivetrain.drivePID(-output - d1 + d2, -output + d1 - d2);
	    	}
	   	}

	protected void backLeft() {
		Robot.drivetrain.drive(-0.05*this.mVel, -0.08*this.mVel);
	}

	protected void backRight() {
		Robot.drivetrain.drive(-0.8*this.mVel, -0.05*this.mVel);
	}

	// returns distance in meters
	protected double toDistance(double area1, double area2) {
		// 1 / qrt(avg_area) = 0.031 * (distance) + 2.038X10^-3
		// distance = (1 / qrt(avg_area) - 2.038X10^-3) / 0.031
		return (1 / Math.sqrt((area1 + area2) / 2) - 0.002038) / 0.031;
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return this.finished;
	}

	// Called once after isFinished returns true
	protected void end() {
		this.stopDrive();
		Robot.vision.lights.dis();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		this.stopDrive();
		Robot.vision.lights.dis();
	}
}
