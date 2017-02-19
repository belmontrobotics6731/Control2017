package com.belmontrobotics17.commands.vision;

import com.belmontrobotics17.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveToTape extends Command {

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
    	
    	Mat frame = Robot.vision.getLastFrame();
    	
    	// Locate tape
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
