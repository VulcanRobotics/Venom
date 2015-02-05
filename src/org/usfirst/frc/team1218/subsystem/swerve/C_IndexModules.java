package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiolmahon
 */
public class C_IndexModules extends Command {

	//TODO redesign to ensure consistent zeroing rather than assuming module rotates quickly
	
	private ControlMode[] lastControlMode;
	
    public C_IndexModules() {
    	requires(Robot.swerveSystem);
    }
    
    protected void initialize() {
    	lastControlMode = new ControlMode[4];
    	Robot.swerveSystem.getModuleList().stream().forEach(m -> {
    		lastControlMode[m.moduleNumber] = m.angleController.getControlMode();
    		m.angleController.changeControlMode(ControlMode.PercentVbus);
    		m.angleController.set(SwerveModule.MAX_ANGLE_CONTROLLER_POWER);
    		Timer.delay(3.0);
    		m.angleController.set(0.0);
    	});
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    	Robot.swerveSystem.getModuleList().stream().forEach(m -> {
    		m.angleController.changeControlMode(lastControlMode[m.moduleNumber]);
    	});
    }

    protected void interrupted() {
    	end();
    }
}
