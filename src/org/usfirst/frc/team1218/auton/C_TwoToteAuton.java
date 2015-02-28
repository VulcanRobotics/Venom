package org.usfirst.frc.team1218.auton;

import org.usfirst.frc.team1218.subsystem.binIntake.BinIntake;
import org.usfirst.frc.team1218.subsystem.binIntake.C_SetBinIntake;
import org.usfirst.frc.team1218.subsystem.binIntake.C_SetClaw;
import org.usfirst.frc.team1218.subsystem.binIntake.C_SetRollLeft;
import org.usfirst.frc.team1218.subsystem.elevator.C_GoToBottom;
import org.usfirst.frc.team1218.subsystem.elevator.C_GoToTop;
import org.usfirst.frc.team1218.subsystem.elevator.C_WaitForTote;
import org.usfirst.frc.team1218.subsystem.fourBar.C_SeekPosition;
import org.usfirst.frc.team1218.subsystem.swerve.C_AutoDrive;
import org.usfirst.frc.team1218.subsystem.swerve.C_Index;
import org.usfirst.frc.team1218.subsystem.swerve.C_ZeroRobotHeading;
import org.usfirst.frc.team1218.subsystem.toteIntake.C_AutorunToteIntake;
import org.usfirst.frc.team1218.subsystem.toteIntake.ToteIntake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class C_TwoToteAuton extends CommandGroup {
    
    public  C_TwoToteAuton() {
    	System.out.println("Two Tote Auton Selected");
    	//get ready - index set heading , prep for bin pickup, turn on intake, pre position darts
    	addSequential(new C_ZeroRobotHeading());
    	addSequential(new C_Index());
    	addParallel(new C_SeekPosition(0.2));
    	addSequential(new C_GoToTop());
    	addSequential(new C_AutorunToteIntake(ToteIntake.TOTE_INTAKE_POWER));
    	
    	addSequential(new C_Index());
    	
    	//pickup first bin/tote combo
    	addParallel(new C_SlowFastDrive());
    	addSequential(new C_SetBinIntake(-0.5));
    	Timer.delay(0.3);
    	addSequential(new C_SetRollLeft(BinIntake.INTAKE_POWER));
    	addSequential(new C_SetClaw(false));
    	addSequential(new C_WaitForTote(20));
    	addSequential(new C_GoToBottom());
    	addSequential(new C_GoToTop());
    	
    	//pickup second tote
    	addSequential(new C_WaitForTote(20));
    	addSequential(new C_GoToBottom());
    	addSequential(new C_GoToTop());
    	
    	addSequential(new C_Index());
    	
    	addSequential(new C_AutoDrive(6, 90, 1.0));
    }
    
    public class C_SlowFastDrive extends CommandGroup {
        
        public  C_SlowFastDrive() {
            addSequential(new C_AutoDrive(4, 0, 0.8));
            addSequential(new C_AutoDrive(10, 0, .7));
        }
    }
}
