package org.usfirst.frc.team1218.subsystem.elevator;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;

public class ElevatorSaftey {

	public ElevatorSaftey() {
		ElevatorCurrentWatch currentWatch = new ElevatorCurrentWatch();
		currentWatch.whenActive(new C_ElevatorReverse());
	}
	
	public class ElevatorCurrentWatch extends Trigger {
		PowerDistributionPanel pdp = new PowerDistributionPanel();
		double KILL_CURRENT = 20;
		
		public boolean get() {
			return pdp.getCurrent(RobotMap.ELEVATOR_LIFT_MASTER_PDP_SLOT) > KILL_CURRENT;
		}
		
	}
	
	public class C_ElevatorReverse extends Command {

	    public C_ElevatorReverse() {
	    	requires(Robot.elevator);
	    }

	    protected void initialize() {
	    	System.out.println("Elevator reached kill current");
	    	setTimeout(0.5);
	    	Robot.elevator.setPower(-Robot.elevator.getCurrentSpeed());
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
