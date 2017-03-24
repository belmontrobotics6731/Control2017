package com.belmontrobotics17.commands.ropeclimbing;

import com.belmontrobotics17.Robot;
import com.belmontrobotics17.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

import com.belmontrobotics17.Robot;

/**
 *
 */
public class RopeControl extends Command {

    public RopeControl() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.ropeClimbing);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.oi.getLogitechButton(RobotMap.JOYSTICK_LIFT_PORT))
    		Robot.ropeClimbing.moveLift(-1.0);
    	else
    		Robot.ropeClimbing.stop();
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
