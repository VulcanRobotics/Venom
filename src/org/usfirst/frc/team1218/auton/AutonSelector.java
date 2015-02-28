package org.usfirst.frc.team1218.auton;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonSelector {
	
	static private final int ZERO_AUTON = 0;
	static private final int JUST_DRIVE = 5;
	static private final int ONE_TOTE = 1;
	static private final int TWO_TOTE = 2;

	
	public static CommandGroup getAuton() {
		final int auton = (int) SmartDashboard.getNumber("Auton_Select", 0);
		CommandGroup autonCommand;

		switch (auton) {
			case ZERO_AUTON:
				autonCommand = new C_ZeroAuton();
				break;
			case JUST_DRIVE:
				autonCommand = new C_JustDrive();
				break;
			case ONE_TOTE:
				autonCommand = new C_OneToteAuton();
				break;
			case TWO_TOTE:
				autonCommand = new C_TwoToteAuton();
				break;
			default:
				autonCommand = new C_ZeroAuton();
				System.out.println("error: no auton selected");

		}
		
		return autonCommand;
	}
}
