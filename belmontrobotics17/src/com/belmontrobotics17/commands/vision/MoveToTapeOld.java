package com.belmontrobotics17.commands.vision;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import com.belmontrobotics17.Robot;
import com.belmontrobotics17.RobotConstants;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class MoveToTapeOld extends Command {

	double cX;
	double al;
	double ar;
	double width = 1280;
	double sensitivity = 0.0;
	double mVel = 0.0;
	boolean back = false;

	public MoveToTapeOld() {
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
		
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//NetworkTable.getTable("vision").putBoolean("requestpoint", true);
		
		//while(!NetworkTable.getTable("vision").putBoolean("requestpoint", true)) { }

		this.back = false;
		
		// Locate tape
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// Centroid
		this.cX = NetworkTable.getTable("vision").getNumber("centerX", this.width/2.0);
		
		// area left and right
		this.al = NetworkTable.getTable("vision").getNumber("area1", 0.0);
		this.ar = NetworkTable.getTable("vision").getNumber("area2", 0.0);
		
		System.out.println("Left area: " + this.al + "  Right area: " + this.ar);
		
		if (this.al < 100.0 && this.ar < 100.0) {
			Robot.drivetrain.drive(-this.mVel*0.05, this.mVel*0.05);
		} else {
		
			if (this.back) {
				// minimum difference in area until stop backing
				double t = 10.0;
				// distance to stop backing in meters
				double t3 = 2.0;
				if (Math.abs(this.al - this.ar) < t || this.toDistance(this.al, this.ar) < t3) {
					this.back = false;
				}
				if (this.al > this.ar) {
					this.backRight();

				} else {
					this.backLeft();

				}
			}

			else {
				// distance to stop moving forward
				double t1 = 0.2;
				// difference in area to back up
				double t2 = 20.0;
				if (this.toDistance(this.al, this.ar) > t1) {
					// move forward
					double turn = this.sensitivity * (this.cX - this.width / 2.0) / (this.width / 2.0);
					Robot.drivetrain.drive(-turn, turn);
				}
				// difference in area to execute backing
				else if (Math.abs(this.al - this.ar) > t2) {
					this.back = true;
				}
			}}
		
	}

	protected void backLeft() {
		Robot.drivetrain.drive(-0.05*this.mVel, -0.08*this.mVel);
	}

	protected void backRight() {
		Robot.drivetrain.drive(-0.8*this.mVel, -0.05*this.mVel);
	}

	// returns distance in meters
	protected double toDistance(double area1, double area2) {
		// 1 / qrt(avg_area) = 0.031 * (distance) + 2.038X10^-3
		// distance = (1 / qrt(avg_area) - 2.038X10^-3) / 0.031
		return (1 / Math.sqrt((area1 + area2) / 2) - 0.002038) / 0.031;
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.drivetrain.stop();
		Robot.vision.lights.dis();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.drivetrain.stop();
		Robot.vision.lights.dis();
	}
}
