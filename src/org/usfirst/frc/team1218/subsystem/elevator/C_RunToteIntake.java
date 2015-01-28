package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_RunToteIntake extends Command {

    public C_RunToteIntake() {
        requires(Robot.elevator);
    }

    protected void initialize() {
    	Robot.elevator.setIntake(1.0);
    	System.out.println("Tote Rollers Enabled");
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.elevator.setIntake(0.0);
    }

    protected void interrupted() {
    }
}
