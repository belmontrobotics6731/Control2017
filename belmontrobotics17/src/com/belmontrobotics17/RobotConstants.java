package com.belmontrobotics17;

public class RobotConstants {

	// Drive PID constants
	public static double DRIVE_PID_P = 1.0;
	public static double DRIVE_PID_I = 1.0;
	public static double DRIVE_PID_D = -1.0;
	
	public static double TURN_PID_P = 1.0;
	public static double TURN_PID_I = 1.0;
	public static double TURN_PID_D = -1.0;
	
	public static double DRIVE_PID_TOLERANCE = 0.05;
	
	public static double TURN_PID_TOLERANCE = 0.01;

	// Controller constants
	public static double CHEESY_ROTATION_SENS = 1.0;
	public static double CHEESY_ROTATION_SENS_FAST = 1.5;
	public static double CHEESY_THROTTLE_SENS = 1.0;
	
	// Dimensions for calculations
	public static double TURN_DISTANCE = 10.0;
	
	public static double WHEEL_RADIUS = 0.0762; // Meters
}
