package org.usfirst.frc.team1218.subsystem.fourBar;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Creating an instance of this class will prevent the darts from drifting apart and damaging the 4bar mechanism.
 * @author afiolmahon
 */
public class DartSafety {
	protected final DartKillWatch dartKillWatch;
	private final DartLimitWatch dartLimitWatch;
	private final DartRealignWatch dartRealignWatch;
	
	public boolean dartKilled = false;
	
	/**
	 * @param dartL Left Dart
	 * @param dartR Right Dart
	 * @param Tolerance Allowable Position Difference before safety kicks in
	 */
	public DartSafety() {
		dartKillWatch = new DartKillWatch();
		dartKillWatch.whileActive(new DartKill());
		
		dartLimitWatch = new DartLimitWatch();
		dartLimitWatch.whileActive(new DartLimit());
		
		dartRealignWatch = new DartRealignWatch();
		dartRealignWatch.whileActive(new DartRealign());
		System.out.println("Dart Safety Initialized");
	}
	
	public class DartKillWatch extends Trigger {
		@Override
		public boolean get() {
			return Robot.fourBar.getDartPositionDifference() > FourBar.DART_FAILSAFE_DISTANCE;
		}
		
	}
	
	private class DartLimitWatch extends Trigger {
		
		@Override
		public boolean get() {
			return Robot.fourBar.dartL.isFwdLimitSwitchClosed() 
					| Robot.fourBar.dartR.isFwdLimitSwitchClosed() 
					| Robot.fourBar.dartL.isRevLimitSwitchClosed()
					| Robot.fourBar.dartR.isRevLimitSwitchClosed();
		}
	}
	
	private class DartRealignWatch extends Trigger {
		@Override
		public boolean get() {
			return (Robot.fourBar.getDartPositionDifference() > FourBar.DART_REALIGN_DISTANCE) && (!dartKilled);
		}
	}
	
	private class DartRealign extends Command {

		DartRealign() {
			requires(Robot.fourBar);
		}
		
		@Override
		protected void initialize() {
			Robot.fourBar.dartL.changeControlMode(ControlMode.PercentVbus);
			Robot.fourBar.dartR.changeControlMode(ControlMode.PercentVbus);
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
			return (Robot.fourBar.getDartPositionDifference() < FourBar.DART_REALIGN_DISTANCE) | dartKilled;
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
		
		@Override
		protected void initialize() {
			Robot.fourBar.disableDarts();
			dartKilled = true;
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
	
	private class DartLimit extends Command {
		
		@Override
		protected void initialize() {
			
		}
		
		@Override
		protected void execute() {
			
		}
		
		@Override
		protected boolean isFinished() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		protected void end() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		protected void interrupted() {
			end();
		}
		
	}
}
