package com.belmontrobotics17.commands.drivetrain;

import com.belmontrobotics17.Robot;
import com.belmontrobotics17.RobotConstants;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnToAngleCmd extends Command {

	private double angle;
	
    public TurnToAngleCmd(double angle) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
        
        this.angle = angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.turnAnglePID.reset();
    	Robot.drivetrain.turnToAnglePID(this.angle, RobotConstants.TURN_PID_TOLERANCE);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.printGyroToNetworkTables();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Robot.drivetrain.turnAnglePID.onTarget() && Robot.drivetrain.gyro.getRate() < RobotConstants.TURN_PID_STOP_OUTPUT_THRESH;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.turnAnglePID.reset();
    	Robot.drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drivetrain.turnAnglePID.reset();
    	Robot.drivetrain.stop();
    }
}
