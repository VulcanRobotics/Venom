package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.subsystem.swerve.math.Vector;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;

/**
 *@Author lcook
 *@Author afiol-mahon
 */
public class C_AutoDrive extends Command implements PIDSource, PIDOutput {

	PIDController distanceController;
	double direction;
	
	double P = 1.0;
	double I = 0;
	double D = 0;
	
    public C_AutoDrive(double distance, double direction) {
    	requires(Robot.swerveDrive);
    	distanceController = new PIDController(P, I, D, this, this);
    	distanceController.setSetpoint(distance);
    	distanceController.setOutputRange(-0.6, 0.6);
    	this.direction = direction;
    }
    
    protected void initialize() {
    	setTimeout(10);
    	Robot.swerveDrive.resetDistanceDriven();
    	distanceController.enable();
    	Robot.swerveDrive.enableHeadingController(Robot.swerveDrive.getHeading());
    }
    
    public double pidGet() {
    	System.out.println("average distance:" + Robot.swerveDrive.getAverageDistanceDriven());
    	return Robot.swerveDrive.getAverageDistanceDriven();
    }
    
    public void pidWrite(double magnitude) {
    	System.out.println("magnitude: " + magnitude);
    	Vector power = new Vector(1, 1);
    	power.setAngle(direction);
    	power.setMagnitude(magnitude);
    	Robot.swerveDrive.powerDrive(power, 0, 0);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return (distanceController.getError() < 0.25) || isTimedOut();
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
