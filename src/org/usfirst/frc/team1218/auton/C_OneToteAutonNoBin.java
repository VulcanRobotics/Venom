package org.usfirst.frc.team1218.auton;

import org.usfirst.frc.team1218.subsystem.binIntake.C_SetBinIntake;
import org.usfirst.frc.team1218.subsystem.elevator.C_GoToBottom;
import org.usfirst.frc.team1218.subsystem.elevator.C_GoToTop;
import org.usfirst.frc.team1218.subsystem.elevator.C_WaitForTote;
import org.usfirst.frc.team1218.subsystem.fourBar.C_SeekPosition;
import org.usfirst.frc.team1218.subsystem.swerve.C_AutoDrive;
import org.usfirst.frc.team1218.subsystem.swerve.C_Index;
import org.usfirst.frc.team1218.subsystem.swerve.C_ZeroRobotHeading;
import org.usfirst.frc.team1218.subsystem.toteIntake.C_AutorunToteIntake;
import org.usfirst.frc.team1218.subsystem.toteIntake.ToteIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class C_OneToteAutonNoBin extends CommandGroup {
    
    public  C_OneToteAutonNoBin() {
    	//get ready
        addSequential(new C_Index());
        addSequential(new C_ZeroRobotHeading());
        
        //intake tote
        addParallel(new C_AutoDrive(3, 0, 0.8));
        addSequential(new C_SetBinIntake(-0.7));
        addSequential(new C_AutorunToteIntake(ToteIntake.TOTE_INTAKE_POWER));
        addSequential(new C_WaitForTote(5), 5);
        addSequential(new C_GoToBottom());
        addParallel(new C_SeekPosition(0.7));
        addSequential(new C_GoToTop());
        
        //stop intakes
        addSequential(new C_AutorunToteIntake(0.0));
        addSequential(new C_SetBinIntake(0));
        //drive to autozone
        addSequential(new C_AutoDrive(10, 90, 1));
    }
}
