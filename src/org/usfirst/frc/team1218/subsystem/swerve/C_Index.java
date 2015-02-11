package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.subsystem.swerve.math.Angle;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiolmahon
 */
public class C_Index extends Command {

	private boolean indexed[] = {false, false, false, false};
	private boolean invertTravelDirection[] = {false, false, false, false};
	private int indexCount[] = {0, 0, 0, 0};
	
	
    public C_Index() {
        requires(Robot.swerveSystem);
    }

    protected void initialize() {
    	for (int i = 0; i < 4; i++) {
    		SwerveModule_DIO module = Robot.swerveSystem.getModuleList().get(i);
    		module.anglePIDController.disable();
    		invertTravelDirection[i] = (Angle.diffBetweenAngles(module.getEncoderAngle(), 0) < 180 ? true : false); //Takes the shortest route to the last known index position
    		indexCount[i] = module.getEncoderIndexCount();

    	}
    }

    protected void execute() {
    	for (int i = 0; i < 4; i++) {
    		SwerveModule_DIO module = Robot.swerveSystem.getModuleList().get(i);
    		if(indexCount[i] == module.getEncoderIndexCount()) {
        		module.angleController.set((invertTravelDirection[i]) ? 0.3 : -0.3); //Travels shortest distance to last known index position
    		} else {//TODO ensure it is shortest distance to index
    			module.angleController.set(0.0);
    			module.anglePIDController.enable();
    			indexed[i] = true;
    		}
    	}
    }

    protected boolean isFinished() {
        return indexed[0] && indexed[1] && indexed[2] && indexed[3];
    }

    protected void end() {
    	Robot.swerveSystem.getModuleList().stream().forEach(m -> {
    		m.anglePIDController.enable();
    		m.setRobotAngle(0);
    	});
    	System.out.println("Swerve Drive Indexed");
    }

    protected void interrupted() {
    	end();
    }
}
