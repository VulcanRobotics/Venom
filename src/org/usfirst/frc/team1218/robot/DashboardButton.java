package org.usfirst.frc.team1218.robot;

import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DashboardButton extends Trigger {

	private String dashboardKey;
	private boolean defaultState;
	
	public DashboardButton(String dashboardKey, boolean defaultState) {
		this.dashboardKey = dashboardKey;
		this.defaultState = defaultState;
	}
	
	@Override
	public boolean get() {
		return SmartDashboard.getBoolean(dashboardKey, defaultState);
	}
}
