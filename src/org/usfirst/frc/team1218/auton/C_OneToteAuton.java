package org.usfirst.frc.team1218.auton;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.subsystem.elevator.C_GoToBottom;
import org.usfirst.frc.team1218.subsystem.elevator.C_GoToTop;
import org.usfirst.frc.team1218.subsystem.elevator.C_WaitForTote;
import org.usfirst.frc.team1218.subsystem.swerve.C_AutoDrive;
import org.usfirst.frc.team1218.subsystem.swerve.C_Index;
import org.usfirst.frc.team1218.subsystem.swerve.C_ZeroRobotHeading;
import org.usfirst.frc.team1218.subsystem.toteIntake.C_AutorunToteIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class C_OneToteAuton extends CommandGroup {
    
    public  C_OneToteAuton() {
    	//get ready
        addSequential(new C_Index());
        addSequential(new C_ZeroRobotHeading());
        
        //intake tote
        addSequential(new C_AutorunToteIntake(Robot.toteIntake.TOTE_INTAKE_POWER));
        addSequential(new C_WaitForTote(5));
        addSequential(new C_GoToBottom());
        addSequential(new C_GoToTop());
        
        //stop intakes
        addSequential(new C_AutorunToteIntake(0.0));
        
        //drive to autozone
        addSequential(new C_AutoDrive(7, 90, 1));
    }
}
