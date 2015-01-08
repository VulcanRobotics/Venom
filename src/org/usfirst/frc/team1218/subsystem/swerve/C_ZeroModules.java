/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.OI;
import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 * @author afiol-mahon
 */
public class C_ZeroModules extends Command {
	
    int numberOfModulesZeroed;
    
    public C_ZeroModules() {
        requires(Robot.swerve);
    }

    protected void initialize() {
    	System.out.println("Zeroing Modules...");
        for(int k = 0; k<4; k++) {
            Robot.swerve.module[k].isZeroing = true;
        }
    }

    protected void execute() {
    	numberOfModulesZeroed = 0;
        for(int k = 0; k<4; k++) {
        	Robot.swerve.module[k].zero();
            if (!Robot.swerve.module[k].isZeroing) numberOfModulesZeroed++;
        }
    }

    protected boolean isFinished() {
        return numberOfModulesZeroed == 4 | OI.CancelZeroModules.get();
    }

    protected void end() {
        System.out.println("Swerve Modules Zeroed");
    }

    protected void interrupted() {
    	System.out.println("Swerve Module Zeroing Interrupted");
    }
}
