package com.belmontrobotics17;

public class RobotConstants {

	/** Testing **/
	public static double TEST_MOVE = 1.0;
	public static double TEST_TURN = 60.0;
	
	
	/** OI **/
	public static double JOYSTICK_THRESHOLD = 0.05;
	
	
	/** PID **/
	public static double DRIVE_PID_P = 10.0;
	public static double DRIVE_PID_I = 0.1;
	public static double DRIVE_PID_D = 1.0;
	
	public static double TURN_PID_P = 0.014;
	public static double TURN_PID_I = 0.0003;
	public static double TURN_PID_D = 0.05;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
	
	public static double MAX_PID_MVEL = 0.4;
	public static double MAX_TURN_PID_MVEL = 0.6;
	
	public static double DRIVE_PID_TOLERANCE = 0.03;
	public static double TURN_PID_TOLERANCE = 0.1;
	
	public static double PID_STOP_OUTPUT_THRESH = 0.05;
	public static double TURN_PID_STOP_OUTPUT_THRESH = 0.01;
	
	public static double LAST_MOVE_DISTANCE = 0.5;
	
	public static double DRIVE_ADJUST_ANGLE_K = 0.02;
	
	public static double CENTER_TO_TAPE_TOLERANCE = 0.01;
	public static double CENTER_TO_TAPE_ANGLE_TOLERANCE = 5.0;

	// Controller constants
	public static double CHEESY_ROTATION_SENS = 1.0;
	public static double CHEESY_ROTATION_SENS_FAST = 1.5;
	public static double CHEESY_THROTTLE_SENS = 1.0;
	
	// Dimensions for calculations
	public static double TURN_DISTANCE = 10.0;
	
	public static double WHEEL_RADIUS = 0.0762; // Meters
	public static double AXLE_RADIUS = 11.5 * 0.0254; // Meters
	
	public static double SPIKE_OFFSET_ANGLE = 60.0; // degrees
	
	public static double CAMERA_WIDTH = 640;
	public static double CAMERA_HEIGHT = 480;
	
	public static double MOVE_TO_TAPE_CENTER_CORRECTION_THRESHOLD = 40; // pixels
	
	public static double GEAR_PLACE_Y_THRESHOLD = 10; // pixels from bottom
	public static double GEAR_PLACE_A_THRESHOLD = 10000; // pixels squared
	
	public static double TAPE_MOVE_ADJUST_K = 0.005;
}
