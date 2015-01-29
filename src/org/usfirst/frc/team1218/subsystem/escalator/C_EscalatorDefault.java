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
    	joystickEscalatorControl();
    	setBinIntakeByButton();
    	setGrabberByButton();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.escalator.setDarts(0.0);
    	Robot.escalator.setIntake(0.0);
		Robot.escalator.openGrabber(false);
		Robot.escalator.disableDarts();
    }
    
    protected void interrupted() {
    	end();
    }
    
    private void joystickEscalatorControl() {
    	double pow = OI.getEscalatorControlAxis();
    	if (pow > 0.1) Robot.escalator.setDarts(pow);
    }
    
    private void setBinIntakeByButton() {
    	if (OI.runBinIntake.get()) {
    		Robot.escalator.setIntake(1.0);
    	} else {
        	Robot.escalator.setIntake(0.0);
    	}
    }
    
    private void setGrabberByButton() {
    	if (OI.openGrabber.get()) {
    		Robot.escalator.openGrabber(true);
    	} else {
    		Robot.escalator.openGrabber(false);
    	}
    }
}
