package org.usfirst.frc.team1218.commands.auton;

import org.usfirst.frc.team1218.commands.binIntake.SetBinIntake;
import org.usfirst.frc.team1218.commands.binIntake.SetClamp;
import org.usfirst.frc.team1218.commands.elevator.AutoStack;
import org.usfirst.frc.team1218.commands.elevator.DelayUntilToteDetected;
import org.usfirst.frc.team1218.commands.fourBar.SeekPosition;
import org.usfirst.frc.team1218.commands.swerve.AutoDrive;
import org.usfirst.frc.team1218.commands.toteIntake.SetToteIntake;
import org.usfirst.frc.team1218.subsystem.binIntake.BinIntake;
import org.usfirst.frc.team1218.subsystem.fourBar.FourBar;
import org.usfirst.frc.team1218.subsystem.toteIntake.ToteIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author afiolmahon
 * @author lcook
 */
public class Auton_OneTote extends CommandGroup {
    
    public  Auton_OneTote() {
    	//get ready
    	addSequential(new SetClamp(false));
    	addSequential(new Auton_Calibrate());
    	addParallel(new SetBinIntake(BinIntake.CONTINOUS_HOLD_POWER));
        addParallel(new SeekPosition(FourBar.PID_HIGH_POSITION));
    	addParallel(new SetToteIntake(ToteIntake.TOTE_INTAKE_POWER));

    	//Drive
        addParallel(new AutoDrive(3.0, 270.0, -90.0, 0.8));
    	
        //intake tote
        addSequential(new DelayUntilToteDetected(5.0));
        addParallel(new AutoStack(1));
        
        //stop intakes
        addSequential(new SetToteIntake(0.0));
        
        //drive to autozone
        addSequential(new AutoDrive(10.0, 0.0, -90.0, 2.0));
    }
}