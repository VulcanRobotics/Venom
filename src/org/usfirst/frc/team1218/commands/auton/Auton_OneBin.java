package org.usfirst.frc.team1218.commands.auton;

import org.usfirst.frc.team1218.commands.binIntake.SetBinIntake;
import org.usfirst.frc.team1218.commands.fourBar.SeekPosition;
import org.usfirst.frc.team1218.commands.swerve.AutoDrive;
import org.usfirst.frc.team1218.subsystem.binIntake.BinIntake;
import org.usfirst.frc.team1218.subsystem.fourBar.FourBar;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *@author afiolmahon
 *@author lcook
 *grabs bin, raises it, and drives backwards
 *
 */
public class Auton_OneBin extends CommandGroup {
    
    public  Auton_OneBin() {
    	addSequential(new Auton_Calibrate());
    	addSequential(new SetBinIntake(BinIntake.INTAKE_POWER));//TODO see how this impacts auton
        addSequential(new SetBinIntake(BinIntake.CONTINOUS_HOLD_POWER));
        addParallel(new SeekPosition(FourBar.PID_HIGH_POSITION));
        addSequential(new AutoDrive(10, 0, 180.0, 1.0));
    }
}
