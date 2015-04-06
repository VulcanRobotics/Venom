package org.usfirst.frc.team1218.commands.auton;

import org.usfirst.frc.team1218.commands.binGrabber.SetGrabber;
import org.usfirst.frc.team1218.commands.fourBar.SeekPosition;
import org.usfirst.frc.team1218.commands.swerve.AutoDrive;
import org.usfirst.frc.team1218.subsystem.binGrabber.BinGrabber;
import org.usfirst.frc.team1218.subsystem.fourBar.FourBar;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auton_Step extends CommandGroup {
    
    public  Auton_Step() {
    	addSequential(new SetGrabber(BinGrabber.REALEASED));
        addSequential(new Auton_Calibrate());
        addSequential(new AutoDrive(5, 180, 180, 2.0));
        addSequential(new SeekPosition(FourBar.PID_HIGH_POSITION));
    }
}
