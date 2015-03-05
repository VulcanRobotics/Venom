package org.usfirst.frc.team1218.commands.auton;

import org.usfirst.frc.team1218.commands.elevator.DelayUntilToteDetected;
import org.usfirst.frc.team1218.commands.elevator.GoToBottom;
import org.usfirst.frc.team1218.commands.elevator.GoToTop;
import org.usfirst.frc.team1218.commands.swerve.AutoDrive;
import org.usfirst.frc.team1218.commands.swerve.CalibrateOrientation;
import org.usfirst.frc.team1218.commands.swerve.ZeroRobotHeading;
import org.usfirst.frc.team1218.commands.toteIntake.AutorunToteIntake;
import org.usfirst.frc.team1218.subsystem.toteIntake.ToteIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auton_OneTote extends CommandGroup {
    
    public  Auton_OneTote() {
    	//get ready
        addSequential(new CalibrateOrientation());
        addSequential(new ZeroRobotHeading());
        
        //intake tote
        addParallel(new AutoDrive(3, 0, 0.8));
        addSequential(new AutorunToteIntake(ToteIntake.TOTE_INTAKE_POWER));
        addSequential(new DelayUntilToteDetected(5), 5);
        addSequential(new GoToBottom());
        addSequential(new GoToTop());
        
        //stop intakes
        addSequential(new AutorunToteIntake(0.0));
        
        //drive to autozone
        addSequential(new AutoDrive(10, 90, 1));
    }
}
