package org.usfirst.frc.team1218.commands.auton;

import org.usfirst.frc.team1218.commands.binIntake.SetClaw;
import org.usfirst.frc.team1218.commands.elevator.ReferenceElevatorTop;
import org.usfirst.frc.team1218.commands.swerve.AutonZeroHeading;
import org.usfirst.frc.team1218.commands.swerve.CalibrateModules;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *@author afiolmahon
 */
public class Auton_Calibrate extends CommandGroup {
    
    public  Auton_Calibrate() {
    	addParallel(new ReferenceElevatorTop());
        addSequential(new AutonZeroHeading());
        addParallel(new CalibrateModules());
    	addSequential(new SetClaw(false));

    }
}
