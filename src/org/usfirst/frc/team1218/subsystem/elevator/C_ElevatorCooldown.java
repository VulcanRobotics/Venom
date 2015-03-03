package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiol-mahon
 */
public class C_ElevatorCooldown extends Command {

	private final double timeout;
	
    public C_ElevatorCooldown(double timeout) {
        requires(Robot.elevator);
        this.timeout = timeout;
    }

    protected void initialize() {
    	Robot.elevator.setPower(0.0);
    	Timer.delay(timeout);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    	System.out.println("Elevator Cooldown complete.");
    }
    
    protected void interrupted() {
    	
    }
}
