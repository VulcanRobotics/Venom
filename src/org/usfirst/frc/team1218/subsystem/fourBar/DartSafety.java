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
		boolean killed = dartKillFlag || dartKillWatch.get();
		if (killed) Robot.fourBar.disableDarts();
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
			return false;
		}
	}
	
	private class DartRealign extends Command {

		public DartRealign() {
			requires(Robot.fourBar);
		}
		
		@Override
		protected void initialize() {
			System.out.println("DART SAFETY: REALIGNING");
			Robot.fourBar.dartEnablePID(false);
			Robot.fourBar.dartSlavePositionController.disable();
		}
		 
		@Override
		protected void execute() {
			if(Robot.fourBar.dartLeft.getPosition() > Robot.fourBar.dartRight.getPosition()) {
				Robot.fourBar.dartLeft.set(-FourBar.DART_REALIGN_POWER);
				Robot.fourBar.dartRight.set(FourBar.DART_REALIGN_POWER);
			} else {
				Robot.fourBar.dartLeft.set(FourBar.DART_REALIGN_POWER);
				Robot.fourBar.dartRight.set(-FourBar.DART_REALIGN_POWER);
			}
		}

		@Override
		protected boolean isFinished() {
			return (Robot.fourBar.getDartPositionDifference() < FourBar.DART_REALIGN_DISTANCE) || dartKilled();
		}

		@Override
		protected void end() {
			System.out.println("Darts have been realigned to each other");
			Robot.fourBar.dartLeft.set(0.0);
			Robot.fourBar.dartRight.set(0.0);
			Robot.fourBar.dartSlavePositionController.enable();
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
			System.out.println("WARNING: Darts have drifted out of specified tolerance range. Disabling...");
			dartKillFlag = true;
		}
		
		@Override
		protected void execute() {
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
