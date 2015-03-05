package org.usfirst.frc.team1218.commands.elevator;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiol-mahon
 */
public class ElevatorCooldown extends Command {

	private final double timeout;
	
    public ElevatorCooldown(double timeout) {
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
