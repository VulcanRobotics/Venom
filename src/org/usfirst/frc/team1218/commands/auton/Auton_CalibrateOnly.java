package org.usfirst.frc.team1218.commands.auton;

import org.usfirst.frc.team1218.commands.binIntake.SetClaw;
import org.usfirst.frc.team1218.commands.elevator.ReferenceElevatorTop;
import org.usfirst.frc.team1218.commands.swerve.CalibrateModules;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auton_CalibrateOnly extends CommandGroup {
    
    public  Auton_CalibrateOnly() {
    	addSequential(new SetClaw(false));
    	addSequential(new ReferenceElevatorTop());
        addSequential(new CalibrateModules());
    }
}
