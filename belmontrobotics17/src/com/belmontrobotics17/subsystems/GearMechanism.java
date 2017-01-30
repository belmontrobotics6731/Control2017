package com.belmontrobotics17.subsystems;

import com.belmontrobotics17.commands.gearmechanism.GearControl;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearMechanism extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private DoubleSolenoid solenoid1 = new DoubleSolenoid(0, 1);
	private DoubleSolenoid solenoid2 = new DoubleSolenoid(2, 3);
	private boolean solenoidOpen = false;
	
	public GearMechanism()
	{
		this.stopSolenoids();
	}
	
	public void stopSolenoids()
	{
		this.solenoid1.set(DoubleSolenoid.Value.kOff);
		this.solenoid2.set(DoubleSolenoid.Value.kOff);
	}
	
	public void toggleSolenoids()
	{
		if(!this.solenoidOpen)
		{
			this.solenoid1.set(DoubleSolenoid.Value.kForward);
			this.solenoid2.set(DoubleSolenoid.Value.kForward);
			this.solenoidOpen = true;
		}
		else
		{
			this.solenoid1.set(DoubleSolenoid.Value.kReverse);
			this.solenoid2.set(DoubleSolenoid.Value.kReverse);
			this.solenoidOpen = false;
		}
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new GearControl());
    }
}

