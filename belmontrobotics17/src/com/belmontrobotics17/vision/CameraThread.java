package com.belmontrobotics17.vision;

import java.io.File;

import org.opencv.core.Core;
import org.opencv.core.CvException;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat4;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.LineSegmentDetector;

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
		//Mat outframe = new Mat();
		
		while(!Thread.interrupted())
		{
			sink.grabFrame(frame);

		        try {
		            LineSegmentDetector ls = Imgproc.createLineSegmentDetector();
		            Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
		     
		            MatOfFloat4 lines = new MatOfFloat4();

		            ls.detect(frame, lines);


		            Mat lineImage = null;
		            frame.copyTo(lineImage);
		            

		            ls.drawSegments(lineImage, lines);


		     //       outframe = cv2.lfdfjh(frame);
					
					outputStream.putFrame(lineImage);

		        } catch (CvException e) {
		         e.printStackTrace();
		        }

		    }
			
			
			
			
			this.lastFrame = frame;
		}
	}

	