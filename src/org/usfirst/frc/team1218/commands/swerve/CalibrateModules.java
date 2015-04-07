package org.usfirst.frc.team1218.commands.swerve;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.subsystem.swerve.SwerveModule;
import org.usfirst.frc.team1218.subsystem.swerve.math.Angle;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiolmahon
 */
public class CalibrateModules extends Command {//TODO try to speed up

	private boolean indexed[] = {false, false, false, false};
	private int[] indexCount = new int[4];
	
    public CalibrateModules() {
        requires(Robot.swerveDrive);
    }

    protected void initialize() {
    	if (!Robot.swerveDrive.hasBeenIndexed()){
    		Robot.swerveDrive.configureForIndexing();
    	}
    	
    	Robot.swerveDrive.getModuleList().stream().forEach(m -> {
    		indexed[m.moduleNumber] = false;
    		indexCount[m.moduleNumber] = m.getEncoderIndexCount();
    		m.enableAnglePID(false);
			boolean invertTravelDirection = (Angle.diffBetweenAngles(m.getEncoderAngle(), -m.getModuleIndexOffset()) < 180 ? true : false);
    		m.setPowerToAngleMotor((invertTravelDirection) ? -0.8 : 0.8);
    	});
    }

    protected void execute() {
    	for (int i = 0; i < 4; i++) {
    		SwerveModule module = Robot.swerveDrive.getModuleList().get(i);
    		if (indexCount[i] != module.getEncoderIndexCount()) {
    		    module.enableAnglePID(true);
    			indexed[i] = true;
    		}
    	}
    }

    protected boolean isFinished() {
        return (indexed[0] && indexed[1] && indexed[2] && indexed[3]);
    }

    protected void end() {
    	System.out.println("swerve drive finished indexing");
    	//Robot.swerveDrive.configureForNoIndex();
    	//System.out.println("swerve drive will no longer index");
    	Robot.swerveDrive.getModuleList().stream().forEach(m -> m.enableAnglePID(true));
    	System.out.println("Swerve Drive Indexed");
    }

    protected void interrupted() {
    	end();
    }
}
