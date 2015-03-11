package org.usfirst.frc.team1218.commands.swerve;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.subsystem.swerve.SwerveModule;
import org.usfirst.frc.team1218.subsystem.swerve.math.Angle;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiolmahon
 *
 *indexes, then checks if index sucessful
 *
 */
public class CalibrateModules extends Command {
	
	
    public CalibrateModules() {
        requires(Robot.swerveDrive);
    }

    protected void initialize() {
    	Robot.swerveDrive.getModuleList().stream().forEach(m -> m.beginIndex());
    }

    protected void execute() {
    }

    protected boolean isFinished() {
    	
        return (Robot.swerveDrive.getModuleList().get(0).index() &&
        		Robot.swerveDrive.getModuleList().get(1).index() &&
        		Robot.swerveDrive.getModuleList().get(2).index() &&
        		Robot.swerveDrive.getModuleList().get(3).index());
    }

    protected void end() {
    	Robot.swerveDrive.getModuleList().stream().forEach(m -> m.endIndex());
    	System.out.println("Swerve Drive Indexed");
    }

    protected void interrupted() {
    	end();
    }
    
    protected void moduleEnablePID() {
    	
    }
}
