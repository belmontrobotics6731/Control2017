package com.belmontrobotics17;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	private Joystick logitechJoystick = new Joystick(RobotMap.LOGITECH_PORT);
	
	public double getJoystick(int axis)
	{
		return this.logitechJoystick.getRawAxis(axis);
	}
}
