package org.usfirst.frc.team1218.commands.auton;

import org.usfirst.frc.team1218.commands.Print;
import org.usfirst.frc.team1218.commands.binIntake.SetClamp;
import org.usfirst.frc.team1218.commands.elevator.ReferenceElevator;
import org.usfirst.frc.team1218.commands.swerve.AutonZeroHeading;
import org.usfirst.frc.team1218.commands.swerve.CalibrateModules;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author afiolmahon
 * @author lcook
 */
public class Auton_Calibrate extends CommandGroup {
    
    public  Auton_Calibrate() {
    	addParallel(new ReferenceElevator());
        addSequential(new AutonZeroHeading());
        addParallel(new CalibrateModules());
    	addSequential(new SetClamp(false));
    	addSequential(new Print("Auton_Calibrate Complete"));
    	System.out.println("Auton_Calibrate Complete");
    }
}
