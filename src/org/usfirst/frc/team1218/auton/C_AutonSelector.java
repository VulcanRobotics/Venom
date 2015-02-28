package org.usfirst.frc.team1218.auton;

import java.util.HashMap;

import org.usfirst.frc.team1218.subsystem.swerve.C_Index;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *@author afiol-mahon
 */
public class C_AutonSelector extends Command {
	
	private String auton;
	private Command command;
	
    protected void initialize() {
    	auton = SmartDashboard.getString("","No Auton");
    	command = COMMANDS.getOrDefault(auton, new C_Index());
    	command.start();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    	System.out.println("Auton " + auton + " selected.");
    }

    protected void interrupted() {
    }
    
    protected static HashMap<String, Command> COMMANDS = new HashMap<String, Command>();
    static {
    	COMMANDS.put("No Auton", new C_Index());
    	COMMANDS.put("TwoToteAuton", new C_TwoToteAuton());
    }
}
