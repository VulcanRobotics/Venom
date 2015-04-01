package org.usfirst.frc.team1218.commands.auton;

import org.usfirst.frc.team1218.commands.binIntake.SetBinIntake;
import org.usfirst.frc.team1218.commands.binIntake.SetClamp;
import org.usfirst.frc.team1218.commands.elevator.AutoStack;
import org.usfirst.frc.team1218.commands.swerve.AutoDrive;
import org.usfirst.frc.team1218.commands.swerve.VisionAlign;
import org.usfirst.frc.team1218.commands.toteIntake.SetToteIntake;
import org.usfirst.frc.team1218.subsystem.binIntake.BinIntake;
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
    	addParallel(new AutoStack());
    	addSequential(new AutoDrive(2, 360.0-135.0, -90, 1.0));
    	addSequential(new AutoDrive(4, 270.0, -90, 2.0));
    	
    	addSequential(new SetClamp(true));
    	addSequential(new SetBinIntake(BinIntake.INTAKE_POWER));
    	addSequential(new VisionAlign());
    	
    	
    	

    }
}
