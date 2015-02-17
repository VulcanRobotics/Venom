package org.usfirst.frc.team1218.subsystem.hooks;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiol-mahon
 */
public class C_DeployHooks extends Command {

    public C_DeployHooks() {
        requires(Robot.hooks);
    	System.out.println("Hooks Deployed");
    }

    protected void initialize() {
        Robot.hooks.deployHooks(true);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.hooks.deployHooks(false);
    }

    protected void interrupted() {
    	end();
    }
}
