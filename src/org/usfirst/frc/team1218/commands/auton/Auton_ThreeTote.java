package org.usfirst.frc.team1218.commands.auton;

import org.usfirst.frc.team1218.commands.Print;
import org.usfirst.frc.team1218.commands.binIntake.SetBinIntake;
import org.usfirst.frc.team1218.commands.elevator.AutoStack;
import org.usfirst.frc.team1218.commands.elevator.DelayUntilToteDetected;
import org.usfirst.frc.team1218.commands.elevator.GoToBottom;
import org.usfirst.frc.team1218.commands.fourBar.SeekPosition;
import org.usfirst.frc.team1218.commands.swerve.AutoDrive;
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
public class Auton_ThreeTote extends CommandGroup {
    
	private final double startTime;
	
	private double currentTime() {
		return Timer.getFPGATimestamp() - startTime;
	}
	
    public  Auton_ThreeTote() {
    	startTime = Timer.getFPGATimestamp();
    	
    	System.out.println("Three Tote Auton Selected");
    	
    	//turn on intakes
    	addParallel(new SetToteIntake(ToteIntake.TOTE_INTAKE_POWER_GENTLE));
    	addParallel(new SetBinIntake(BinIntake.CONTINOUS_HOLD_POWER));
    	
    	addSequential(new Auton_Calibrate());
    	addSequential(new Print("Three Tote Auton Calibrate done, time: " + Timer.getMatchTime()));
       	addParallel(new SeekPosition(FourBar.PID_HIGH_POSITION));
    	
    	//pickup first bin/tote combo
		addSequential(new DelayUntilToteDetected(5.0));
		addParallel(new AutoStack(1));

		//Go To Second Tote
    	addSequential(new AutoDrive(5.2, 270.0, -90.0, 2.0));
    	//addSequential(new VisionAlign(), 3.0);
    	addSequential(new Print("ready to drive into second tote, time: " + Timer.getMatchTime()));
    	//drive into second tote and pick it up
    	addSequential(new AutoDrive(2.0, 270.0, -90.0, 2.0));
    	addParallel(new AutoStack(1));
    	
    	addSequential(new Print("ready to drive around bin, time: " + Timer.getMatchTime()));
    	//drive around 2nd bin to third tote
    	addSequential(new AutoDrive(2.0, 0.0, -90.0, 2.0)); // moves left robot-centric to get away from bin
    	addSequential(new AutoDrive(3.0, 270.0, -45.0, 2.0)); //forward robot-centric, moves past bin
    	addSequential(new AutoDrive(2.0, 180.0, -45.0, 2.0)); //right robot centric, moves back to original path to pickup 3rd tote
    	addSequential(new AutoDrive(4.0, 270.0, -90.0, 2.0)); // moves forward to thirds tote
    	//addSequential(new VisionAlign(), 3.0);
    	addSequential(new Print("ready to drive into third tote, time: " + Timer.getMatchTime()));
    	//get third tote in robot, lower other two on top
    	addSequential(new AutoDrive(4.0, 270.0, -90.0, 2.0));
    	addParallel(new GoToBottom());

    	//drive to auto zone
    	addSequential(new AutoDrive(10.0, 0.0, -90.0, 2.0));
    	
    	//spit out stack and drive back
    	addSequential(new SetToteIntake(-ToteIntake.TOTE_INTAKE_POWER));
    	addSequential(new AutoDrive(5.0, 90.0, -90.0, 2.0));
    	addSequential(new Print("Three Tote Auton done, time: " + Timer.getMatchTime()));

    	
    }
}
