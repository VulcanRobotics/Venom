package org.usfirst.frc.team1218.subsystem.hooks;

import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Hooks extends Subsystem {
    private Solenoid hookDeploy;
    
    public void initDefaultCommand() {
        setDefaultCommand(new C_HooksDefault());
    }
    
    public Hooks() {
    	hookDeploy = new Solenoid(RobotMap.HOOK_DEPLOY_SOLENOID);
    }
    
    public void raiseHooks() {
    	hookDeploy.set(true);//TODO Verify this goes up
    }
    
    public void lowerHooks() {
    	hookDeploy.set(false);//TODO Verify this goes down
    }
}

