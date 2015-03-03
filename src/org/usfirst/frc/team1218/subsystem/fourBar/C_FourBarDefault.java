package org.usfirst.frc.team1218.subsystem.fourBar;

import org.usfirst.frc.team1218.robot.OI;
import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiol-mahon
 */
public class C_FourBarDefault extends Command {

    public C_FourBarDefault() {
        requires(Robot.fourBar);
    }

    protected void initialize() {
    	Robot.fourBar.dartEnablePID(false);
    }

    protected void execute() {
    	double power = OI.getFourBarControlAxis();
    	if (Math.abs(power) > 0.1) {
    		Robot.fourBar.setDartPower(power);
    	} else {
    		Robot.fourBar.setDartPower(0.0);
    	}
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.fourBar.setDartPower(0.0);
    }
    
    protected void interrupted() {
    	end();
    }
}
