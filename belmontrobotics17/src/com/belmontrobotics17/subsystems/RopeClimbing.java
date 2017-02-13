package com.belmontrobotics17.subsystems;

import com.belmontrobotics17.RobotMap;
import com.belmontrobotics17.commands.ropeclimbing.RopeControl;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class RopeClimbing extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private VictorSP lift_motor = new VictorSP(RobotMap.LIFT_MOTOR_PORT);

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new RopeControl());
    }
    
    public void moveLift(double v)
    {
    	this.lift_motor.set(v);
    }
}

