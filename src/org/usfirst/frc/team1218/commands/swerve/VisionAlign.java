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
	
	private final double P = -0.1;
	private final double I = -0.003;
	private final double D = -1.0;
	
	private final double MAX_POWER = 1.5;
	
    public VisionAlign() {
    	requires(Robot.swerveDrive);
    	PID = new PIDController(P, I, D, this, this);
    	PID.setInputRange(-1.0, 1.0);
    	PID.setOutputRange(-MAX_POWER, MAX_POWER);
    	PID.setSetpoint(0.0);
    	PID.setAbsoluteTolerance(1);
    	PID.disable();
    }

    public void pidWrite(double velocity){
    	Robot.swerveDrive.powerDrive(new Vector(0, velocity), 0);
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
    
    protected void initialize() {
    	Robot.swerveDrive.enableHeadingController(-90.0);
    	PID.enable();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
    	double dvdt = Math.abs(SmartDashboard.getNumber("dvdt", NOT_CONNECTED));
        return PID.onTarget() && dvdt < 0.007;
    }

    protected void end() {
    	PID.disable();
    	Robot.swerveDrive.powerDrive(new Vector(0, 0), 0);
    	Robot.swerveDrive.disableHeadingController();
    }

    protected void interrupted() {
    	end();
    }
}
