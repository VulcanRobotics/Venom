package org.usfirst.frc.team1218.commands.auton;

import org.usfirst.frc.team1218.commands.binIntake.SetBinIntake;
import org.usfirst.frc.team1218.commands.elevator.DelayForAndPickupTote;
import org.usfirst.frc.team1218.commands.elevator.DelayUntilToteDetected;
import org.usfirst.frc.team1218.commands.elevator.GoToBottom;
import org.usfirst.frc.team1218.commands.elevator.GoToTop;
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
 * @author afiol-mahon
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
    	addSequential(new SetToteIntake(ToteIntake.TOTE_INTAKE_POWER_GENTLE));
    	addSequential(new SetBinIntake(BinIntake.INTAKE_POWER));
    	addParallel(new SeekPosition(FourBar.PID_AUTON_START_POSITION));
    	
    	//pickup first bin/tote combo
    	addParallel(new SeekPosition(FourBar.PID_HIGH_POSITION));
    	addSequential(new SetBinIntake(BinIntake.CONTINOUS_HOLD_POWER));
    	
    	addParallel(new DelayForAndPickupTote(0.5));
    	addSequential(new AutoDrive(5.2, 270.0, -90.0, 2.0));
    	
    	addSequential(new VisionAlign(), 3.0);
    	addSequential(new SetToteIntake(ToteIntake.TOTE_INTAKE_POWER_GENTLE));
    	addParallel(new AutoDrive(4.0, 270.0, -90.0, 2.0));
    	
    	//pickup second tote
    	addSequential(new DelayUntilToteDetected(5));
    	addSequential(new GoToBottom());
    	addSequential(new SetToteIntake(0));
    	addParallel(new GoToTop());
    	//stop intakes
    	
    	addSequential(new SetBinIntake(BinIntake.CONTINOUS_HOLD_POWER));  
    	//drive to auto zone
    	addSequential(new AutoDrive(10.0, 0.0, -90.0, 2.0));
    	System.out.println("done 2 tote auton. Total completion time: " + currentTime());
    }
}
