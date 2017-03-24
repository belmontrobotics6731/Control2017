package com.belmontrobotics17.commands.auto;

import com.belmontrobotics17.commands.drivetrain.DriveDistanceCmd;
import com.belmontrobotics17.commands.drivetrain.DriveSafe;
import com.belmontrobotics17.commands.drivetrain.TurnAngleCmd;
import com.belmontrobotics17.commands.vision.KeepTapeCentered;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SidesAuto extends CommandGroup {

	// x: starting offset from center of field (to the left is negative)
    public SidesAuto(double x) {
    	
    	double s = 1.03386; // hexagon side length
    	double d = 96.0 * 0.0254; // distance from starting line to hexagon side
    	double h = 28.0 * 0.0254; // robot length
    	double r3 = Math.sqrt(3.0);
    	
    	double a1 = d + (r3 + 3) * s * 0.25 - Math.abs(x) / r3 - h * 0.5;
    	double a2 = (Math.abs(x) - 0.75 * s) * 2.0 / r3 - h * 0.5;
    	
    	System.out.println("A2: " + a2);
    	
    	//addSequential(new DriveSafe(0.3, 0.3, 1000));
    	addSequential(new DriveDistanceCmd(10.0, 2600));
    	addSequential(new TurnAngleCmd(Math.signum(x) * -60.0, 4000));
    	addSequential(new KeepTapeCentered());
    	//addSequential(new DriveDistanceCmd(a2, 4000));
    }
}
