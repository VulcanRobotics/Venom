package org.usfirst.frc.team1218.commands.auton;

import org.usfirst.frc.team1218.commands.binIntake.SetBinIntake;
import org.usfirst.frc.team1218.commands.elevator.DelayUntilToteDetected;
import org.usfirst.frc.team1218.commands.elevator.GoToBottom;
import org.usfirst.frc.team1218.commands.elevator.GoToTop;
import org.usfirst.frc.team1218.commands.fourBar.SeekPosition;
import org.usfirst.frc.team1218.commands.swerve.AutoDrive;
import org.usfirst.frc.team1218.commands.swerve.CalibrateOrientation;
import org.usfirst.frc.team1218.commands.swerve.ZeroRobotHeading;
import org.usfirst.frc.team1218.commands.toteIntake.AutorunToteIntake;
import org.usfirst.frc.team1218.subsystem.binIntake.BinIntake;
import org.usfirst.frc.team1218.subsystem.fourBar.FourBar;
import org.usfirst.frc.team1218.subsystem.toteIntake.ToteIntake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class Auton_TwoTote extends CommandGroup {
    
	final double startTime;
	
	double currentTime() {
		return Timer.getFPGATimestamp() - startTime;
	}
	
    public  Auton_TwoTote() {
    	
    	startTime = Timer.getFPGATimestamp();
    	
    	System.out.println("Two Tote Auton Selected");
    	//get ready - index set heading , prep for bin pickup, turn on intake, pre position darts
    	addSequential(new ZeroRobotHeading());
    	addSequential(new CalibrateOrientation());
    	addParallel(new SeekPosition(0.2));
    	addSequential(new GoToTop());
    
    	
    	//pickup first bin/tote combo

    	addSequential(new AutorunToteIntake(ToteIntake.TOTE_INTAKE_POWER_GENTLE));
    	addSequential(new SetBinIntake(BinIntake.INTAKE_POWER));
    	addParallel(new AutoDrive(1.5, 0, 0.7));
    	Timer.delay(0.5);
    	addParallel(new SeekPosition(FourBar.PID_HIGH_POSITION));

    	addSequential(new DelayUntilToteDetected(20));
    	addSequential(new GoToBottom());
    	addSequential(new GoToTop());
    	addParallel(new C_SlowFastDrive());
    	
    	//pickup second tote
    	addSequential(new DelayUntilToteDetected(20));
    	addSequential(new GoToBottom());
    	addSequential(new GoToTop());

    	//stop intakes
    	addSequential(new AutorunToteIntake(0.0));
    	addSequential(new SetBinIntake(0.0));  
    	
    	//drive to auto zone
    	addSequential(new CalibrateOrientation());
    	addSequential(new AutoDrive(8, 90, 1.0));
    	
    	System.out.println("done 2 tote auton. Total completion time: " + currentTime());
    	
    }
    
    public class C_SlowFastDrive extends CommandGroup {
        
        public  C_SlowFastDrive() {
            addSequential(new AutoDrive(4, 0, 0.8));
            addSequential(new AutoDrive(10, 0, .7));
        }
    }
}
