package org.usfirst.frc.team1218.subsystem.escalator;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Creating an instance of this class will prevent the darts from drifting apart and damaging the 4bar mechanism.
 * @author afiolmahon
 */
public class DartSafety {
	
	private final CANTalon dartL;
	private final CANTalon dartR;
	private final double killDistance;
	private final double realignDistance;
	private final DartKillWatch dartKillWatch;
	private final DartRealignWatch dartRealignWatch;
	/**
	 * @param dartL Left Dart
	 * @param dartR Right Dart
	 * @param Tolerance Allowable Position Difference before safety kicks in
	 */
	public DartSafety(CANTalon dartL, CANTalon dartR, double killDistance, double realignDistance) {
		this.dartL = dartL;
		this.dartR = dartR;
		this.killDistance = killDistance;
		this.realignDistance = realignDistance;
		
		dartKillWatch = new DartKillWatch();
		dartKillWatch.whileActive(new DartKill());
		
		dartRealignWatch = new DartRealignWatch();
		dartRealignWatch.whileActive(new DartRealign());
	}
	
	private class DartRealignWatch extends Trigger {
		@Override
		public boolean get() {
			return Math.abs(dartL.getAnalogInPosition() - dartR.getAnalogInPosition()) > realignDistance;
		}
	}
	
	private class DartKillWatch extends Trigger {
		@Override
		public boolean get() {
			return Math.abs(dartL.getAnalogInPosition() - dartR.getAnalogInPosition()) > killDistance;
		}
		
	}	
	
	private class DartRealign extends Command {

		DartRealign() {
			requires(Robot.escalator);
		}
		
		@Override
		protected void initialize() {
			Robot.escalator.dartManualMode();
		}

		@Override
		protected void execute() {
			if(dartL.getAnalogInPosition() > dartR.getAnalogInPosition()) {
				dartL.set(-Escalator.DART_REALIGN_POWER);
				dartR.set(Escalator.DART_REALIGN_POWER);
			} else {
				dartL.set(Escalator.DART_REALIGN_POWER);
				dartR.set(-Escalator.DART_REALIGN_POWER);
			}
		}

		@Override
		protected boolean isFinished() {
			return Math.abs(dartL.getAnalogInPosition() - dartR.getAnalogInPosition()) < realignDistance;
		}

		@Override
		protected void end() {
			System.out.println("Darts have been realigned to each other");
		}

		@Override
		protected void interrupted() {}
	}
	
	private class DartKill extends Command {
		
		@Override
		protected void initialize() {
			dartL.disableControl();
			dartR.disableControl();
		}

		@Override
		protected void execute() {
			System.out.println("WARNING: Darts have drifted out of specified tolerance range. Disabling...");
			dartL.disableControl();
			dartR.disableControl();
		}

		@Override
		protected boolean isFinished() {
			return true;
		}

		@Override
		protected void end() {
			dartL.disableControl();
			dartR.disableControl();
		}

		@Override
		protected void interrupted() {
			end();
		}
		
	}
}
