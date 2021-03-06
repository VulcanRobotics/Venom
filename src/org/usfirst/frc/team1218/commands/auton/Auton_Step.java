package org.usfirst.frc.team1218.commands.auton;

import org.usfirst.frc.team1218.commands.Delay;
import org.usfirst.frc.team1218.commands.binGrabber.SetGrabber;
import org.usfirst.frc.team1218.commands.swerve.SetRawWheelAngle;
import org.usfirst.frc.team1218.commands.swerve.SetSwervePower;
import org.usfirst.frc.team1218.subsystem.binGrabber.BinGrabber;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auton_Step extends CommandGroup {
    
    public  Auton_Step() {
    	addSequential(new SetGrabber(BinGrabber.REALEASED));
        addSequential(new Auton_Calibrate(false));
        class AlignSwervesAndDelay extends CommandGroup{
        	AlignSwervesAndDelay(){
        		addParallel(new Delay(1.0));
        		addParallel(new SetRawWheelAngle(0));
        	}
        }
        addSequential(new AlignSwervesAndDelay());
        addSequential(new SetSwervePower(0.6, 1.2));
        //addSequential(new AutoDrive(3.0, 180, 180, 2.0));
        //addSequential(new Delay(8.0));
        //addSequential(new SeekPosition(FourBar.PID_HIGH_POSITION));
    }
}
