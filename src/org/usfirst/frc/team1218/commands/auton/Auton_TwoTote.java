package org.usfirst.frc.team1218.commands.auton;

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
import org.usfirst.frc.team1218.subsystem.toteIntake.ToteIntake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author afiolmahon
 * @author lcook
 */

public class Auton_TwoTote extends CommandGroup {
    
	private final double startTime;
	
	private double currentTime() {
		return Timer.getFPGATimestamp() - startTime;
	}
	
    public  Auton_TwoTote() {
    	startTime = Timer.getFPGATimestamp();
    	System.out.println("Two Tote Auton Selected");
    	addSequential(new Auton_Calibrate());
    	
    	//turn on intakes
    	addSequential(new SetClamp(false));
    	addParallel(new SetToteIntake(ToteIntake.TOTE_INTAKE_POWER_GENTLE));
    	addParallel(new SetBinIntake(BinIntake.CONTINOUS_HOLD_POWER));
    	addParallel(new SeekPosition(FourBar.PID_HIGH_POSITION));
    	
    	//pickup first bin/tote combo
		addSequential(new DelayUntilToteDetected(5.0));
		addParallel(new AutoStack(1));

		//Go To Second Tote
    	addSequential(new AutoDrive(5.2, 270.0, -90.0, 2.0));
    	addSequential(new VisionAlign(), 3.0);
    	addParallel(new AutoDrive(4.0, 270.0, -90.0, 1.0));
    	
    	//pickup second tote
    	addSequential(new DelayUntilToteDetected(5.0));
    	addParallel(new AutoStack(1));
    	
    	//stop intake
    	addSequential(new SetToteIntake(0.0));
    	
    	//drive to auto zone
    	addSequential(new AutoDrive(10.0, 0.0, -90.0, 2.0));
    	
    	System.out.println("done 2 tote auton. Total completion time: " + currentTime());
    }
}
