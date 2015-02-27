package org.usfirst.frc.team1218.auton;

import org.usfirst.frc.team1218.subsystem.binIntake.BinIntake;
import org.usfirst.frc.team1218.subsystem.binIntake.C_SetBinIntake;
import org.usfirst.frc.team1218.subsystem.binIntake.C_SetClaw;
import org.usfirst.frc.team1218.subsystem.binIntake.C_SetRollLeft;
import org.usfirst.frc.team1218.subsystem.elevator.C_GoToBottom;
import org.usfirst.frc.team1218.subsystem.elevator.C_GoToTop;
import org.usfirst.frc.team1218.subsystem.elevator.C_WaitForTote;
import org.usfirst.frc.team1218.subsystem.fourBar.C_GoToPosition;
import org.usfirst.frc.team1218.subsystem.fourBar.C_SeekPosition;
import org.usfirst.frc.team1218.subsystem.swerve.C_AutoDrive;
import org.usfirst.frc.team1218.subsystem.swerve.C_Index;
import org.usfirst.frc.team1218.subsystem.swerve.C_ZeroRobotHeading;
import org.usfirst.frc.team1218.subsystem.toteIntake.C_AutorunToteIntake;
import org.usfirst.frc.team1218.subsystem.toteIntake.ToteIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class C_PickupTote extends CommandGroup {
    
    public  C_PickupTote() {
    	
    	//get ready - index set heading , prep for bin pickup, turn on intake, pre position darts
    	addSequential(new C_ZeroRobotHeading());
    	addSequential(new C_Index());
    	addParallel(new C_SeekPosition(0.2));
    	addSequential(new C_GoToTop());
    	addSequential(new C_AutorunToteIntake(ToteIntake.TOTE_INTAKE_POWER));
    	
    	//pickup first bin/tote combo
    	//addParallel(new C_AutoDrive(4, 0, 0.7));
    	addParallel(new C_SlowFastDrive());
    	addSequential(new C_SetRollLeft(BinIntake.INTAKE_POWER));
    	addSequential(new C_SetClaw(false));
    	addSequential(new C_WaitForTote(20));
    	addSequential(new C_GoToBottom());
    	addSequential(new C_GoToTop());
    	
    	//pickup second tote
    	//addParallel(new C_AutoDrive(4.5, 0, 0.7));
    	addSequential(new C_Index());
    	addParallel(new C_SlowFastDrive());
    	addSequential(new C_WaitForTote(20));
    	addSequential(new C_GoToBottom());
    	addSequential(new C_GoToTop());
    	
    	//pickup third tote
    	//addParallel(new C_AutoDrive(4.5, 0, 0.7));
    	addSequential(new C_Index());
    	addParallel(new C_SlowFastDrive());
    	addSequential(new C_WaitForTote(20));
    	addSequential(new C_GoToBottom());
    	addSequential(new C_GoToTop());
    	
    	//drive to auto zone
    	addSequential(new C_AutorunToteIntake(0));
    	addParallel(new C_GoToPosition(0.85));
    	addSequential(new C_SetBinIntake(0));
    	addSequential(new C_AutoDrive(8, 90, 0.9));
    	
    	//drop stack
    	addSequential(new C_GoToBottom());
    	addSequential(new C_AutorunToteIntake(-ToteIntake.TOTE_INTAKE_POWER));
    	addSequential(new C_AutoDrive(4, 180, 0.7));
    	addSequential(new C_AutorunToteIntake(0));
    	
    }
}
