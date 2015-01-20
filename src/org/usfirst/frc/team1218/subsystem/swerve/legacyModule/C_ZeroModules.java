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
    	for (int i = 0; i < 4; i++) {
    		Robot.swerveSystem.module.get(i).setZeroing();
    		System.out.println("setZeroing on module " + i);
    	}
    }

    protected void execute() {
    	modulesReset = 0;
    	for (int i = 0; i < 4; i++) {
    		if (Robot.swerveSystem.module.get(i).getZeroing()) {
    			Robot.swerveSystem.module.get(i).zeroModule();
    		} else {
    			modulesReset += 1;
    			System.out.println("Module " + i + " complete zero. standing by");
    		}
    	}
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
