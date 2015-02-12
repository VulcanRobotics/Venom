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
    }

    protected void execute() {
    	joystickEscalatorControl(OI.getFourBarControlAxis());
    	setBinIntakeByButton();
    	setGrabberByButton();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.fourBar.setDartPower(0.0);
    	Robot.fourBar.setIntake(0.0);
		Robot.fourBar.openGrabber(false);
    }
    
    protected void interrupted() {
    	end();
    }
    
    /**
     * Method binds a joystick axis to escalator motor
     */
    private void joystickEscalatorControl(double pow) {
    	if (Math.abs(pow) > 0.1) {
    		Robot.fourBar.setDartPower(pow);
    	} else {
    		Robot.fourBar.setDartPower(0.0);
    	}
    }
    
    /**
     * Method binds button to intake control
     */
    private void setBinIntakeByButton() {
    	if (OI.fourBarRunBinIntake.get()) {
    		Robot.fourBar.setIntake(1.0);
    	} else {
        	Robot.fourBar.setIntake(0.0);
    	}
    }
    
    /**
     * Method binds button to grabber control
     */
    private void setGrabberByButton() {
    	Robot.fourBar.openGrabber(OI.fourBarOpenGrabber.get());
    }
}
