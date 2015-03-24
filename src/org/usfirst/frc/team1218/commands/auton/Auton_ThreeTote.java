package org.usfirst.frc.team1218.commands.auton;

import org.usfirst.frc.team1218.commands.binIntake.SetBinIntake;
import org.usfirst.frc.team1218.commands.binIntake.SetRollLeft;
import org.usfirst.frc.team1218.commands.elevator.DelayUntilToteDetected;
import org.usfirst.frc.team1218.commands.elevator.GoToBottom;
import org.usfirst.frc.team1218.commands.elevator.GoToTop;
import org.usfirst.frc.team1218.commands.fourBar.SeekPosition;
import org.usfirst.frc.team1218.commands.swerve.AutoDrive;
import org.usfirst.frc.team1218.commands.swerve.CalibrateModules;
import org.usfirst.frc.team1218.commands.toteIntake.SetToteIntake;
import org.usfirst.frc.team1218.subsystem.binIntake.BinIntake;
import org.usfirst.frc.team1218.subsystem.toteIntake.ToteIntake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author afiolmahon
 * @author lcook
 */
public class Auton_ThreeTote extends CommandGroup {
    
	private final double startTime;
	
	private double currentTime() {
		return Timer.getFPGATimestamp() - startTime;
	}
	
    public  Auton_ThreeTote() {
    	startTime = Timer.getFPGATimestamp();
    	addSequential(new Auton_Calibrate());
    	//get ready - index set heading , prep for bin pickup, turn on intake, pre position darts
    	addSequential(new SetBinIntake(-0.6));
    	addParallel(new SeekPosition(0.2));
    	addSequential(new SetToteIntake(ToteIntake.TOTE_INTAKE_POWER));
    	
    	
    	System.out.println("done index, about to start driving to first tote. Time: " + currentTime());
    	//pickup first bin/tote combo
    	addParallel(new AutoDrive(3, 270, -90.0, 1.25));
    	
    	addSequential(new SetRollLeft(BinIntake.INTAKE_POWER));
    	addSequential(new DelayUntilToteDetected(20));
    	addSequential(new GoToBottom());
    	addSequential(new GoToTop());
    	
    	//pickup second tote
    	System.out.println("Done picking up first tote. Time: " + currentTime());
    	addParallel(new AutoDrive(4.5, 270, -90.0, 0.3));
    	
    	addSequential(new CalibrateModules());
    	addSequential(new DelayUntilToteDetected(20));
    	addSequential(new GoToBottom());
    	addParallel(new SeekPosition(0.85));
    	addSequential(new GoToTop());
    	
    	//pickup third tote
    	System.out.println("Done picking up second tote. Time: " + currentTime());
    	addParallel(new AutoDrive(4.5, 270, -90.0, 0.7));
    	
    	addSequential(new CalibrateModules());
    	addSequential(new DelayUntilToteDetected(20));
    	addParallel(new GoToBottom());
    	
    	System.out.println("have third tote in robot. Time: " + currentTime());
    	//drive to auto zone
    	addSequential(new SetToteIntake(0));
    	addSequential(new SetBinIntake(0.2));
    	addSequential(new AutoDrive(8, 0, -90.0, 0.9));
    	
    	//drop stack
    	System.out.println("in auto zone. Time: " + currentTime());
    	addSequential(new GoToBottom());
    	addSequential(new SetToteIntake(-ToteIntake.TOTE_INTAKE_POWER));
    	addSequential(new AutoDrive(4, 180, -90.0, 0.7));
    	addSequential(new SetToteIntake(0));
    	
    	System.out.println("done three tote autonomous. Total completion time: " + currentTime());
    	
    }
}
