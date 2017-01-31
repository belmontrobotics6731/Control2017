package com.belmontrobotics17.commands.gearmechanism;

import com.belmontrobotics17.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ToggleGearMechanism extends Command {

	private long startTime;
	private long runTime;
	
    public ToggleGearMechanism(long run) {
        // Use requires() here to declare subsystem dependencies
        this.runTime = run;
    	
    	requires(Robot.gearMechanism);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.gearMechanism.toggleSolenoids();
    	
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
    	Robot.gearMechanism.stopSolenoids();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.gearMechanism.stopSolenoids();
    }
}
