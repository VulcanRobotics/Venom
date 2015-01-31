package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class C_ToggleAutoBreaking extends Command {

    public C_ToggleAutoBreaking() {
        requires(Robot.swerveSystem);
    }

    protected void initialize() {
    	//Gets the Current SwerveSystem break mode by referencing the mode of 1 so they always switch together
    	boolean breakMode = Robot.swerveSystem.getModuleList().get(0).getDriveWheelController().getBrakeEnableDuringNeutral();
        Robot.swerveSystem.getModuleList().stream().forEach(m ->
        	m.getDriveWheelController().enableBrakeMode(!breakMode));
        SmartDashboard.putBoolean("SM_Break_Mode", !breakMode);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
