package com.belmontrobotics17.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;


import com.belmontrobotics17.Robot;
import com.belmontrobotics17.RobotConstants;
import com.belmontrobotics17.RobotPrefs;

/**
 *
 */
public class DriveDistanceCmd extends Command {

	private double distance;
	private long timeout;
	private long startTime;
	
    public DriveDistanceCmd(double distance, long timeout) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
    
        this.distance = distance;
        this.timeout = timeout;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.driveDistancePID.reset();
    	Robot.drivetrain.driveDistancePID(this.distance, 1.0, RobotConstants.DRIVE_PID_TOLERANCE);
    
    	this.startTime = System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.printEncodersToNetworkTables();
    	Robot.drivetrain.printGyroToNetworkTables();
    	//System.out.println("Ting");
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Robot.drivetrain.driveDistancePID.onTarget() && Robot.drivetrain.leftDriveEncoder.getRate() < RobotConstants.PID_STOP_OUTPUT_THRESH) || (System.currentTimeMillis() - this.startTime) > this.timeout;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.driveDistancePID.reset();
    	Robot.drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drivetrain.driveDistancePID.reset();
    	Robot.drivetrain.stop();
    }
}
