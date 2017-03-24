package com.belmontrobotics17.commands.drivetrain;

import com.belmontrobotics17.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Brake extends Command {

	private long startTime;
	private long runTime;
	
    public Brake(long run) {
    	this.runTime = run;
    	
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.stop();
    
    	this.startTime = System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (System.currentTimeMillis() > this.startTime + this.runTime);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
