package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.OI;
import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiol-mahon
 */
public class C_ElevatorManual extends Command {

    public C_ElevatorDefault() {
        requires(Robot.elevator);
    }

    protected void initialize() {
    	Robot.elevator.configureElevatorMotorControllersForSpeedControl();
    }

    protected void execute() {
    	
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
