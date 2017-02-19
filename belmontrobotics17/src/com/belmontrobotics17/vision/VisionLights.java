package com.belmontrobotics17.vision;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.VictorSP;

public class VisionLights extends VictorSP {

	public VisionLights(int channel)
	{
		super(channel);
	}
	
	public void en()
	{
		this.set(1.0);
	}
	
	public void dis()
	{
		this.set(0.0);
	}
}
