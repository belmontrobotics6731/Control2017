
package com.belmontrobotics17;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.belmontrobotics17.commands.auto.*;
import com.belmontrobotics17.subsystems.Drivetrain;
import com.belmontrobotics17.subsystems.GearMechanism;
import com.belmontrobotics17.subsystems.RopeClimbing;
import com.belmontrobotics17.subsystems.Vision;
import com.belmontrobotics17.vision.CameraThread;

/*import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;*/


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static Drivetrain drivetrain = new Drivetrain();
	public static GearMechanism gearMechanism = new GearMechanism();
	public static RopeClimbing ropeClimbing = new RopeClimbing();
	public static Vision vision = new Vision();
	
	//public static NetworkTable dashtable;
	//public static NetworkTable ntable;
	
	public static OI oi;

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		
		drivetrain.gyro = new ADXRS450_Gyro();

		//vision.initCameraStream(360, 270, 20);
		
		chooser.addDefault("Test Auto", new SidesAuto(-57 * 0.0254));
		chooser.addObject("Empty Auto", new EmptyAuto());
		chooser.addObject("Sides Auto", new SidesAuto(-60.0 * 0.0254));
		//chooser.addObject("Center Auto", new CenterAuto());
		SmartDashboard.putData("Auto mode", chooser);
		
		RobotPrefs.init();
		
		//NetworkTable.getTable("vision").putBoolean("requestpoint", false);
		
		//dashtable = NetworkTable.getTable("SmartDashboard");
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		//drivetrain.gyro.calibrate();
		drivetrain.gyro.reset();
		RobotPrefs.update();
		
		//NetworkTable.getTable("vision").putBoolean("requestpoint", false);
		
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		
		drivetrain.gyro.reset();
		RobotPrefs.update();
		
		//NetworkTable.getTable("vision").putBoolean("requestpoint", false);
		
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
