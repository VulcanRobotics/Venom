package org.usfirst.frc.team1218.subsystem.swerve.legacyModule;

import org.usfirst.frc.team1218.robot.OI;
import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class C_ZeroModules extends Command {
	
	private int modulesReset = 0;
	
    public C_ZeroModules() {
        requires(Robot.swerveSystem);
    }
    
    protected void initialize() {
    	Robot.swerveSystem.module.stream().forEach(m -> m.setZeroing());
    }
    
    protected void execute() {
    	modulesReset = 0;
    	Robot.swerveSystem.module.stream().forEach(m -> {
    		if (m.getZeroing()) {
    			m.zeroModule();
    		} else {
    			modulesReset += 1;
    		}
    	});
    }
    
    protected boolean isFinished() {
        return (modulesReset >= 4) | OI.cancelResetModules.get();
    }
    
    protected void end() {
    	System.out.println("Module resetting complete");
    }
    
    protected void interrupted() {
    	System.out.println("Module reset interrupted");
    }
}
