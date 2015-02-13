package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.subsystem.swerve.math.Angle;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiolmahon
 */
public class C_Index extends Command {

	private boolean[] indexed = new boolean[4];
	private boolean[] invertTravelDirection = new boolean[4];
	private int[] indexCount = new int[4];
	
	
    public C_Index() {
        requires(Robot.swerveDrive);
    }

    protected void initialize() {
    	for (int i = 0; i < 4; i++) {
    		SwerveModule module = Robot.swerveDrive.getModuleList().get(i);
    		module.anglePIDController.disable();
    		invertTravelDirection[i] = (Angle.diffBetweenAngles(module.getEncoderAngle(), -module.moduleAngleOffset[module.moduleNumber])) < 180 ? true : false ; //Takes the shortest route to the last known index position
    		indexCount[i] = module.getEncoderIndexCount();
    		System.out.println("SM_" + module.moduleNumber + ": Distance to Index: " + Angle.diffBetweenAngles(module.getEncoderAngle(), -module.moduleAngleOffset[module.moduleNumber]));
    		module.driveWheelController.set(0.0);
    	}
    }

    protected void execute() {
    	for (int i = 0; i < 4; i++) {
    		SwerveModule module = Robot.swerveDrive.getModuleList().get(i);
    		if (indexCount[i] != module.getEncoderIndexCount()) {
    			module.anglePIDController.setSetpoint(0.0);
    			module.anglePIDController.enable();
    			indexed[i] = true;
    		} else {
        		module.angleController.set((invertTravelDirection[i]) ? 0.3 : -0.3); //Travels shortest distance to last known index position
    		}
    	}
    }

    protected boolean isFinished() {
        return indexed[0] && indexed[1] && indexed[2] && indexed[3];
    }

    protected void end() {
    	Robot.swerveDrive.getModuleList().stream().forEach(m -> {
    		m.anglePIDController.enable();
    		m.setRobotAngle(0);
    	});
    	System.out.println("Indexing ended...");
    }

    protected void interrupted() {
    	end();
    }
}
