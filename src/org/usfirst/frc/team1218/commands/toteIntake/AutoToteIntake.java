package org.usfirst.frc.team1218.commands.toteIntake;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.subsystem.toteIntake.ToteIntake;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoToteIntake extends Command {
	
    public AutoToteIntake() {
        requires(Robot.toteIntake);
    }
    
    protected void initialize() {
    }
    
    protected void execute() {
    	if (Robot.elevator.hasTote()) {
    		Robot.toteIntake.setPower(ToteIntake.TOTE_INTAKE_POWER_HOLD);
    	} else {
    		Robot.toteIntake.setPower(ToteIntake.TOTE_INTAKE_POWER);
    	}
    }
    
    protected boolean isFinished() {
        return false;
    }
    
    protected void end() {
    	Robot.toteIntake.setPower(0.0);
    }
    
    protected void interrupted() {
    	end();
    }
}
