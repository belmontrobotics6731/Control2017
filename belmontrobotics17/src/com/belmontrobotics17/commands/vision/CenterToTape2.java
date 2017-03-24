package com.belmontrobotics17.commands.vision;

import com.belmontrobotics17.Robot;
import com.belmontrobotics17.RobotConstants;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class CenterToTape2 extends Command {

	private double e_offset = 0.0;
	private double pe_offset = 0.0;
	private double e_distance = 0.0;
	private boolean first_move = true;
	private double k_error = 1.0;
	private boolean finished = false;
	private double turn_angle = 0.0;
	private double start_angle = 0.0;
	/*private double pixelOffset;
	private boolean angleAdjusting = false;
	private double padjustAngle;
	private double adjustAngle;
	private double k_adjustError = 1.0;
	private boolean firstAngleAdjust = true;
	private boolean continueAngleAdjusting = false;*/
	
	// for testing
	private int move_index = 0;
	private double [] test_offsets = {0.6, 0.2, -0.1, 0.001};
	
	// 0 is none yet, 1 is rotating to moving position, 2 is moving, 3 is rotating backz
	private int phase = 0;
	
    public CenterToTape2() {
    	requires(Robot.drivetrain);
        requires(Robot.vision);
    }
    
    private void stopDrive()
    {
    	Robot.drivetrain.turnAnglePID.reset();
    	Robot.drivetrain.driveDistancePID.reset();
		Robot.drivetrain.stop();
    }
    
    private void move()
    {
    	//this.angleAdjusting = false;
    	//this.firstAngleAdjust = true;
    	
    	try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	this.pe_offset = this.e_offset;
    	this.e_offset = NetworkTable.getTable("vision").getNumber("offset", this.test_offsets[this.move_index++]);
    	//this.e_distance = NetworkTable.getTable("vision").getNumber("distance", 0.0);
    	
    	if(this.first_move)
			this.first_move = false;
		else if(this.pe_offset > 0.001 || this.pe_offset < -0.001)
		{
			this.k_error = (this.pe_offset + this.e_offset) / this.pe_offset;
			this.e_offset = this.k_error * this.e_offset;
		}
    	
    	// Adjust number
    	this.turn_angle = Math.toDegrees(Math.atan(1.0 / this.e_offset));
    	
    	if(this.e_offset < RobotConstants.CENTER_TO_TAPE_TOLERANCE && this.e_offset > -RobotConstants.CENTER_TO_TAPE_TOLERANCE)
    		this.finished = true;
    	else
    	{
    		this.phase = 1;
    		// TODO: Turn the correct angle
    		Robot.drivetrain.turnToAnglePID(this.start_angle + this.turn_angle, RobotConstants.TURN_PID_TOLERANCE);
    	}
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.stop();
    	
    	Robot.vision.lights.en();
    	
    	this.start_angle = Robot.drivetrain.gyro.getAngle();
    	
    	this.move();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	/*if(this.angleAdjusting)
		{
			if(Robot.drivetrain.turnAnglePID.onTarget())
			{
				this.angleAdjusting = false;
				Robot.drivetrain.stop();
				Robot.drivetrain.turnAnglePID.reset();
				this.continueAngleAdjusting = true;
				
				//this.getValues();
				
				//this.degrees_per_pixel = this.adjustAngle / (this.pixelOffset - this.cx);
			}
		}
    	else */if(this.phase == 1 && Robot.drivetrain.turnAnglePID.onTarget() && Robot.drivetrain.gyro.getRate() < RobotConstants.TURN_PID_STOP_OUTPUT_THRESH)
    	{
    		this.stopDrive();
    		this.phase = 2;
    		
    		Robot.drivetrain.driveDistancePID(-this.e_offset / Math.cos(Math.toRadians(this.turn_angle)), 1.0, RobotConstants.DRIVE_PID_TOLERANCE);
    	}
    	else if(this.phase == 2 && Robot.drivetrain.driveDistancePID.onTarget() && Robot.drivetrain.gyro.getRate() < RobotConstants.PID_STOP_OUTPUT_THRESH)
    	{
    		this.stopDrive();
    		this.phase = 3;
    		
    		Robot.drivetrain.turnToAnglePID(this.start_angle, RobotConstants.TURN_PID_TOLERANCE);
    	}
    	else if(this.phase == 3 && Robot.drivetrain.turnAnglePID.onTarget() && Robot.drivetrain.gyro.getRate() < RobotConstants.TURN_PID_STOP_OUTPUT_THRESH)
    	{
    		/*try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    		
    		double cx = NetworkTable.getTable("vision").getNumber("centerX", RobotConstants.CAMERA_WIDTH / 2.0);
    		
    		double coff = cx - RobotConstants.CAMERA_WIDTH / 2.0;
    		
    		if(coff > RobotConstants.MOVE_TO_TAPE_CENTER_CORRECTION_THRESHOLD || coff < - RobotConstants.MOVE_TO_TAPE_CENTER_CORRECTION_THRESHOLD)
    		{
    			this.pixelOffset = coff;
    			
    			this.padjustAngle = this.adjustAngle;
    			this.adjustAngle = Math.atan(this.e_offset / this.e_distance);
    			
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
    			this.continueAngleAdjusting = false;*/
    			this.stopDrive();
    			this.move();
    		//}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return this.finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.vision.lights.dis();
    	this.stopDrive();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.vision.lights.dis();
    	this.stopDrive();
    }
}
