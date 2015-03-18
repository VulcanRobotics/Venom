package org.usfirst.frc.team1218.commands.auton;

import org.usfirst.frc.team1218.commands.binIntake.SetBinIntake;
import org.usfirst.frc.team1218.commands.fourBar.SeekPosition;
import org.usfirst.frc.team1218.commands.swerve.AutoDrive;
import org.usfirst.frc.team1218.subsystem.binIntake.BinIntake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 *grabs bin, raises it, and drives backwards
 *
 */
public class Auton_OneBin extends CommandGroup {
    
    public  Auton_OneBin() {
    	addSequential(new Auton_Calibrate());
    	addSequential(new SetBinIntake(BinIntake.INTAKE_POWER));//TODO see how this impacts auton
        Timer.delay(0.5);
        addSequential(new SetBinIntake(BinIntake.CONTINOUS_HOLD_POWER));
        addParallel(new SeekPosition(0.5));
        addSequential(new AutoDrive(10, 270, 270, 0.7));
    }
}
