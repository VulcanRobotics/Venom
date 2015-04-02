package org.usfirst.frc.team1218.commands.auton;

import org.usfirst.frc.team1218.commands.binGrabber.SetGrabber;
import org.usfirst.frc.team1218.commands.swerve.AutoDrive;
import org.usfirst.frc.team1218.subsystem.binGrabber.BinGrabber;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auton_Step extends CommandGroup {
    
    public  Auton_Step() {
    	addSequential(new SetGrabber(BinGrabber.REALEASED));
        addSequential(new Auton_Calibrate());
        addSequential(new AutoDrive(5, 180, 0, 2.0));
        addSequential(new SetGrabber(BinGrabber.HELD));
    }
}
