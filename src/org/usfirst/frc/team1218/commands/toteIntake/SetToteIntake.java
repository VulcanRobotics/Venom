package org.usfirst.frc.team1218.commands.toteIntake;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * @author afiol-mahon
 */
public class SetToteIntake extends Command {
	
	private double power;
	
    public SetToteIntake(double power) {
    	requires(Robot.toteIntake);
    	this.power = power;
    }
    
    protected void initialize() {
    	Robot.toteIntake.setIntakePower(power);
    }
    
    protected void execute() {}
    
    protected boolean isFinished() {
        return true;
    }
    
    protected void end() {}
    
    protected void interrupted() {}
}
