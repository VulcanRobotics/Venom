package org.usfirst.frc.team1218.commands.auton;

import org.usfirst.frc.team1218.commands.Delay;
import org.usfirst.frc.team1218.commands.binIntake.SetBinIntake;
import org.usfirst.frc.team1218.commands.binIntake.SetClamp;
import org.usfirst.frc.team1218.commands.elevator.AutoStack;
import org.usfirst.frc.team1218.commands.elevator.DelayUntilToteDetected;
import org.usfirst.frc.team1218.commands.elevator.ElevatorHoldPosition;
import org.usfirst.frc.team1218.commands.fourBar.SeekPosition;
import org.usfirst.frc.team1218.commands.swerve.AutoDrive;
import org.usfirst.frc.team1218.commands.toteIntake.SetToteIntake;
import org.usfirst.frc.team1218.subsystem.binIntake.BinIntake;
import org.usfirst.frc.team1218.subsystem.elevator.Elevator;
import org.usfirst.frc.team1218.subsystem.fourBar.FourBar;
import org.usfirst.frc.team1218.subsystem.toteIntake.ToteIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;


/**
 * @author afiolmahon
 * @author lcook
 */
public class Auton_ThreeTote extends CommandGroup {
    	
    public  Auton_ThreeTote() {    	
    	System.out.println("Three Tote Auton Selected");
    	
    	//turn on intakes
    	addParallel(new SetToteIntake(false, true, ToteIntake.TOTE_INTAKE_POWER));
    	addParallel(new SetBinIntake(0.0));
    	addSequential(new Auton_Calibrate());
    	
    	class FirstDrive extends CommandGroup {
    	     FirstDrive() {  
    	    	 addParallel(new AutoStack(1));
    	    	 addSequential(new AutoDrive(2, 360.0-135.0, -90, 1.0));
    	         addSequential(new AutoDriveVision(4, 270.0, -90, 2.0));
    	    	 }
    	}
    	FirstDrive firstDrive = new FirstDrive();
    	firstDrive.start();
    
    	addSequential(new SetBinIntake(BinIntake.INTAKE_POWER));
    	addSequential(new SetToteIntake(ToteIntake.TOTE_INTAKE_POWER));
        addSequential(new SetClamp(true));
    	
    	class SecondDrive extends CommandGroup{
    		SecondDrive(){
    			addParallel(new AutoDriveVision(7.5, 270.0, -90.0, 2.0));
    			addSequential(new DelayUntilToteDetected(2.0));
    			addParallel(new AutoStack(1));
    			addSequential(new SetClamp(false));
    			addSequential(new Delay(0.5));
    			addParallel(new SeekPosition(FourBar.PID_HIGH_POSITION));
    			addSequential(new Delay(0.5));
    			addSequential(new SetBinIntake(BinIntake.CONTINOUS_HOLD_POWER));
    		}
    	}
    	SecondDrive secondDrive = new SecondDrive();
    	secondDrive.start();
    	
    	addParallel(new AutoDrive(4.0, 270.0, -90, 1.2));
    	addSequential(new DelayUntilToteDetected(2.0));
    	addParallel(new ElevatorHoldPosition(Elevator.BOTTOM_SOFT_LIMT));
    	
    	addSequential(new AutoDrive(9.0, 0, -90, 2.0));
    	addSequential(new SetBinIntake(-BinIntake.INTAKE_POWER));
    	
    	addSequential(new AutoDrive(5.0, 90, -90, 2.0));
    }
}
