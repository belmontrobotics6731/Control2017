package com.belmontrobotics17.commands.vision;

import com.belmontrobotics17.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class VisionTest extends Command {

    public VisionTest() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.vision);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.vision.lights.dis();
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
