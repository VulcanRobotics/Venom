package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiolmahon
 */
public class C_ToggleStableMode extends Command {
	
    public C_ToggleStableMode() {
        requires(Robot.swerveDrive);
    }
    
    protected void initialize() {
    	Robot.swerveDrive.module.stream().forEach(
    		m -> m.setStableMode(!m.isStableMode())
    	);
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
