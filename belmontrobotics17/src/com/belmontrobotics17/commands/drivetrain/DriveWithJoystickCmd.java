package com.belmontrobotics17.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.belmontrobotics17.RobotMap;
import com.belmontrobotics17.RobotVars;
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
    	double fb = Robot.oi.getLogitechJoystick(RobotMap.JOYSTICK_FB_PORT);
    	double lr = Robot.oi.getLogitechJoystick(RobotMap.JOYSTICK_LR_PORT);
    	double sens = (1.0 - Robot.oi.getLogitechJoystick(RobotMap.JOYSTICK_SENS_PORT)) / 2.0;
    	boolean fast = Robot.oi.getLogitechButton(RobotMap.JOYSTICK_FASTTURN_BUTTON);
    	
    	fb = Math.pow(Math.abs(fb), 1) * Math.signum(fb);
    	lr = Math.pow(Math.abs(lr),  1) * Math.signum(lr);
    	
    	if(Robot.oi.getLogitechButton(RobotMap.JOYSTICK_NOTURN_BUTTON))
    		lr = 0.0;
    	
    	SmartDashboard.putNumber("Throttle", fb);
    	SmartDashboard.putNumber("Turn", lr);
    	
    	Robot.drivetrain.driveCheesy(fb, lr, fast, sens);
    	
    	//Robot.ntable.putNumber("Throttle", fb);
    	//Robot.ntable.putNumber("Turn", lr);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
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
