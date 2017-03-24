package com.belmontrobotics17.vision;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvException;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfFloat4;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.LineSegmentDetector;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CameraThread extends Thread {
	
	private int width;
	private int height;
	private int FPS;
	
	public Mat lastFrame = new Mat();
		public CameraThread(int w, int h, int fps)
	{
		this.width = w;
		this.height = h;
		this.FPS = fps;
	}
	
	public void run()
	{
		CameraServer server = CameraServer.getInstance();
		
		//UsbCamera frontcamera = server.startAutomaticCapture(0);
		//frontcamera.setResolution(this.width, this.height);
		//frontcamera.setFPS(this.FPS);
		
		UsbCamera climbcamera = server.startAutomaticCapture(1);
		climbcamera.setResolution(this.width, this.height);
		climbcamera.setFPS(this.FPS);
		
		//System.out.println("-------------------------------------------- DONE ---------------------------------------------");
		
		//CvSink frontsink = server.getVideo(frontcamera);
		//CvSink climbsink = server.getVideo(climbcamera);
		
		//CvSource frontstream = server.putVideo("Front", this.width, this.height);
		//CvSource climbstream = server.putVideo("Climb", this.width, this.height);
		
		//Mat frame = new Mat();
		//Mat lineImage = new Mat();
		//Mat outframe = new Mat();
		
		//LineSegmentDetector ls = Imgproc.createLineSegmentDetector();
		
		while(!Thread.interrupted())
		{
			/*try{
				frontsink.grabFrame(frame);
				
				if(frame.size().height == this.height && frame.size().width == this.width)
					frontstream.putFrame(frame);
				
				climbsink.grabFrame(frame);
				
				if(frame.size().height == this.height && frame.size().width == this.width)
					climbstream.putFrame(frame);
				
		        //try {
		         //   Imgproc.cvtColor(frame, frame, Imgproc.COLOR_RGB2HSV_FULL);
		            
		           // List<MatOfPoint> contours = new ArrayList<MatOfPoint>();

		           // Mat hierarchy = new Mat();
		            
		            //Imgproc.findContours(frame, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
		            
		            //ls.detect(frame, lines);

		            //frame.copyTo(lineImage);
		            

		            //ls.drawSegments(lineImage, lines);


		     //       outframe = cv2.lfdfjh(frame);
		            //int i = 0;
		//			for (MatOfPoint c: contours){
			//			SmartDashboard.putNumber("Contour " + (i++), Imgproc.contourArea(c));
				//	}

		    	} catch (Exception e) {
		    		e.printStackTrace();
		    	}

		    }
			
			
			
			
			this.lastFrame = frame;*/
		}
	}
}
	