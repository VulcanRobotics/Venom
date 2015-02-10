package org.usfirst.frc.team1218.robot;

import edu.wpi.first.wpilibj.Joystick;

import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team1218.robot.OI.Axis;
import org.usfirst.frc.team1218.subsystem.swerve.math.Vector;

import edu.wpi.first.wpilibj.Timer;
/**
 *
 */
//TODO: rewrite to remove duplicate code for left and right joysticks
public class LazyStick extends Joystick {
	//smoothes joystick output
	//change in joystick output per second
		
	HashMap<Integer, Double> maxAcceleration = new HashMap<Integer, Double>();
	HashMap<Integer, Double> lastRecorededPower = new HashMap<Integer, Double>();
	HashMap<Integer, Double> acceleration = new HashMap<Integer, Double>();
	HashMap<Integer, Double> deltaPower = new HashMap<Integer, Double>();
	HashMap<Integer, Double> power = new HashMap<Integer, Double>();

	double lastRecordedTime = 0;
	
	public LazyStick(int port) {
		super(port);
		maxAcceleration.put(Axis.LEFT_X, 5.0);
		maxAcceleration.put(Axis.LEFT_Y, 1.0);
		maxAcceleration.put(Axis.RIGHT_X, 5.0);
		maxAcceleration.put(Axis.RIGHT_Y, 1.0);
	}
	
	@Override
	public double getRawAxis(int axisID) {
		//TODO: check math
		//get joystick powers
		power.put(Axis.LEFT_X, super.getRawAxis(Axis.LEFT_X));
		power.put(Axis.RIGHT_X, super.getRawAxis(Axis.RIGHT_X));
		power.put(Axis.LEFT_Y, -super.getRawAxis(Axis.LEFT_Y));
		power.put(Axis.RIGHT_Y, -super.getRawAxis(Axis.RIGHT_Y));
		
		//calculate accleration on specified axis
		double deltaT = Timer.getFPGATimestamp() - lastRecordedTime;
		deltaPower.put(axisID, power.get(axisID) - lastRecorededPower.get(axisID));
		acceleration.put(axisID, deltaPower.get(axisID) / deltaT);
		
		//check if axis has too much acceleration
		if (acceleration.get(axisID) > maxAcceleration.get(axisID)) {
			//if acceleration is too great, use vf = vi + a*t
			power.put(axisID, power.get(axisID) + deltaT * maxAcceleration.get(axisID));
		}
		else {
			//leave as is, do not make any adjustments to joystick power
		}
		
		lastRecordedTime = Timer.getFPGATimestamp();
		return power.get(axisID);
	}
}
