package com.belmontrobotics17.subsystems;

import org.opencv.core.Mat;

import com.belmontrobotics17.RobotMap;
import com.belmontrobotics17.commands.vision.VisionTest;
import com.belmontrobotics17.vision.CameraThread;
import com.belmontrobotics17.vision.VisionLights;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Vision extends Subsystem {

	private CameraThread thread;
	public VisionLights lights = new VisionLights(RobotMap.LIGHTS_PORT);
	
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new VisionTest());
    }
	
	public void initCameraStream(int cw, int ch, int fps)
	{
		this.thread = new CameraThread(cw, ch, fps);
		this.thread.start();
	}
	
	public Mat getLastFrame()
	{
		return this.thread.lastFrame;
	}
}
