package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;

public class ElevatorSafety {

	final double COOLDOWN_TIME = 1.25;
	
	public ElevatorSafety() {
		ElevatorCurrentWatch currentWatch = new ElevatorCurrentWatch();
		currentWatch.whenActive(new C_ElevatorReverse());
		
	}
	
	public class ElevatorCurrentWatch extends Trigger {
		
		double KILL_CURRENT = 20;
		
		public boolean get() {
			return Robot.elevator.getCurrent() > KILL_CURRENT;
		}
		
	}
	
	public class C_ElevatorReverse extends Command {

	    public C_ElevatorReverse() {
	    	requires(Robot.elevator);
	    }

	    protected void initialize() {
	    	System.out.println("Elevator reached kill current");
	    	setTimeout(COOLDOWN_TIME);
	    	Robot.elevator.setPower(0);
	    }

	    protected void execute() {
	    }

	    protected boolean isFinished() {
	        return isTimedOut();
	    }

	    protected void end() {
	    	System.out.println("Eleavtor done reversing");
	    	Robot.elevator.setPower(0);
	    }

	    protected void interrupted() {
	    	end();
	    }
	}

	
}