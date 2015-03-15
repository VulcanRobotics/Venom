package org.usfirst.frc.team1218.commands.auton;

import org.usfirst.frc.team1218.commands.binIntake.SetClaw;
import org.usfirst.frc.team1218.commands.elevator.ReferenceElevatorTop;
import org.usfirst.frc.team1218.commands.swerve.AutoDrive;
import org.usfirst.frc.team1218.commands.swerve.CalibrateModules;
import org.usfirst.frc.team1218.commands.swerve.ZeroRobotHeading;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auton_JustDrive extends CommandGroup {
    
    public  Auton_JustDrive() {
        addSequential(new ZeroRobotHeading());
        addSequential(new SetClaw(false));
        addSequential(new CalibrateModules());
    	addSequential(new ReferenceElevatorTop());
        addSequential(new AutoDrive(10, 90, 0, 1.0));
    }
}
