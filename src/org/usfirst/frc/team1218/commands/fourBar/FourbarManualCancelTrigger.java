package org.usfirst.frc.team1218.commands.fourBar;

import org.usfirst.frc.team1218.robot.OI;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class FourbarManualCancelTrigger extends Trigger {

	public FourbarManualCancelTrigger(){
		super();
	}
	
	public boolean get(){
		return Math.abs(OI.getFourBarControlAxis()) > 0.5; 
	}

}
