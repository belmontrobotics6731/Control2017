package com.belmontrobotics17.commands.auto;

import com.belmontrobotics17.commands.drivetrain.DriveDistanceCmd;
import com.belmontrobotics17.commands.drivetrain.DriveSafe;
import com.belmontrobotics17.commands.drivetrain.TurnAngleCmd;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterAuto extends CommandGroup {

    public CenterAuto() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	
    	//addSequential(new DriveDistanceCmd(0.5, 1.0));
    	//addSequential(new TurnAngleCmd(0.5*Math.PI));
    	//addSequential(new DriveDistanceCmd(1.0, 1.0));
    	//addSequential(new TurnAngleCmd(-0.5*Math.PI));
    	//addSequential(new DriveDistanceCmd(0.5, 1.0));
    	
    	//addSequential(new DriveSafe(0.3, 0.3, 1000));
    	addSequential(new DriveDistanceCmd(3.0, 5000));
    }
}
