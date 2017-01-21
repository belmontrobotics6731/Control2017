package com.belmontrobotics17.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;


import com.belmontrobotics17.Robot;
import com.belmontrobotics17.RobotVars;

/**
 *
 */
public class DriveDistanceCmd extends Command {

	private double distance;
	
    public DriveDistanceCmd(double distance) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
    
        this.distance = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	//Robot.drivetrain.driveDistancePID(this.distance, RobotVars.DRIVE_PID_TOLERANCE);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        //return Robot.drivetrain.driveDistancePID.onTarget();
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
