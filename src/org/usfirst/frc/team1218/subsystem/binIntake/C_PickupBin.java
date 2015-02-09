package org.usfirst.frc.team1218.subsystem.binIntake;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1218.robot.Robot;

/**
 *
 */
public class C_PickupBin extends Command {
	
    public C_PickupBin() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.binIntake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.binIntake.setClamp(false);
    	Robot.binIntake.setSpeed(Robot.binIntake.INTAKE_SPEED);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.binIntake.syncDashboard();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//will either be ended by OI or by detecting bin
        return Robot.binIntake.getHasBin(); // only returns true if bin is detected to be in claws
    }

    // Called once after isFinished returns true
    protected void end() {
    	//when button released, close grabbers to capture bin and stop wheels
    	Robot.binIntake.setClamp(true);
    	Robot.binIntake.setSpeed(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	
    	end();
    }
    
    
    
}
