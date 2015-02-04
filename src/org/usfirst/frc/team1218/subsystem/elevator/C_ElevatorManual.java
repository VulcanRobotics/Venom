package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.OI;
import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiol-mahon
 */
public class C_ElevatorManual extends Command {

    public C_ElevatorManual() {
        requires(Robot.elevator);
    }

    protected void initialize() {
    	Robot.elevator.configureElevatorMotorControllersForSpeedControl();
    }

    protected void execute() {
    	double power = 0.0;
    	power += OI.elevatorRaise.get() ? 1 : 0;
    	power += OI.elevatorLower.get() ? -1 : 0;
    	Robot.elevator.setElevatorSpeed(power);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	
    }

    protected void interrupted() {
    	end();
    }
    
   
}
