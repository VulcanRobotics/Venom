package org.usfirst.frc.team1218.commands.auton;

import org.usfirst.frc.team1218.commands.Delay;
import org.usfirst.frc.team1218.commands.binIntake.CloseClampWhenIntakesHitTote;
import org.usfirst.frc.team1218.commands.binIntake.SetBinIntake;
import org.usfirst.frc.team1218.commands.binIntake.SetClamp;
import org.usfirst.frc.team1218.commands.binIntake.SetRollLeft;
import org.usfirst.frc.team1218.commands.elevator.AutoStack;
import org.usfirst.frc.team1218.commands.elevator.DelayUntilToteDetected;
import org.usfirst.frc.team1218.commands.elevator.ElevatorHoldPositionWhenToteDetected;
import org.usfirst.frc.team1218.commands.fourBar.SeekPosition;
import org.usfirst.frc.team1218.commands.swerve.AutoDrive;
import org.usfirst.frc.team1218.commands.swerve.MaintainRobotHeading;
import org.usfirst.frc.team1218.commands.swerve.SetHeadingController;
import org.usfirst.frc.team1218.commands.swerve.VisionAlign;
import org.usfirst.frc.team1218.commands.toteIntake.AutoToteIntake;
import org.usfirst.frc.team1218.commands.toteIntake.SetToteIntake;
import org.usfirst.frc.team1218.subsystem.binIntake.BinIntake;
import org.usfirst.frc.team1218.subsystem.elevator.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;


/**
 * @author afiolmahon
 * @author lcook
 */
public class Auton_ThreeTote extends CommandGroup {
    	
    public  Auton_ThreeTote() {    	
    	System.out.println("Three Tote Auton Selected");
    	addSequential(new SetClamp(BinIntake.OPEN));
    	addParallel(new Auton_Calibrate(false));
    	addSequential(new Delay(0.5));
    	
    	addParallel(new AutoToteIntake());
    	addParallel(new AutoStack(1));
    	
    	addParallel(new SetClamp(BinIntake.CLOSED));
    	addParallel(new SeekPosition(0.2));
    	
    	addSequential(new AutoDrive(1.5, 270, -90, 2.0));
    	addSequential(new SwivelAndDrop(2.5, -60, -30));
    	addParallel(new SetRollLeft(-1.0));
    	
    	class WaitForFourbarAndRotate extends CommandGroup{
    		WaitForFourbarAndRotate(){
    			addParallel(new SeekPosition(.135, .13, .15));
    	    	addSequential(new MaintainRobotHeading(-80));
    	    	addParallel(new Delay(0.4));
    	    	addSequential(new SetHeadingController(-90));
    	    	addSequential(new SetBinIntake(0.9));
    	    	//addSequential(new VisionAlign(), 0.5); //TODO: add this back in but don't make it delay seqeuncing
    		}
    	}
    	addSequential(new WaitForFourbarAndRotate());
    	    	
    	addParallel(new CloseClampWhenIntakesHitTote());
    	//addParallel(new AutoDrive(7.0, 270, -90, 1.3));
    	addParallel(new VisionAlign(-1.0));
    	addSequential(new DelayUntilToteDetected(10.0));
    	
    	class PickupAndDrive extends CommandGroup{
    		PickupAndDrive(){
    			addParallel(new AutoStack(1));
    			addParallel(new AutoDrive(2.0, 270, -90, 0.0), 0.5); //stop
            	addSequential(new SetClamp(BinIntake.CLOSED));
            	addParallel(new SeekPosition(1.0, 0.55, 0.9));
    		}
    	}
    	addSequential(new PickupAndDrive());
    	addParallel(new SeekPosition(0.8, 0.7, 0.9));
    	addSequential(new SetBinIntake(BinIntake.CONTINOUS_HOLD_POWER));
    	
    	addParallel(new VisionAlign(-1.4));
    	//addParallel(new AutoDrive(6.0, 270, -90, 1.4));
    	addSequential(new DelayUntilToteDetected(6.0));
    	addParallel(new ElevatorHoldPositionWhenToteDetected(Elevator.BOTTOM_SOFT_LIMT));
    	
    	addSequential(new AutoDrive(12.0, 0, -90, 2.5));
    	addSequential(new SetToteIntake(-0.8));
    	addSequential(new AutoDrive(3.0, 90, -90, 2.4));
    }
}
