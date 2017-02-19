package com.belmontrobotics17;

import edu.wpi.first.wpilibj.Preferences;

public class RobotPrefs {
	
	// Preferences
	public static double JOYSTICK_THRESHOLD = 0.05;
	
	
	// utils
	private static Preferences prefs;
	
	public static void init()
	{
		//prefs = Preferences.getInstance();
		
		//prefs.getDouble("Joystick threshold", JOYSTICK_THRESHOLD);
	}
}
