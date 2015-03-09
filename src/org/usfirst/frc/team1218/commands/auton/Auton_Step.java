package org.usfirst.frc.team1218.commands.auton;

import org.usfirst.frc.team1218.commands.autonHooks.DeployHooks;
import org.usfirst.frc.team1218.commands.elevator.GoToTop;
import org.usfirst.frc.team1218.commands.swerve.AutoDrive;
import org.usfirst.frc.team1218.commands.swerve.CalibrateModules;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auton_Step extends CommandGroup {
    
    public  Auton_Step() {
    	addSequential(new CalibrateModules());
    	addParallel(new GoToTop());
    	addSequential(new AutoDrive(-6, 0, 0, 1));
    	addSequential(new DeployHooks(true));
    	addSequential(new AutoDrive(10, 0, 0, 1));
    	addSequential(new DeployHooks(false));

    }
}
