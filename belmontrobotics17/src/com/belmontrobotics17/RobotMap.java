package com.belmontrobotics17;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	/***  PWM ports  ***/
	
	// Left drive ports
	public static int DRIVE0_PORT = 0;
	public static int DRIVE1_PORT = 1;
	
	// Right drive ports
	public static int DRIVE2_PORT = 2;
	public static int DRIVE3_PORT = 3;
	
	
	// DIGITAL ports
	public static int LEFT_ENCODER_PORT1 = 3;
	public static int LEFT_ENCODER_PORT2 = 4;
	public static int RIGHT_ENCODER_PORT1 = 5;
	public static int RIGHT_ENCODER_PORT2 = 6;
	
	// Controller ports
	public static int LOGITECH_PORT = 0;
	public static int JOYSTICK_FB_PORT = 1;
	public static int JOYSTICK_LR_PORT = 2;
	public static int JOYSTICK_SENS_PORT = 3;
	public static int JOYSTICK_FASTTURN_BUTTON = 2;
	public static int JOYSTICK_GEAR_TOGGLE_BUTTON = 3;
}
