package org.usfirst.frc.team1218.commands.auton;

import org.usfirst.frc.team1218.commands.Delay;
import org.usfirst.frc.team1218.commands.binIntake.SetBinIntake;
import org.usfirst.frc.team1218.commands.binIntake.SetClamp;
import org.usfirst.frc.team1218.commands.elevator.AutoStack;
import org.usfirst.frc.team1218.commands.elevator.DelayUntilToteDetected;
import org.usfirst.frc.team1218.commands.fourBar.SeekPosition;
import org.usfirst.frc.team1218.commands.swerve.AutoDrive;
import org.usfirst.frc.team1218.commands.swerve.VisionAlign;
import org.usfirst.frc.team1218.commands.toteIntake.SetToteIntake;
import org.usfirst.frc.team1218.subsystem.binIntake.BinIntake;
import org.usfirst.frc.team1218.subsystem.fourBar.FourBar;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author afiolmahon
 * @author lcook
 */

public class Auton_TwoTote extends CommandGroup {
    
    public Auton_TwoTote() {
    	//turn on intakes
    	addParallel(new SetClamp(BinIntake.CLOSED));
    	addParallel(new SetBinIntake(BinIntake.CONTINOUS_HOLD_POWER));
    	addParallel(new SetToteIntake(1.0));
    	addParallel(new SetBinIntake(BinIntake.INTAKE_POWER));
    	addSequential(new Auton_Calibrate(false));
    	
    	addParallel(new SeekPosition(FourBar.PID_HIGH_POSITION));
    	class FirstDrive extends CommandGroup {
    	     FirstDrive() {  
    	    	 addParallel(new AutoStack(1));
    	         addSequential(new AutoDrive(3.0, 270.0, -90, 1.4));
    	     }
    	}
    	addSequential( new FirstDrive());
    	addSequential(new VisionAlign(), 0.5);
    	addParallel(new SetToteIntake(2.0));
    	addParallel(new AutoDrive(4.0, 270, -90.0, 0.9));
		addSequential(new DelayUntilToteDetected(4.0));
		addParallel(new AutoStack(1));
		addSequential(new Delay(3));
		addSequential(new AutoDrive(5, 0, -90, 2.2));
    }
}
