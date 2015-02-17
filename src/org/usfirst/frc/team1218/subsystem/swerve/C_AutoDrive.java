package org.usfirst.frc.team1218.subsystem.swerve;

import java.util.List;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.subsystem.swerve.math.Vector;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_AutoDrive extends Command implements PIDSource, PIDOutput {

	double heading;
	PIDController distanceController;
	
	double P = 1.0;
	double I = 0;
	double D = 0;
	
    public C_AutoDrive(Vector translation, double heading) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.swerveDrive);
    	distanceController = new PIDController(P, I, D, this, this);
    	distanceController.setSetpoint(translation.getMagnitude());
    	distanceController.setAbsoluteTolerance(0.25);
    	distanceController.setInputRange(-9999, 9999);
    	distanceController.setOutputRange(-0.6, 0.6);
    	this.heading = heading;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(10);
    	Robot.swerveDrive.zeroDriveEncoders();
    	distanceController.enable();
    }
    
    public double pidGet() {
    	System.out.println("average distance:" + Robot.swerveDrive.getAverageDistance());
    	return Robot.swerveDrive.getAverageDistance();
    }
    
    public void pidWrite(double magnitude) {
    	System.out.println("magnitude: " + magnitude);
    	Vector power = new Vector(1, 1);
    	power.setAngle(heading);
    	power.setMagnitude(magnitude);
    	Robot.swerveDrive.powerDrive(power, 0, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return distanceController.onTarget() || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	distanceController.disable();
    	System.out.println("autodrive ended");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
