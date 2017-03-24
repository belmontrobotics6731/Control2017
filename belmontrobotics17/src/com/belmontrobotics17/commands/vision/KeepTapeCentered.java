package com.belmontrobotics17.commands.vision;

import com.belmontrobotics17.Robot;
import com.belmontrobotics17.RobotConstants;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class KeepTapeCentered extends Command {

	double cx = 0.01;
	double cy;
	double a1;
	double a2;
	
	boolean lastMove = false;
	double lastMoveAngle;
	long lastMoveStartTime;
	long lastMoveTime = 400;
	
	boolean finished = false;
	
	int found = 0;
	
	double w = 480.0;
	double h = 270.0;
	
    public KeepTapeCentered() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrain);
    	requires(Robot.vision);
    }
    
    private void stop()
    {
    	Robot.drivetrain.stop();
    	Robot.vision.lights.dis();
    }

    private void getValues()
	{
		NetworkTable table = NetworkTable.getTable("vision");
		
		//this.w = table.getNumber("cwidth", this.w);
		//this.h = table.getNumber("cheight", this.h);
		this.cx = table.getNumber("centerX", this.w / 2.0);
		this.cy = table.getNumber("centerY", this.h / 2.0);
		this.a1 = table.getNumber("area1", 0.0);
		this.a2 = table.getNumber("area2", 0.0);
		this.found = (int)table.getNumber("found", 0.0);
	}
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	this.lastMove = false;
    	this.finished = false;
    	this.found = 0;
    	
    	System.out.println("done");
    	
    	Robot.drivetrain.stop();

		// turn on lights
		Robot.vision.lights.en();
		
		/*try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double v = -0.25;
    	double mv = 0.25;
    	double k = 2.0;
    	double k2 = 0.02;
    	
    	if(this.lastMove)
    	{
    		double d = 0.01 * (Robot.drivetrain.gyro.getAngle() - this.lastMoveAngle);
    		
    		if(System.currentTimeMillis() - this.lastMoveStartTime > this.lastMoveTime)
    			this.finished = true;
    		else
    			Robot.drivetrain.drive(v + d, v - d);
    	}
    	
    	this.getValues();
    	
    	double r = Robot.drivetrain.gyro.getRate();
    	
    	double e = k * (this.cx - this.w / 2.0) / (this.w / 2.0);
    	
    	double e2 = (Math.abs(e) - Math.abs(r) * k2);
    	
    	if(e2 < 0.0)
    		e2 = 0.0;
    	
    	e = Math.signum(e) * e2;
    	
    	System.out.println(this.cx);
    	
    	if(e > mv)
    		e = mv;
    	else if(e < -mv)
    		e = -mv;
    	
    	if(this.found == 2)
    	{
    		Robot.drivetrain.drive(v - e, v + e);
    	}
    	else if(this.cy < 100.0)
    	{
    		this.lastMove = true;
    		this.lastMoveAngle = Robot.drivetrain.gyro.getAngle();
    		this.lastMoveStartTime = System.currentTimeMillis();
    	}
    	else
    	{
    		e = 0.0;
    		Robot.drivetrain.stop();
    	}
    	
    	SmartDashboard.putNumber("e", e);
    	SmartDashboard.putNumber("cx", this.cx);
    	SmartDashboard.putNumber("cy", this.cy);
    	SmartDashboard.putNumber("gyro rate", r);
    	SmartDashboard.putNumber("number of tapes found", this.found);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return this.finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	this.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.stop();
    }
}
