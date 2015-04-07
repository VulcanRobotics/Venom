package org.usfirst.frc.team1218.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Print extends Command {

	String str = "";
	
    public Print(String str) {
    	this.str = str;
    }

    protected void initialize() {
    	System.out.println(str);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
