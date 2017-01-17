package com.belmontrobotics17;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	// PWM ports
	public static int LEFT_DRIVE_PORT = 1;
	public static int RIGHT_DRIVE_PORT = 2;
	
	// DIGITAL ports
	public static int ENCODER_PORT_1 = 5;
	public static int ENCODER_PORT_2 = 6;
	
	// Drive PID constants
	public static double DRIVE_PID_P = 1.0;
	public static double DRIVE_PID_I = 0.0;
	public static double DRIVE_PID_D = 0.0;
	
	public static double DRIVE_PID_TOLERANCE = 0.5;
	
	// Controller ports
	//public static int XBOX_PORT = 1;
	public static int LOGITECH_PORT = 0;
	public static int JOYSTICK_FB_PORT = 1;
	public static int JOYSTICK_LR_PORT = 2;
	
	// Controller constants
	public static double CHEESY_ROTATION_SENS = 1.0;
	public static double CHEESY_ROTATION_SENS_FAST = 2.0;
}
