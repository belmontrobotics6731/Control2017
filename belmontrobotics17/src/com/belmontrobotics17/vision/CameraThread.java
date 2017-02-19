package com.belmontrobotics17.vision;

import org.opencv.core.Mat;


import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class CameraThread extends Thread {
	
	private int width;
	private int height;
	
	public Mat lastFrame = new Mat();
	
	public CameraThread(int w, int h)
	{
		this.width = w;
		this.height = h;
	}
	
	public void run()
	{
		CameraServer server = CameraServer.getInstance();
		
		UsbCamera camera = server.startAutomaticCapture();
		camera.setResolution(this.width, this.height);
		
		//System.out.println("-------------------------------------------- DONE ---------------------------------------------");
		
		CvSink sink = server.getVideo();
		CvSource outputStream = server.putVideo("Vision", this.width, this.height);
		
		Mat frame = new Mat();
		
		while(!Thread.interrupted())
		{
			sink.grabFrame(frame);
			outputStream.putFrame(frame);
			this.lastFrame = frame;
		}
	}
}
