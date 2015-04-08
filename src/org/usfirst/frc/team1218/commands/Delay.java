package org.usfirst.frc.team1218.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Delay extends Command {

	double time;
	
    public Delay(double time) {
    	this.time = time;
    }

    protected void initialize() {
    	setTimeout(time);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
