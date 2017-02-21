package com.belmontrobotics17;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import com.belmontrobotics17.commands.drivetrain.ToggleDrivetrainDirection;
import com.belmontrobotics17.commands.gearmechanism.ToggleGearMechanism;
import com.belmontrobotics17.commands.vision.VisionTest;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	private Joystick logitechJoystick = new Joystick(RobotMap.LOGITECH_PORT);
	private Button gearToggleButton = new JoystickButton(this.logitechJoystick, RobotMap.JOYSTICK_GEAR_TOGGLE_BUTTON);
	private Button visionTestButton = new JoystickButton(this.logitechJoystick, RobotMap.JOYSTICK_VISION_TEST_BUTTON);
	private Button directionToggleButton = new JoystickButton(this.logitechJoystick, RobotMap.JOYSTICK_DIRECTION_TOGGLE_BUTTON);
	
	public OI()
	{
		this.gearToggleButton.whenReleased(new ToggleGearMechanism(400));
		//this.visionTestButton.whenReleased(new VisionTest());
		this.directionToggleButton.whenReleased(new ToggleDrivetrainDirection());
	}
	
	public double getLogitechJoystick(int axis)
	{
		double r = this.logitechJoystick.getRawAxis(axis);
		
		double thresh = RobotPrefs.JOYSTICK_THRESHOLD;
		
		if(r < thresh && r > -thresh)
    		r = 0.0;
		
		return (r - Math.signum(r) * thresh) / (1.0 - thresh);
	}
	
	public boolean getLogitechButton(int button)
	{
		return this.logitechJoystick.getRawButton(button);
	}
}
