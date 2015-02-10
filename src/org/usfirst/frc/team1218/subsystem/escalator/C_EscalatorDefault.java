package org.usfirst.frc.team1218.subsystem.escalator;

import org.usfirst.frc.team1218.robot.OI;
import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiol-mahon
 */
public class C_EscalatorDefault extends Command {

    public C_EscalatorDefault() {
        requires(Robot.escalator);
    }

    protected void initialize() {
    	Robot.escalator.dartManualMode();
    	Robot.escalator.enableDarts();
    }

    protected void execute() {
    	joystickEscalatorControl(OI.getEscalatorControlAxis());
    	//setBinIntakeByButton();
    	//setGrabberByButton();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.escalator.setDartPower(0.0);
		Robot.escalator.disableDarts();
    }
    
    protected void interrupted() {
    	end();
    }
    
    /**
     * Method binds a joystick axis to escalator motor
     */
    private void joystickEscalatorControl(double pow) {
    	if (Math.abs(pow) > 0.1) {
    		Robot.escalator.setDartPower(pow);
    	} else {
    		Robot.escalator.setDartPower(0.0);
    	}
    }
    
}
