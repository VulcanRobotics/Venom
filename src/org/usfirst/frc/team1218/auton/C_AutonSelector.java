package org.usfirst.frc.team1218.auton;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *@author afiol-mahon
 */
public class C_AutonSelector extends Command {
	
	private int auton;
	
    protected void initialize() {
    	auton = (int) SmartDashboard.getNumber("Auton_Select", 0);
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
}
