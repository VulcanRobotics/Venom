package org.usfirst.frc.team1218.subsystem.autonHooks;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiol-mahon
 */
public class C_DeployHooks extends Command {

	boolean deployHooks = true;
	
    public C_DeployHooks() {
        requires(Robot.hooks);
    	System.out.println("Hooks Deployed");
    }

    public C_DeployHooks(boolean deployHooks) {
        requires(Robot.hooks);
        this.deployHooks = deployHooks;
    	System.out.println("Hooks Deployed");
    }

    
    protected void initialize() {
        Robot.hooks.deployHooks(deployHooks);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    	
    }

    protected void interrupted() {
    	end();
    }
}
