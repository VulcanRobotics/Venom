package org.usfirst.frc.team1218.subsystem.escalator;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Creating an instance of this class will prevent the darts from drifting apart and damaging the 4bar mechanism.
 * @author afiolmahon
 */
public class DartSafety {
	private final DartKillWatch dartKillWatch;
	private final DartRealignWatch dartRealignWatch;
	
	/**
	 * @param dartL Left Dart
	 * @param dartR Right Dart
	 * @param Tolerance Allowable Position Difference before safety kicks in
	 */
	public DartSafety() {
		dartKillWatch = new DartKillWatch();
		dartKillWatch.whileActive(new DartKill());
		
		dartRealignWatch = new DartRealignWatch();
		dartRealignWatch.whileActive(new DartRealign());
		System.out.println("Dart Safety Initialized");
	}
	
	private class DartRealignWatch extends Trigger {
		@Override
		public boolean get() {
			return Robot.escalator.getDartPositionDifference() > Escalator.DART_REALIGN_DISTANCE;
		}
	}
	
	private class DartKillWatch extends Trigger {
		@Override
		public boolean get() {
			return Robot.escalator.getDartPositionDifference() > Escalator.DART_FAILSAFE_DISTANCE;
		}
		
	}	
	
	private class DartRealign extends Command {

		DartRealign() {
			requires(Robot.escalator);
		}
		
		@Override
		protected void initialize() {
			Robot.escalator.dartL.changeControlMode(ControlMode.PercentVbus);
			Robot.escalator.dartR.changeControlMode(ControlMode.PercentVbus);
		}
		 
		@Override
		protected void execute() {
			if(Robot.escalator.dartL.getAnalogInPosition() > Robot.escalator.dartR.getAnalogInPosition()) {
				Robot.escalator.dartL.set(-Escalator.DART_REALIGN_POWER);
				Robot.escalator.dartR.set(Escalator.DART_REALIGN_POWER);
			} else {
				Robot.escalator.dartL.set(Escalator.DART_REALIGN_POWER);
				Robot.escalator.dartR.set(-Escalator.DART_REALIGN_POWER);
			}
		}

		@Override
		protected boolean isFinished() {
			return Robot.escalator.getDartPositionDifference() < Escalator.DART_REALIGN_DISTANCE;
		}

		@Override
		protected void end() {
			System.out.println("Darts have been realigned to each other");
			Robot.escalator.dartL.set(0.0);
			Robot.escalator.dartR.set(0.0);
		}

		@Override
		protected void interrupted() {}
	}
	
	private class DartKill extends Command {
		
		@Override
		protected void initialize() {
			Robot.escalator.disableDarts();
		}
		
		@Override
		protected void execute() {
			System.out.println("WARNING: Darts have drifted out of specified tolerance range. Disabling...");
			Robot.escalator.disableDarts();
		}
		
		@Override
		protected boolean isFinished() {
			return false;
		}
		
		@Override
		protected void end() {
			Robot.escalator.disableDarts();
		}
		
		@Override
		protected void interrupted() {
			end();
		}
		
	}
}
