package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.OI;
import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_ElevatorDefault extends Command {

    public C_ElevatorDefault() {
        requires(Robot.elevator);
    }

    protected void initialize() {
    }

    protected void execute() {
    	setToteIntakeByTrigger();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.elevator.setIntake(0.0);
    }

    protected void interrupted() {
    	end();
    }
    
    private void setToteIntakeByTrigger() {
    	if (OI.runToteIntake.get()) {
        	Robot.elevator.setIntake(Elevator.TOTE_INTAKE_POWER);
    	} else {
        	Robot.elevator.setIntake(0.0);
    	}
    }
}
