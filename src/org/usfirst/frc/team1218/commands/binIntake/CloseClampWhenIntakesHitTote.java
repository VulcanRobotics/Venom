package org.usfirst.frc.team1218.commands.binIntake;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.subsystem.binIntake.BinIntake;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CloseClampWhenIntakesHitTote extends Command {

	public final double CUTOFF_CURRENT = 5.0;
	
    public CloseClampWhenIntakesHitTote() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.binIntake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.binIntake.setClamp(BinIntake.OPEN);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.toteIntake.getAverageCurrent() > CUTOFF_CURRENT;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("tote intake amperage above " + CUTOFF_CURRENT + ", closing clamp");
    	Robot.binIntake.setClamp(BinIntake.CLOSED);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
