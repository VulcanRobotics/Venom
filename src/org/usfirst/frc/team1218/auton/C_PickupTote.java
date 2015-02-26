package org.usfirst.frc.team1218.auton;

import org.usfirst.frc.team1218.subsystem.binIntake.BinIntake;
import org.usfirst.frc.team1218.subsystem.binIntake.C_SetBinIntake;
import org.usfirst.frc.team1218.subsystem.binIntake.C_SetClaw;
import org.usfirst.frc.team1218.subsystem.elevator.C_GoToBottom;
import org.usfirst.frc.team1218.subsystem.elevator.C_GoToTop;
import org.usfirst.frc.team1218.subsystem.fourBar.C_SeekPosition;
import org.usfirst.frc.team1218.subsystem.swerve.C_AutoDrive;
import org.usfirst.frc.team1218.subsystem.toteIntake.C_AutorunToteIntake;
import org.usfirst.frc.team1218.subsystem.toteIntake.ToteIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class C_PickupTote extends CommandGroup {
    
    public  C_PickupTote() {
    	addSequential(new C_GoToTop());
    	addSequential(new C_SeekPosition(0.2));
    	addParallel(new C_AutoDrive(5, 0));
    	addSequential(new C_AutorunToteIntake(ToteIntake.TOTE_INTAKE_POWER));
    	addSequential(new C_SetBinIntake(BinIntake.INTAKE_POWER));
    	addSequential(new C_SetClaw(true));
    	addSequential(new C_Wait(2));
    	addSequential(new C_SetBinIntake(0));
    	addSequential(new C_SetClaw(false));
    	addParallel(new C_SeekPosition(0.5));
    	addSequential(new C_GoToBottom());
    	addSequential(new C_AutorunToteIntake(0.0));
    	addSequential(new C_GoToTop());
    }
}
