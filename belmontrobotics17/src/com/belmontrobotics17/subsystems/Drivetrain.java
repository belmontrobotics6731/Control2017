package com.belmontrobotics17.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.PIDOutput;

import com.belmontrobotics17.RobotMap;
import com.belmontrobotics17.RobotConstants;
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
	
	public ADXRS450_Gyro gyro;
	
	public Encoder leftDriveEncoder = new Encoder(RobotMap.LEFT_ENCODER_PORT1, RobotMap.LEFT_ENCODER_PORT2);
	//private Encoder rightDriveEncoder = new Encoder(RobotMap.RIGHT_ENCODER_PORT1, RobotMap.RIGHT_ENCODER_PORT2);
	
	private boolean direction = true;
	
	// 3 settings - slow: 0.33, medium: 0.66, fast: 1
	private double velMultiplier = 1.0;
	
	private double rightPIDFactor = 1.0;
	private double startAnglePID;
	
	private double kCorr = 1.08;		//1.08;
	
	public double lastPIDOutput = 0.0;
	
	public void toggleDirection()
	{
		this.direction = !this.direction;
	}
	
	public void setVelMultiplier(double vel)
	{
		this.velMultiplier = vel;
	}
	
	// In meters
	public PIDController driveDistancePID = new PIDController(RobotConstants.DRIVE_PID_P, RobotConstants.DRIVE_PID_I, RobotConstants.DRIVE_PID_D, new DriveDistanceSource(), new DriveDistanceOutput());
	
	// In radians
	public PIDController turnAnglePID = new PIDController(RobotConstants.TURN_PID_P, RobotConstants.TURN_PID_I, RobotConstants.TURN_PID_D, new TurnAngleSource(), new TurnAngleOutput());
	
	public Drivetrain()
	{
		this.leftDriveEncoder.setDistancePerPulse(-1.19744869609*7.0742*6.0*0.0254*Math.PI/1440.0);
		//this.rightDriveEncoder.setDistancePerPulse(6.0*0.0254*Math.PI/1440.0);
	}
	
	// debug
	public void printEncodersToNetworkTables()
	{
		SmartDashboard.putNumber("Left encoder", this.leftDriveEncoder.getDistance());
		//SmartDashboard.putNumber("Right encoder", this.rightDriveEncoder.getDistance());
	}
	
	public void printGyroToNetworkTables()
	{
		SmartDashboard.putNumber("Gyro", this.gyro.getAngle());
	}
	
	public void driveDistancePID(double setPoint, double rightFactor, double absoluteTolerance)
	{
		this.leftDriveEncoder.reset();
		//this.rightDriveEncoder.reset();
		this.rightPIDFactor = rightFactor;
		this.startAnglePID = this.gyro.getAngle();
		
		this.driveDistancePID.setSetpoint(setPoint);
		this.driveDistancePID.setAbsoluteTolerance(absoluteTolerance);
		this.driveDistancePID.enable();
	}
	
	public void turnAnglePID(double setPoint, double absoluteTolerance)
	{
		this.turnToAnglePID(this.gyro.getAngle() + setPoint, absoluteTolerance);
	}
	
	public void turnToAnglePID(double setPoint, double absoluteTolerance)
	{
		this.leftDriveEncoder.reset();
		//this.rightEncoder.reset();
		
		this.turnAnglePID.setSetpoint(setPoint);
		this.turnAnglePID.setAbsoluteTolerance(absoluteTolerance);
		this.turnAnglePID.enable();
	}
	
	public void drive(double left, double right)
	{
		this.drive_motor0.set(-this.velMultiplier*left);
		this.drive_motor1.set(-this.velMultiplier*left);
		
		this.drive_motor2.set(this.velMultiplier*right/this.kCorr);
		this.drive_motor3.set(this.velMultiplier*right/this.kCorr);
		
		SmartDashboard.putNumber("Left", this.velMultiplier*left/this.kCorr);
		SmartDashboard.putNumber("Right", this.velMultiplier*right);
	}
	
	public void drivePID(double left, double right)
	{		
		this.drive_motor0.pidWrite(-left);
		this.drive_motor1.pidWrite(-left);
		
		this.drive_motor2.pidWrite(right/this.kCorr);
		this.drive_motor3.pidWrite(right/this.kCorr);
		
		SmartDashboard.putNumber("Left", left/this.kCorr);
		SmartDashboard.putNumber("Right", right);
	}
	
	public void driveCheesy(double throttle, double rotation, boolean fasterTurn, double insens)
	{
		if(!this.direction) {
			throttle = -throttle;
		}
		
		double lDrive = throttle * RobotConstants.CHEESY_THROTTLE_SENS;
		double rDrive = throttle * RobotConstants.CHEESY_THROTTLE_SENS;
		
		double fturnConstant = 0.0;
		double sens = RobotConstants.CHEESY_ROTATION_SENS;
		if(fasterTurn) {
			fturnConstant = 1.0;
			sens = RobotConstants.CHEESY_ROTATION_SENS_FAST;
		}
		
		lDrive -= rotation * sens;
		rDrive += rotation * sens;
		
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
		
		//this.drive(insens,  insens);
		this.drive(insens * lDrive, insens * rDrive);
	
	}
	
	public void stop()
	{
		this.drive_motor0.stopMotor();
		this.drive_motor1.stopMotor();
		this.drive_motor2.stopMotor();
		this.drive_motor3.stopMotor();
		this.drive(0.0, 0.0);
	}
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        
    	setDefaultCommand(new DriveWithJoystickCmd());
    }
    
    public class DriveDistanceSource implements PIDSource {
    	
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
    		printEncodersToNetworkTables();
    		return leftDriveEncoder.getDistance();
    	}
    }
    
    private class DriveDistanceOutput implements PIDOutput {
    	
    	@Override
    	public void pidWrite(double output) {
    		//drivePID(-output, -rightPIDFactor*output);
    		double d = RobotConstants.DRIVE_ADJUST_ANGLE_K * (gyro.getAngle() - startAnglePID);
    		
    		if(output > RobotConstants.MAX_PID_MVEL)
    			output = RobotConstants.MAX_PID_MVEL;
    		else if(output < -RobotConstants.MAX_PID_MVEL)
    			output = -RobotConstants.MAX_PID_MVEL;

    		
    		lastPIDOutput = output;
    		
    		//drivePID(-output, -output);
    		
    		//if(output < 0.0)
    			drivePID(-output + d, -output - d);
    		//else
    			//drivePID(-output + d, -output - d);
    	}
    }
    
    private class TurnAngleSource implements PIDSource {
    	
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
    		// Returns angle in radians that has been turned through
    		return (gyro.getAngle());
    	}
    }
    
    private class TurnAngleOutput implements PIDOutput {
    	
    	@Override
    	public void pidWrite(double output) {
    		
    		if(output > RobotConstants.MAX_TURN_PID_MVEL)
    			output = RobotConstants.MAX_TURN_PID_MVEL;
    		else if(output < -RobotConstants.MAX_TURN_PID_MVEL)
    			output = -RobotConstants.MAX_TURN_PID_MVEL;
    		
    		lastPIDOutput = output;
    		drivePID(-output, output);
    	}
    }
}

