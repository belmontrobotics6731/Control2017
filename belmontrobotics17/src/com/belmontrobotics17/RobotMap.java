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
	
	public static int LIFT_MOTOR_PORT = 6;
	
	public static int LIGHTS_PORT = 5;
	
	public static int GYRO_PORT = 0;
	
	// DIGITAL ports
	public static int LEFT_ENCODER_PORT1 = 0;
	public static int LEFT_ENCODER_PORT2 = 1;
	public static int RIGHT_ENCODER_PORT1 = 2;
	public static int RIGHT_ENCODER_PORT2 = 3;
	
	// Controller ports
	public static int LOGITECH_PORT = 0;
	public static int JOYSTICK_FB_PORT = 1;
	public static int JOYSTICK_LR_PORT = 2;
	public static int JOYSTICK_LIFT_PORT = 10;
	public static int JOYSTICK_SENS_PORT = 3;
	public static int JOYSTICK_FASTTURN_BUTTON = 2;
	public static int JOYSTICK_GEAR_TOGGLE_BUTTON = 3;
	public static int JOYSTICK_VISION_TEST_BUTTON = 6;
	public static int JOYSTICK_DIRECTION_TOGGLE_BUTTON = 5;
	public static int JOYSTICK_NOTURN_BUTTON = 1;
	public static int JOYSTICK_SLOW_BUTTON = 11;
	public static int JOYSTICK_MEDIUM_BUTTON = 9;
	public static int JOYSTICK_FAST_BUTTON = 7;
	public static int JOYSTICK_BRAKE_BUTTON = 4;
	public static int JOYSTICK_180_BUTTON = 12;
}
