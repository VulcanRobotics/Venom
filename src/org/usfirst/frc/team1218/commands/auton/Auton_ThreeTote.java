package org.usfirst.frc.team1218.commands.auton;

import org.usfirst.frc.team1218.commands.binIntake.CloseClampWhenIntakesHitTote;
import org.usfirst.frc.team1218.commands.binIntake.SetBinIntake;
import org.usfirst.frc.team1218.commands.binIntake.SetClamp;
import org.usfirst.frc.team1218.commands.binIntake.SetRollLeft;
import org.usfirst.frc.team1218.commands.elevator.AutoStack;
import org.usfirst.frc.team1218.commands.elevator.DelayUntilToteDetected;
import org.usfirst.frc.team1218.commands.elevator.ElevatorHoldPosition;
import org.usfirst.frc.team1218.commands.fourBar.SeekPosition;
import org.usfirst.frc.team1218.commands.swerve.AutoDrive;
import org.usfirst.frc.team1218.commands.swerve.MaintainRobotHeading;
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
    	addSequential(new Auton_Calibrate(false));

    	addParallel(new AutoToteIntake());
    	addParallel(new AutoStack(1));
    	
    	addParallel(new SetClamp(BinIntake.CLOSED));
    	addParallel(new SeekPosition(0.23));
    	
    	addSequential(new AutoDrive(1.5, 270, -90, 2.0));
    	addSequential(new MaintainRobotHeading(-30));
    	addSequential(new SetClamp(BinIntake.OPEN));
    	addParallel(new SetRollLeft(-1.0));
    	addSequential(new MaintainRobotHeading(-100));
    	addParallel(new SeekPosition(.15, .132, .16));
    	
    	addSequential(new SetBinIntake(0.9));
    	addSequential(new VisionAlign(), 1.0);
    	addParallel(new CloseClampWhenIntakesHitTote());
    	addParallel(new AutoDrive(7.0, 270, -90, 1.3));
    	addSequential(new DelayUntilToteDetected(10.0));
    	
    	class PickupAndDrive extends CommandGroup{
    		PickupAndDrive(){
    			addParallel(new AutoStack(1));
    			
            	addSequential(new SetClamp(BinIntake.CLOSED));
            	addParallel(new SeekPosition(0.8));
     	
            	addSequential(new AutoDrive(2.0, 270, -90, 2.0));
    		}
    	}
    	addSequential(new PickupAndDrive());
    	addSequential(new SetBinIntake(BinIntake.CONTINOUS_HOLD_POWER));
    	
    	//addSequential(new VisionAlign(), 1.0);
    	addParallel(new AutoDrive(6.0, 270, -90, 1.4));
    	addSequential(new DelayUntilToteDetected(4.0));
    	addParallel(new ElevatorHoldPosition(Elevator.BOTTOM_SOFT_LIMT));
    	
    	addSequential(new AutoDrive(4.0, 0, -90, 2.0));
    	addSequential(new SetToteIntake(-0.8));
    	addSequential(new AutoDrive(3.0, 90, -90, 2.4));
    }
}
