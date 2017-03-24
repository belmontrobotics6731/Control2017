package com.belmontrobotics17.commands.vision;

import com.belmontrobotics17.Robot;
import com.belmontrobotics17.RobotConstants;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class CenterToTape extends Command {

	// translational offset
	private double e_offset;
	
	// angle through which the circle sector sweeps through
	private double sector_angle;
	
	private boolean angleAdjusting = false;
	private boolean moving = false;
	private boolean finished = false;
	
	// distance for the left wheel to go through during the first semicircle
	private double leftDistance;
	
	// factor to turn (for right wheels)
	private double rightFactor;
	
	// 0 is not set yet, 1 is first sector, 2 is left sector
	private int sector = 0;
	
    public CenterToTape() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
        requires(Robot.vision);
    }
    
    // Check if it has to move, and if it does do so
    private void move()
    {
    	// Get the estimated translational offset from the raspberry pi
       	this.e_offset = NetworkTable.getTable("vision").getNumber("offset", 0.0);
       
       	this.sector_angle = 90.0;
       	
        this.leftDistance = (0.5 * this.e_offset - RobotConstants.AXLE_RADIUS) * 0.5 * Math.PI;
        this.rightFactor = (0.5 * this.e_offset + RobotConstants.AXLE_RADIUS) / (0.5 * this.e_offset - RobotConstants.AXLE_RADIUS);
        
        if(this.e_offset < RobotConstants.CENTER_TO_TAPE_TOLERANCE && this.e_offset > -RobotConstants.CENTER_TO_TAPE_TOLERANCE)
       		this.finished = true;
       	else
       	{
       		this.sector = 1;
       		this.finished = false;
       		this.angleAdjusting = false;
       		this.moving = true;
       		
       		Robot.drivetrain.driveDistancePID(this.leftDistance, this.rightFactor, RobotConstants.DRIVE_PID_TOLERANCE);
        }
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.stop();
    	
    	Robot.vision.lights.en();
    
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	this.move();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(this.finished == true || this.sector == 0)
    		return;
    	
    	if(this.angleAdjusting)
    	{
    		if(Robot.drivetrain.turnAnglePID.onTarget())
    		{
    			this.angleAdjusting = false;
    			Robot.drivetrain.turnAnglePID.reset();
    			Robot.drivetrain.stop();
    		}
    	}
    	else if(!this.moving || Robot.drivetrain.driveDistancePID.onTarget())
    	{
    		Robot.drivetrain.driveDistancePID.reset();
        	Robot.drivetrain.stop();
    		
        	double ang = Robot.drivetrain.gyro.getAngle();

        	if(this.sector == 1)
        	{
        		if(ang > RobotConstants.SPIKE_OFFSET_ANGLE + this.sector_angle + RobotConstants.CENTER_TO_TAPE_ANGLE_TOLERANCE || ang < RobotConstants.SPIKE_OFFSET_ANGLE + this.sector_angle - RobotConstants.CENTER_TO_TAPE_ANGLE_TOLERANCE)
        		{
        			Robot.drivetrain.turnAnglePID(-ang + RobotConstants.SPIKE_OFFSET_ANGLE, RobotConstants.TURN_PID_TOLERANCE);
        			this.angleAdjusting = true;
        			this.moving = false;
        		}
        		else
        		{
        			this.sector = 2;
        			Robot.drivetrain.driveDistancePID(this.leftDistance*this.rightFactor, 1.0 / this.rightFactor, RobotConstants.DRIVE_PID_TOLERANCE);
        			this.moving = true;
        		}
        	}
        	else if(this.sector == 2)
        	{
        		if(ang > RobotConstants.SPIKE_OFFSET_ANGLE + RobotConstants.CENTER_TO_TAPE_ANGLE_TOLERANCE || ang < RobotConstants.SPIKE_OFFSET_ANGLE - RobotConstants.CENTER_TO_TAPE_ANGLE_TOLERANCE)
        		{
        			Robot.drivetrain.turnAnglePID(ang - RobotConstants.SPIKE_OFFSET_ANGLE, RobotConstants.TURN_PID_TOLERANCE);
        			this.angleAdjusting = true;
        			this.moving = false;
        		}
        		else
        			this.move();
        	}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return this.finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.stop();
    	Robot.vision.lights.dis();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drivetrain.driveDistancePID.reset();
    	Robot.drivetrain.stop();
    	Robot.vision.lights.dis();
    }
}
