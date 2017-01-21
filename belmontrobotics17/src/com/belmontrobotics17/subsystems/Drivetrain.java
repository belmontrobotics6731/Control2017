package com.belmontrobotics17.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;


import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.PIDOutput;

import com.belmontrobotics17.RobotMap;
import com.belmontrobotics17.RobotVars;
import com.belmontrobotics17.commands.drivetrain.DriveWithJoystickCmd;

/**
 *
 */
public class Drivetrain extends Subsystem {

	// Left drive motors
	private Spark drive_motor0 = new Spark(RobotMap.DRIVE0_PORT);
	private Spark drive_motor1 = new Spark(RobotMap.DRIVE1_PORT);
	
	// Right drive motors		
	private Spark drive_motor2 = new Spark(RobotMap.DRIVE2_PORT);
	private Spark drive_motor3 = new Spark(RobotMap.DRIVE3_PORT);
	
	private Encoder leftDriveEncoder = new Encoder(RobotMap.LEFT_ENCODER_PORT1, RobotMap.LEFT_ENCODER_PORT2);
	private Encoder rightDriveEncoder = new Encoder(RobotMap.RIGHT_ENCODER_PORT1, RobotMap.RIGHT_ENCODER_PORT2);
	
	//public PIDController driveDistancePID = new PIDController(RobotVars.DRIVE_PID_P, RobotVars.DRIVE_PID_I, RobotVars.DRIVE_PID_D, new DriveDistanceSource(), new DriveDistanceOutput());
	
	public void driveDistancePID(double setPoint, double absoluteTolerance)
	{
		this.leftDriveEncoder.reset();
		this.rightDriveEncoder.reset();
		
		/*this.driveDistancePID.setSetpoint(setPoint);
		this.driveDistancePID.setAbsoluteTolerance(absoluteTolerance);
		this.driveDistancePID.enable();*/
	}
	
	public void drive(double left, double right)
	{
		this.drive_motor0.set(left);
		this.drive_motor1.set(left);
		
		this.drive_motor2.set(right);
		this.drive_motor3.set(right);
	}
	
	public void drivePID(double val)
	{
		this.drive_motor0.pidWrite(val);
		this.drive_motor1.pidWrite(val);
		
		this.drive_motor2.pidWrite(val);
		this.drive_motor3.pidWrite(val);
	}
	
	public void driveCheesy(double throttle, double rotation, boolean fasterTurn)
	{
		double lDrive = throttle;
		double rDrive = throttle;
		
		double fturnConstant = 0.0;
		double sens = RobotVars.CHEESY_ROTATION_SENS;
		if(fasterTurn) {
			fturnConstant = 1.0;
			sens = RobotVars.CHEESY_ROTATION_SENS_FAST;
		}
		
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
		
		this.drive(RobotVars.CHEESY_CONTROL_SENS * lDrive, RobotVars.CHEESY_CONTROL_SENS * rDrive);
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
    
    /*private class DriveDistanceSource implements PIDSource {
    	
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
    		return (leftDriveEncoder.getDistance() + rightDriveEncoder.getDistance()) / 2.0;
    	}
    }
    
    private class DriveDistanceOutput implements PIDOutput {
    	
    	@Override
    	public void pidWrite(double output) {
    		drivePID(output);
    	}
    }*/
}

