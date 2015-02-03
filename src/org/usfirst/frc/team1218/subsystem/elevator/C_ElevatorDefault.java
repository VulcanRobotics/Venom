package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.OI;
import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiol-mahon
 */
public class C_ElevatorDefault extends Command {

    public C_ElevatorDefault() {
        requires(Robot.elevator);
    }

    protected void initialize() {
    	configureElevatorMotorControllersForPID();
    }

    protected void execute() {
    	setToteIntakeByButton();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.elevator.setIntakePower(0.0);
    }

    protected void interrupted() {
    	end();
    }
    
    private void setToteIntakeByButton() {
    	if (OI.elevatorRunToteIntake.get()) {
        	Robot.elevator.setIntakePower(Elevator.TOTE_INTAKE_POWER);
    	} else {
        	Robot.elevator.setIntakePower(0.0);
    	}
    }
}
