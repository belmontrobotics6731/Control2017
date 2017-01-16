package com.belmontrobotics17.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.PIDOutput;

import com.belmontrobotics17.RobotMap;
import com.belmontrobotics17.commands.drivetrain.DriveWithJoystickCmd;

/**
 *
 */
public class Drivetrain extends Subsystem {

	private Talon leftDrive = new Talon(RobotMap.LEFT_DRIVE_PORT);
	private Talon rightDrive = new Talon(RobotMap.RIGHT_DRIVE_PORT);
	
	private Encoder driveEncoder = new Encoder(RobotMap.ENCODER_PORT_1, RobotMap.ENCODER_PORT_2);
	
	public PIDController driveDistancePID = new PIDController(RobotMap.DRIVE_PID_P, RobotMap.DRIVE_PID_I, RobotMap.DRIVE_PID_D, new DriveDistanceSource(), new DriveDistanceOutput());
	
	public void runPID(double setPoint, double absoluteTolerance)
	{
		this.driveDistancePID.setSetpoint(setPoint);
		this.driveDistancePID.setAbsoluteTolerance(absoluteTolerance);
		this.driveDistancePID.enable();
	}
	
	public void drive(double left, double right)
	{
		this.leftDrive.set(left);
		this.rightDrive.set(right);
	}
	
	public void driveCheesy(double throttle, double rotation, boolean fasterTurn)
	{
		double lDrive = throttle;
		double rDrive = throttle;
		
		double fturnConstant = fasterTurn ? 1.0 : 0.0;
		double sens = fasterTurn ? RobotMap.CHEESY_ROTATION_SENS_FAST : RobotMap.CHEESY_ROTATION_SENS;
		
		lDrive += rotation * sens;
		rDrive -= rotation * sens;
		
		if(lDrive > 1.0) {
			rDrive -= fturnConstant * (lDrive - 1.0);
			lDrive = 1.0;
		}
		else if(lDrive < -1.0) {
			rDrive += fturnConstant * (-1.0 - lDrive);
			lDrive = -1.0;
		}
		else if(rDrive > 1.0) {
			lDrive -= fturnConstant * (rDrive - 1.0);
			rDrive = 1.0;
		}
		else if(rDrive < -1.0) {
			lDrive += fturnConstant * (-1.0 - rDrive);
			rDrive = -1.0;
		}
		
		this.drive(lDrive, rDrive);
	}
	
	public void stop()
	{
		this.drive(0, 0);
	}
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        
    	setDefaultCommand(new DriveWithJoystickCmd());
    }
    
    private class DriveDistanceSource implements PIDSource {
    	
    	@Override
    	public PIDSourceType getPIDSourceType()
    	{
    		return PIDSourceType.kDisplacement;
    	}
    	
    	@Override
    	public void setPIDSourceType(PIDSourceType pidSource) { }
    	
    	@Override
    	public double pidGet()
    	{
    		return driveEncoder.getDistance();
    	}
    }
    
    private class DriveDistanceOutput implements PIDOutput {
    	
    	@Override
    	public void pidWrite(double output) {
    		leftDrive.pidWrite(output);
    		rightDrive.pidWrite(-output);
    	}
    }
}

