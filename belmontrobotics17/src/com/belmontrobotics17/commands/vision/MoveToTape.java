package com.belmontrobotics17.commands.vision;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import com.belmontrobotics17.Robot;
import com.belmontrobotics17.RobotConstants;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveToTape extends Command {
	
	float cX;
	float width = 1280;
	double sensitivity = 0.5;
	boolean back = false;
    public MoveToTape() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.vision);
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// turn off motors
    	Robot.drivetrain.stop();
    	
    	// turn on lights
    	Robot.vision.lights.en();
    	
    	back = false;
    	// Locate tape
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	// area left and right
    	float al = 0, ar = 0;
    	
    	if (back)
    	{
    		// minimum difference in area until stop backing
    		float t = 10;
    		// minimum area to stop backing
    		float t3 = 300;
    		if(al > ar)
    		{
    			backRight();
    			
    		}
    		else
    		{
    			backLeft();
    			
    		}
    		if(Math.abs(al - ar) < t || al + ar < t3)
    		{
    			back = false;
    		}	
    	}
    	
    	else
    	{
    		// area to stop moving forward
    		float t1 = 500;
    		// difference in area to back up
    		float t2 = 20;
    		if (al + ar > t1)
    		{
    			// move forward
    			double turn = sensitivity * (cX-width/2) / (width/2);
            	Robot.drivetrain.drive(-turn, turn);
    		}
    		// difference in area to execute backing
    		else if (Math.abs(al - ar) > t2){
    			back = true;    	
    		}
    	}
    }
    protected void backLeft()
    {
    	Robot.drivetrain.drive(-0.3, -0.4);
    }
    protected void backRight()
    {
    	Robot.drivetrain.drive(-0.4, -0.3);
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
