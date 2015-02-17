package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.auton.C_Wait;
import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.subsystem.swerve.C_AutoDrive;
import org.usfirst.frc.team1218.subsystem.swerve.math.Vector;
import org.usfirst.frc.team1218.subsystem.toteIntake.C_AutorunToteIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;
/**
 *
 */
public class C_PickupTote extends CommandGroup {
    
    public  C_PickupTote() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	addSequential(new C_GoToClearance());
    	addParallel(new C_AutoDrive(new Vector(5.0, 0), 0));
    	addSequential(new C_AutorunToteIntake(Robot.toteIntake.TOTE_INTAKE_POWER));
    	addSequential(new C_Wait(2));
    	addSequential(new C_GoToBottom());
    	addSequential(new C_AutorunToteIntake(0.0));
    	addSequential(new C_GoToClearance());
    	

    }
}
