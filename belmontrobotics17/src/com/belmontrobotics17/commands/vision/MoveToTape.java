package com.belmontrobotics17.commands.vision;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import com.belmontrobotics17.Robot;
import com.belmontrobotics17.RobotConstants;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * This class moves the robot forward to place the gear after it has been centered to the spike (and is facing it correctly)
 */
public class MoveToTape extends Command {

	private double cx;
	private double cy;
	private double al;
	private double ar;
	private double e_offset;
	private double e_distance;
	private double degrees_per_pixel;
	private boolean angleAdjusting = false;
	private boolean firstAngleAdjust = true;
	private double pixelOffset;
	private double adjustAngle = 0.0;
	private double padjustAngle = 0.0;
	private double k_adjustError = 1.0;
	private double mVel = 0.1;

	public MoveToTape() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.vision);
		requires(Robot.drivetrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		// turn off motors
		Robot.drivetrain.stop();

		// turn on lights
		Robot.vision.lights.en();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Robot.drivetrain.drive(this.mVel, this.mVel);
	}
	
	private void getValues()
	{
		NetworkTable table = NetworkTable.getTable("vision");
		
		this.cx = table.getNumber("centerX", RobotConstants.CAMERA_WIDTH / 2.0);
		this.cy = table.getNumber("centerY", RobotConstants.CAMERA_HEIGHT / 2.0);
		
		this.al = table.getNumber("area1", 0.0);
		this.ar = table.getNumber("area2", 0.0);
		
		this.e_offset = table.getNumber("offset", 0.0);
		this.e_distance = table.getNumber("distance", 0.0);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// Centroid
		this.getValues();
		
		double coff = this.cx - RobotConstants.CAMERA_WIDTH / 2.0;
		
		if(this.angleAdjusting)
		{
			if(Robot.drivetrain.turnAnglePID.onTarget() && Robot.drivetrain.gyro.getRate() < RobotConstants.TURN_PID_STOP_OUTPUT_THRESH)
			{
				this.angleAdjusting = false;
				Robot.drivetrain.stop();
				Robot.drivetrain.turnAnglePID.reset();
				
				try {
					Thread.sleep(400);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				//this.getValues();
				
				//this.degrees_per_pixel = this.adjustAngle / (this.pixelOffset - this.cx);
			}
		}
		else if(coff > RobotConstants.MOVE_TO_TAPE_CENTER_CORRECTION_THRESHOLD || coff < - RobotConstants.MOVE_TO_TAPE_CENTER_CORRECTION_THRESHOLD)
		{
			this.pixelOffset = coff;
			
			this.padjustAngle = this.adjustAngle;
			this.adjustAngle = Math.toDegrees(Math.atan(this.e_offset / this.e_distance));
			
			if(this.firstAngleAdjust)
				this.firstAngleAdjust = false;
			else if(this.padjustAngle > 0.001 || this.padjustAngle < -0.001)
			{
				this.k_adjustError = (this.padjustAngle + this.adjustAngle) / this.padjustAngle;
				this.adjustAngle = this.k_adjustError * this.adjustAngle;
			}
			
			Robot.drivetrain.stop();
			Robot.drivetrain.turnAnglePID(-this.adjustAngle, RobotConstants.TURN_PID_TOLERANCE);
			this.angleAdjusting = true;
		}
		else
		{
			Robot.drivetrain.drive(this.mVel, this.mVel);
			this.firstAngleAdjust = true;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return (!this.angleAdjusting && this.cy < RobotConstants.GEAR_PLACE_Y_THRESHOLD && (this.al + this.ar) > RobotConstants.GEAR_PLACE_A_THRESHOLD);
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.drivetrain.stop();
		Robot.vision.lights.dis();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.drivetrain.stop();
		Robot.vision.lights.dis();
	}
}
