package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.subsystem.swerve.math.Angle;

import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiolmahon
 */
public class C_Index extends Command {

	boolean indexed[] = {false, false, false, false};
	boolean invertTravelDirection[] = {false, false, false, false};
	int indexCount[] = {0, 0, 0, 0};
	
	
    public C_Index() {
        requires(Robot.swerveSystem);
    }

    protected void initialize() {
    	for (int i = 0; i < 4; i++) {
    		SwerveModule module = Robot.swerveSystem.getModuleList().get(i);
    		invertTravelDirection[i] = (Angle.diffBetweenAngles(module.getEncoderAngle(), 0) < 180 ? true : false); //Takes the shortest route to the last known index position
    		indexCount[i] = module.getEncoderIndexCount();
    	}
    }

    protected void execute() {
    	for (int i = 0; i < 4; i++) {
    		SwerveModule module = Robot.swerveSystem.getModuleList().get(i);
    		
    		if(indexCount[i] == module.getEncoderIndexCount()) {
    			module.angleController.changeControlMode(ControlMode.PercentVbus);
        		module.angleController.set((invertTravelDirection[i]) ? SwerveModule.MAX_ANGLE_CONTROLLER_POWER : -SwerveModule.MAX_ANGLE_CONTROLLER_POWER); //Travels shortest distance to last known index position
    		} else {
    			module.angleController.changeControlMode(ControlMode.Position);
    			module.angleController.setPosition(0);
    			indexed[i] = true;
    		}
    	}
    }

    protected boolean isFinished() {
        return indexed[0] && indexed[1] && indexed[2] && indexed[3];
    }

    protected void end() {
    	System.out.println("Swerve Drive Indexed");
    }

    protected void interrupted() {
    	end();
    }
}
