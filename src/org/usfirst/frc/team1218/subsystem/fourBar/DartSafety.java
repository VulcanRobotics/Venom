package org.usfirst.frc.team1218.subsystem.fourBar;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Creating an instance of this class will prevent the darts from drifting apart and damaging the 4bar mechanism.
 * @author afiolmahon
 */
public class DartSafety {
	private final DartKillWatch dartKillWatch;
	private final DartRealignWatch dartRealignWatch;
	
	private boolean dartKillFlag = false;
	
	
	
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
	
	public boolean dartKilled() {
		boolean killed = dartKillFlag | dartKillWatch.get();
		if (killed) System.out.println("DARTS KILLED... RESTART ROBOT CODE AND CHECK FOR HARDWARE/SOFTWARE FAULT...");
		return killed;
	}
	
	private class DartKillWatch extends Trigger {
		@Override
		public boolean get() {
			return Robot.fourBar.getDartPositionDifference() > FourBar.DART_FAILSAFE_DISTANCE;
		}
		
	}
	
	private class DartRealignWatch extends Trigger {
		@Override
		public boolean get() {
			return (Robot.fourBar.getDartPositionDifference() > FourBar.DART_REALIGN_DISTANCE) && (!dartKilled());
		}
	}
	
	private class DartRealign extends Command {

		public DartRealign() {
			requires(Robot.fourBar);
		}
		
		@Override
		protected void initialize() {
			Robot.fourBar.dartEnablePID(false);
		}
		 
		@Override
		protected void execute() {
			System.out.println("DART SAFETY: REALIGNING");
			if(Robot.fourBar.dartL.getAnalogInPosition() > Robot.fourBar.dartR.getAnalogInPosition()) {
				Robot.fourBar.dartL.set(-FourBar.DART_REALIGN_POWER);
				Robot.fourBar.dartR.set(FourBar.DART_REALIGN_POWER);
			} else {
				Robot.fourBar.dartL.set(FourBar.DART_REALIGN_POWER);
				Robot.fourBar.dartR.set(-FourBar.DART_REALIGN_POWER);
			}
		}

		@Override
		protected boolean isFinished() {
			return (Robot.fourBar.getDartPositionDifference() < FourBar.DART_REALIGN_DISTANCE) | dartKilled();
		}

		@Override
		protected void end() {
			System.out.println("Darts have been realigned to each other");
			Robot.fourBar.dartL.set(0.0);
			Robot.fourBar.dartR.set(0.0);
		}

		@Override
		protected void interrupted() {}
	}
	
	private class DartKill extends Command {
		
		public DartKill() {
			requires(Robot.fourBar);
		}
		
		@Override
		protected void initialize() {
			Robot.fourBar.disableDarts();
			dartKillFlag = true;
		}
		
		@Override
		protected void execute() {
			System.out.println("WARNING: Darts have drifted out of specified tolerance range. Disabling...");
			Robot.fourBar.disableDarts();
		}
		
		@Override
		protected boolean isFinished() {
			return false;
		}
		
		@Override
		protected void end() {
			Robot.fourBar.disableDarts();
		}
		
		@Override
		protected void interrupted() {
			end();
		}
		
	}
}
