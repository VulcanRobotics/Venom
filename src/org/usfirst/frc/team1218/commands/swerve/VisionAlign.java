package org.usfirst.frc.team1218.commands.swerve;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.subsystem.swerve.math.Vector;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class VisionAlign extends Command implements PIDSource, PIDOutput{

	private double NOT_CONNECTED = 3;
	
	private PIDController PID;
	
	private final double P = -.080;
	private final double I = 0.0;
	private final double D = 0.0;
	
	private final double MAX_POWER = 1.5;
	
    public VisionAlign() {

    	requires(Robot.swerveDrive);
    	PID = new PIDController(P, I, D, this, this);
    	PID.setInputRange(-1.0, 1.0);
    	PID.setOutputRange(-MAX_POWER, MAX_POWER);
    	PID.setSetpoint(0.0);
    	PID.setAbsoluteTolerance(0.1);
    	PID.disable();
    }

    public void pidWrite(double velocity){
    	Robot.swerveDrive.powerDrive(new Vector(velocity, 0), 0);
    }
    
    public double pidGet() {
    	double xRatio = SmartDashboard.getNumber("xDistance", NOT_CONNECTED);
    	if (xRatio == NOT_CONNECTED) {
    		System.out.println("error: cannot connect to roborealm");
    		xRatio = 0;
    	}
    	System.out.println(xRatio);
    	return xRatio;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.swerveDrive.enableHeadingController(0.0);
    	PID.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return PID.onTarget() || Math.abs(SmartDashboard.getNumber("xRatio")) >= 0.5;
    }

    // Called once after isFinished returns true
    protected void end() {
    	PID.disable();
    	Robot.swerveDrive.disableHeadingController();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
