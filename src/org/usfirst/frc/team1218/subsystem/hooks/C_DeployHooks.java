package org.usfirst.frc.team1218.subsystem.hooks;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_DeployHooks extends Command {

    public C_DeployHooks(boolean state) {
        requires(Robot.hooks);
        Robot.hooks.deployHooks(state);
    	System.out.println("Hooks Deployed: " + state);
    }

    protected void initialize() {
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
