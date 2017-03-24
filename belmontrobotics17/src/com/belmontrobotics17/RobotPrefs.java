package com.belmontrobotics17;

import edu.wpi.first.wpilibj.Preferences;

public class RobotPrefs {
	
	private static Preferences prefs;
	
	public static void init()
	{
		prefs = Preferences.getInstance();
		
		prefs.putDouble("drive_pid_p", RobotConstants.DRIVE_PID_P);
		prefs.putDouble("drive_pid_i", RobotConstants.DRIVE_PID_I);
		prefs.putDouble("drive_pid_d", RobotConstants.DRIVE_PID_D);
		
		prefs.putDouble("turn_pid_p", RobotConstants.TURN_PID_P);
		prefs.putDouble("turn_pid_i", RobotConstants.TURN_PID_I);
		prefs.putDouble("turn_pid_d", RobotConstants.TURN_PID_D);
		
		prefs.putDouble("drive_pid_tolerance", RobotConstants.DRIVE_PID_TOLERANCE);
		
		prefs.putDouble("turn_pid_tolerance", RobotConstants.TURN_PID_TOLERANCE);
		
		prefs.putDouble("max_pid_mvel", RobotConstants.MAX_PID_MVEL);
		
		prefs.putDouble("test_move", RobotConstants.TEST_MOVE);
		prefs.putDouble("test_turn", RobotConstants.TEST_TURN);
	}
	
	public static void update()
	{
		RobotConstants.DRIVE_PID_P = prefs.getDouble("drive_pid_p", RobotConstants.DRIVE_PID_P);
		RobotConstants.DRIVE_PID_I = prefs.getDouble("drive_pid_i", RobotConstants.DRIVE_PID_I);
		RobotConstants.DRIVE_PID_D = prefs.getDouble("drive_pid_d", RobotConstants.DRIVE_PID_D);
		
		RobotConstants.TURN_PID_P = prefs.getDouble("turn_pid_p", RobotConstants.TURN_PID_P);
		RobotConstants.TURN_PID_I = prefs.getDouble("turn_pid_i", RobotConstants.TURN_PID_I);
		RobotConstants.TURN_PID_D = prefs.getDouble("turn_pid_d", RobotConstants.TURN_PID_D);
		
		RobotConstants.DRIVE_PID_TOLERANCE = prefs.getDouble("drive_pid_tolerance", RobotConstants.DRIVE_PID_TOLERANCE);
		
		RobotConstants.TURN_PID_TOLERANCE = prefs.getDouble("turn_pid_tolerance", RobotConstants.TURN_PID_TOLERANCE);
		
		RobotConstants.MAX_PID_MVEL = prefs.getDouble("max_pid_mvel", RobotConstants.MAX_PID_MVEL);
	
		RobotConstants.TEST_MOVE = prefs.getDouble("test_move", RobotConstants.TEST_MOVE);
		RobotConstants.TEST_TURN = prefs.getDouble("test_turn", RobotConstants.TEST_TURN);
	}
}
