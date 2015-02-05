package org.usfirst.frc.team1218.subsystem.toteIntake;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiol-mahon
 */
public class C_RunToteIntake extends Command {
	
    public C_RunToteIntake() {
    	requires(Robot.toteIntake);
    }
    
    protected void initialize() {
    	Robot.toteIntake.setIntakePower(Robot.toteIntake.TOTE_INTAKE_POWER);
    }
    
    protected void execute() {
    }
    
    protected boolean isFinished() {
        return false;
    }
    
    protected void end() {
    	Robot.toteIntake.setIntakePower(0.0);
    }
    
    protected void interrupted() {
    	end();
    }
}
