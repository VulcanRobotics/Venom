package org.usfirst.frc.team1218.commands.swerve;

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
public class AutoDrive extends Command implements PIDSource, PIDOutput {

	private final PIDController distanceController;
	private final double direction;
	private final double heading;
	
	private static final double P = 1.5;
	private static final double I = 0.0001;
	private static final double D = 0;
		
    public AutoDrive(double distance, double direction, double heading, double maxSpeed) {
    	requires(Robot.swerveDrive);
    	distanceController = new PIDController(P, I, D, this, this);
    	distanceController.setSetpoint(Math.abs(distance));//Could be implemented more nicely
    	distanceController.setOutputRange(-maxSpeed, maxSpeed);
    	this.direction = direction;
    	this.heading = heading;
    }
    
    protected void initialize() {
    	setTimeout(10);
    	Robot.swerveDrive.resetDistanceDriven();
    	Timer.delay(0.1);
    	distanceController.enable();
    	Robot.swerveDrive.enableHeadingController(heading);
    }
    
    public double pidGet() {
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