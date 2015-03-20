package org.usfirst.frc.team1218.commands.elevator;

import org.usfirst.frc.team1218.commands.toteIntake.AutorunToteIntake;
import org.usfirst.frc.team1218.subsystem.toteIntake.ToteIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DelayForAndPickupTote extends CommandGroup {
	
	public DelayForAndPickupTote(double delayTimeout)
	{
		addSequential(new AutorunToteIntake(ToteIntake.TOTE_INTAKE_POWER_GENTLE));
		addSequential(new DelayUntilToteDetected(delayTimeout));
		addSequential(new AutorunToteIntake(ToteIntake.TOTE_INTAKE_POWER_HOLD));
    	addSequential(new GoToBottom());
    	addSequential(new AutorunToteIntake(ToteIntake.TOTE_INTAKE_POWER_GENTLE));
    	addSequential(new GoToTop());
	}
}