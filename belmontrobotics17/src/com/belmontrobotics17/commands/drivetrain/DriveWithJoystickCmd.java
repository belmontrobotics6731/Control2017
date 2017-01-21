package com.belmontrobotics17.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;

import com.belmontrobotics17.RobotMap;
import com.belmontrobotics17.Robot;

/**
 *
 */
public class DriveWithJoystickCmd extends Command {
	
    public DriveWithJoystickCmd() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.driveCheesy(Robot.oi.getLogitechJoystick(RobotMap.JOYSTICK_FB_PORT), Robot.oi.getLogitechJoystick(RobotMap.JOYSTICK_LR_PORT), Robot.oi.getLogitechButton(RobotMap.JOYSTICK_FASTTURN_BUTTON));
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
