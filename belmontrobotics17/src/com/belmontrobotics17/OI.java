package com.belmontrobotics17;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import com.belmontrobotics17.commands.ToggleGearMechanism;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	private Joystick logitechJoystick = new Joystick(RobotMap.LOGITECH_PORT);
	private Button gearToggleButton = new JoystickButton(this.logitechJoystick, RobotMap.JOYSTICK_GEAR_TOGGLE_BUTTON);
	
	public OI()
	{
		this.gearToggleButton.whenReleased(new ToggleGearMechanism());
	}
	
	public double getLogitechJoystick(int axis)
	{
		return this.logitechJoystick.getRawAxis(axis);
	}
	
	public boolean getLogitechButton(int button)
	{
		return this.logitechJoystick.getRawButton(button);
	}
}
