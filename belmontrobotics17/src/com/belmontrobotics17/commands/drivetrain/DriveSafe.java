package com.belmontrobotics17.commands.drivetrain;

import com.belmontrobotics17.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Drives, and stops if it hits a wall when going forward
 */
public class DriveSafe extends Command {

	boolean hit = false;
	long startTime;
	long timeout;
	
	double r;
	double l;
	
    public DriveSafe(double l, double r, long timeout) {
    	this.timeout = timeout;
    	
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.drivetrain);
    	requires(Robot.vision);
    	
    	this.r = r;
    	this.l = l;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	this.startTime = System.currentTimeMillis();
    
    	Robot.drivetrain.drive(this.l, this.r);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//if(Robot.vision.distanceSensor.getDistance() < 0.1)
    	//	this.hit = true;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (System.currentTimeMillis() - this.startTime) > this.timeout || this.hit;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drivetrain.stop();
    }
}
