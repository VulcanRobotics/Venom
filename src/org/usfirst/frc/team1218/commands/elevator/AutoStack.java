package org.usfirst.frc.team1218.commands.elevator;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.subsystem.elevator.Elevator;

import edu.wpi.first.wpilibj.command.Command;

/**
 * @author afiolmahon
 * @author lcook
 */
public class AutoStack extends Command {//TODO try using autostack during autons to prevent dropping or other things

	private int state = 0;
	private int numberToStack = -1;
	private int stackCounter = 0;
	
	
    public AutoStack() {
        requires(Robot.elevator);
    }
    
    public AutoStack(int numberToStack) {
    	requires(Robot.elevator);
    	this.numberToStack = numberToStack;
    }
    
    protected void initialize() {
    }

    protected void execute() {
    	switch (state) {
    		case 0: 
    			Robot.elevator.setPosition(Elevator.TOP_SOFT_LIMIT);
    			if (Robot.elevator.hasTote()) state = 1;
    			break;
    		case 1:
    			Robot.elevator.setPosition(Elevator.BOTTOM_SOFT_LIMT);
    			if (Robot.elevator.atBottom() && Robot.elevator.hasTote()) state = 2;
    			if (!Robot.elevator.hasTote()) state = 0;
    			break;
    		case 2:
    			
    			Robot.elevator.setPosition(Elevator.TOP_SOFT_LIMIT);
    			if (Robot.elevator.atTop()) {
    				state = 0;
    				stackCounter++;
    			}
    			break;
       	}
    }

    protected boolean isFinished() {
        return (numberToStack != -1) && (stackCounter >= numberToStack);
    }

    protected void end() {
		Robot.elevator.setPosition(Elevator.TOP_SOFT_LIMIT);

    }

    protected void interrupted() {
    	end();
    }
}
