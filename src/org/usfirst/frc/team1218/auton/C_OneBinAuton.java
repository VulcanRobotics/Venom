package org.usfirst.frc.team1218.auton;

import org.usfirst.frc.team1218.subsystem.binIntake.BinIntake;
import org.usfirst.frc.team1218.subsystem.binIntake.C_SetBinIntake;
import org.usfirst.frc.team1218.subsystem.fourBar.C_SeekPosition;
import org.usfirst.frc.team1218.subsystem.swerve.C_AutoDrive;
import org.usfirst.frc.team1218.subsystem.swerve.C_Index;
import org.usfirst.frc.team1218.subsystem.swerve.C_ZeroRobotHeading;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 *grabs bin, raises it, and drives backwards
 *
 */
public class C_OneBinAuton extends CommandGroup {
    
    public  C_OneBinAuton() {
    	addSequential(new C_ZeroRobotHeading());
    	addSequential(new C_SetBinIntake(BinIntake.INTAKE_POWER));
        addSequential(new C_Index());
        Timer.delay(0.5);
        addSequential(new C_SetBinIntake(BinIntake.CONTINOUS_HOLD_POWER));
        addParallel(new C_SeekPosition(0.5));
        addSequential(new C_AutoDrive(10, 180, 0.7));
    }
}
