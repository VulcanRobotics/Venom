package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_ToggleFieldCentricDrive extends Command {

    public C_ToggleFieldCentricDrive() {
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
