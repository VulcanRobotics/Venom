package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.subsystem.escalator.Escalator;
import org.usfirst.frc.team1218.subsystem.swerve.math.*;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 *creating an instance of this class will try to stop the robot from tipping over using the nav 6 
 */
public class TipSaftey {

	double MAX_ROLL = 5;
	
	double P = 0.01;
	
	double MAX_RESPONSE_SPEED = 0.2;
	
	TipWatch tipWatch;
	
    public TipSaftey() {
        tipWatch = new TipWatch();
        tipWatch.whileActive(new TipResponder());
    }
    
    private class TipWatch extends Trigger {
    	@Override
    	public boolean get() {
    		// is tripped
    		return Math.abs(Robot.swerveSystem.navModule.getRoll()) > MAX_ROLL;
    	}
    }
    
    private class TipResponder extends Command {
    	TipResponder() {
			requires(Robot.swerveSystem);
		}
		
		@Override
		protected void initialize() {
			System.out.println("responding to robot tip");
		}

		@Override
		protected void execute() {
			//TODO: MAKE SURE ROBOT RESPONDS IN CORRECT DIRECTION
			float roll = Robot.swerveSystem.navModule.getRoll();
			double motorPower = P * roll; 
			if (motorPower > MAX_RESPONSE_SPEED) {motorPower = MAX_RESPONSE_SPEED;}
			if (motorPower < -MAX_RESPONSE_SPEED) {motorPower = -MAX_RESPONSE_SPEED;}
			double y = motorPower;
			Robot.swerveSystem.module.forEach(m -> m.setVector(new Vector(0.0, y)));
		}

		@Override
		protected boolean isFinished() {
			//handled  by trigger
			return false;
		}

		@Override
		protected void end() {
			System.out.println("Recovered from tip");
		}

		@Override
		protected void interrupted() {}
    }
   
}

