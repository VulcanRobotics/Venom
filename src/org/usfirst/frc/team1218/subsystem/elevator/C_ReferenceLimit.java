package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiol-mahon
 */
public class C_ReferenceLimit extends Command {

    public C_ReferenceLimit() {
        requires(Robot.elevator);
    }

    protected void initialize() {
    }

    protected void execute() {
    	System.out.println("Seeking Elevator Bottom Limit");
    	Robot.elevator.setPower(-Elevator.ELEVATOR_REFERENCING_POWER);
    }

    protected boolean isFinished() {
        return Robot.elevator.atReference();
    }

    protected void end() {
    	Robot.elevator.zeroPosition();
    	Robot.elevator.setPosition(0);
    }

    protected void interrupted() {
    	end();
    }
}
