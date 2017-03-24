package com.belmontrobotics17.commands.drivetrain;

import com.belmontrobotics17.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnAngleCmd2 extends Command {

	double angle;
	long startTime;
	
    public TurnAngleCmd2(double ang) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
        
        this.angle = ang;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(this.angle > 0.0)
    		Robot.drivetrain.drive(0.3, -0.3);
    	else
    		Robot.drivetrain.drive(-0.3,  0.3);
    
    	this.startTime = System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (double)(System.currentTimeMillis() - this.startTime) > 400.0 * Math.abs(this.angle);
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
