package org.usfirst.frc.team1218.commands.swerve;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiolmahon
 */
public class ToggleFieldCentricDrive extends Command {

    public ToggleFieldCentricDrive() {
        requires(Robot.swerveDrive);
    }

    protected void initialize() {
    	Robot.swerveDrive.setFieldCentricDriveMode(!Robot.swerveDrive.isFieldCentricDriveMode());
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    	System.out.println("Field Centric Drive: " + Robot.swerveDrive.isFieldCentricDriveMode());
    }

    protected void interrupted() {
    	end();
    }
}
