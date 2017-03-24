package com.belmontrobotics17.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

import com.belmontrobotics17.RobotConstants;
import com.belmontrobotics17.commands.Wait;
import com.belmontrobotics17.commands.drivetrain.DriveDistanceCmd;
import com.belmontrobotics17.commands.drivetrain.DriveSafe;
import com.belmontrobotics17.commands.drivetrain.TurnAngleCmd;
import com.belmontrobotics17.commands.drivetrain.TurnAngleCmd2;
import com.belmontrobotics17.commands.vision.CenterToTape2;
import com.belmontrobotics17.commands.vision.KeepTapeCentered;
import com.belmontrobotics17.commands.vision.MoveToTape;

/**
 *
 */
public class TestAuto extends CommandGroup {

    public TestAuto() {
        
    	//addSequential(new DriveDistanceCmd(1.0));
    	
    	//addSequential(new TurnAngleCmd(-RobotConstants.SPIKE_OFFSET_ANGLE));
    	
    	//addSequential(new DriveDistanceCmd(RobotConstants.TEST_MOVE, 1.0));
    	//addSequential(new Wait(500));
    	//addSequential(new TurnAngleCmd(RobotConstants.TEST_TURN));
    	//addSequential(new CenterToTape2());
    	
    	//addSequential(new TurnAngleCmd(90.0, 5000));
    	//addSequential(new DriveDistanceCmd(1.0, 4000));
    	//addSequential(new TurnAngleCmd(-60.0, 5000));
    	//addSequential(new DriveDistanceCmd(72.0 * 0.0254, 5000));
    	
    	addSequential(new KeepTapeCentered());
    	
    	/*addSequential(new DriveDistanceCmd(0.5, 1.0));
    	addSequential(new Wait(500));
    	addSequential(new TurnAngleCmd2(-1.25));
    	addSequential(new Wait(500));
    	addSequential(new DriveDistanceCmd(1.0, 1.0));
    	addSequential(new Wait(500));
    	addSequential(new TurnAngleCmd2(0.8));
    	addSequential(new Wait(500));
    	addSequential(new DriveDistanceCmd(1.0, 1.0));*/
    }
}
