package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.subsystem.swerve.math.Vector;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *@Author lcook
 *@Author afiol-mahon
 */
public class C_AutoDrive extends Command implements PIDSource, PIDOutput {

	PIDController distanceController;
	double direction;
	
	double P = 1.5;//1.2;
	double I = 0.0001;
	double D = 0;
	
    public C_AutoDrive(double distance, double direction, double maxSpeed) {
    	requires(Robot.swerveDrive);
    	distanceController = new PIDController(P, I, D, this, this);
    	distanceController.setSetpoint(distance);
    	distanceController.setOutputRange(-maxSpeed, maxSpeed);
    	this.direction = direction;
    }
    
    protected void initialize() {
    	setTimeout(10);
    	Robot.swerveDrive.resetDistanceDriven();
    	Timer.delay(0.1);
    	distanceController.enable();
    	Robot.swerveDrive.enableHeadingController(0);
    }
    
    public double pidGet() {
    	System.out.println("average distance:" + Robot.swerveDrive.getAverageDistanceDriven());
    	return Robot.swerveDrive.getAverageDistanceDriven();
    }
    
    public void pidWrite(double magnitude) {
    	Vector power = new Vector(1, 1);
    	power.setAngle(direction);
    	power.setMagnitude(magnitude);
    	Robot.swerveDrive.powerDrive(power, 0);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return (Math.abs(distanceController.getError()) < 0.25) || isTimedOut();
    }

    protected void end() {
    	distanceController.disable();
    	Robot.swerveDrive.disableHeadingController();
    	System.out.println("autodrive ended");
    }

    protected void interrupted() {
    	end();
    }
}