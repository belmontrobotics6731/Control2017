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
		int al = 0, ar = 0;
		// cannot find the tapes
		if (al < 100 && ar < 100) {
			Robot.drivetrain.drive(-0.5, 0.5);
		} else {

			if (back) {
				// minimum difference in area until stop backing
				float t = 10;
				// distance to stop backing in meters
				float t3 = 4;
				if (al > ar) {
					backRight();

				} else {
					backLeft();

				}
				if (Math.abs(al - ar) < t || toDistance(al, ar) < t3) {
					back = false;
				}
			}

			else {
				// distance to stop moving forward
				float t1 = 0.2f;
				// difference in area to back up
				float t2 = 20;
				if (toDistance(al, ar) > t1) {
					// move forward
					double turn = sensitivity * (cX - width / 2) / (width / 2);
					Robot.drivetrain.drive(-turn, turn);
				}
				// difference in area to execute backing
				else if (Math.abs(al - ar) > t2) {
					back = true;
				}
			}
		}
	}

	protected void backLeft() {
		Robot.drivetrain.drive(-0.3, -0.4);
	}

	protected void backRight() {
		Robot.drivetrain.drive(-0.4, -0.3);
	}

	// returns distance in meters
	protected double toDistance(int area1, int area2) {
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
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
