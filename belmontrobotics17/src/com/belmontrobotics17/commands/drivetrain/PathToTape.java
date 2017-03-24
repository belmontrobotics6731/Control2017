package com.belmontrobotics17.commands.drivetrain;

import java.util.Arrays;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Moves to gear spike using path planning
 */
public class PathToTape extends CommandGroup {

	// offset, in meters, from the center of the starting line (to the left is negative, to the right is positive)
	double offset;
	
    public PathToTape(double offsetX) {
        this.offset = offsetX;
        
        double x = this.offset; // starting offset from center of field
        
        double m = 0.4; // initial forward distance
        double d = 2.0; // distance from back of field to first side of the hexagon
        double k = 1.0; // length in front of spike to stop
        double s = 1.2; // side length of hexagon
        
        double r3 = Math.sqrt(3.0);
        
        double [] D = new double[3];
        
        double h = d + s*r3*0.25 - k*0.5 - m;
        double w1 = s*0.75 + k*r3*0.5 + x;
        double w2 = s*0.75 + k*r3*0.5 - x;
        
        // d1
        D[0] = Math.sqrt(h*h + w1*w1);
        
        // d3
        D[2] = Math.sqrt(h*h + w2*w2);
        
        // d2
        D[1] = Math.sqrt(x * x + (d - m - k) * (d - m - k));
        
        addSequential(new DriveDistanceCmd(m, 2000));
        
        if(D[0] >= D[1] && D[0] >= D[2])
        {
        	double a = Math.toDegrees(Math.atan(Math.abs(h / w1)));
        	
        	addSequential(new TurnToAngleCmd(-90.0 + a));
        	addSequential(new DriveDistanceCmd(D[0], 4000));
        	addSequential(new TurnToAngleCmd(60.0));
        }
        else if(D[2] >= D[1] && D[2] >= D[0])
        {
        	double a = Math.toDegrees(Math.atan(Math.abs(h / w2)));
        	
        	addSequential(new TurnToAngleCmd(90.0 - a));
        	addSequential(new DriveDistanceCmd(D[2], 4000));
        	addSequential(new TurnToAngleCmd(-60.0));
        }
        else
        {
        	double a = Math.toDegrees(Math.atan((d - m - k) / x));
        	
        	addSequential(new TurnToAngleCmd(Math.signum(a) * 90.0 - a));
        	addSequential(new DriveDistanceCmd(D[1], 4000));
        	addSequential(new TurnToAngleCmd(60.0));
        }
    }
}
