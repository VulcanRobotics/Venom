package org.usfirst.frc.team1218.auton;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.subsystem.swerve.C_SetRobotHeading;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class C_Wobble extends CommandGroup {
    
    public  C_Wobble() {
    	addSequential(new C_SetRobotHeading(-45));
      	Timer.delay(0.4);
      	addSequential(new C_SetRobotHeading(45));
      	Timer.delay(0.4);
      	addSequential(new C_SetRobotHeading(-45));
      	Timer.delay(0.4);
      	addSequential(new C_SetRobotHeading(45));
      	Timer.delay(0.4);
      	addSequential(new C_SetRobotHeading(-45));
      	Timer.delay(0.4);
      	addSequential(new C_SetRobotHeading(45));
      	Timer.delay(0.4);
      	addSequential(new C_SetRobotHeading(-45));
      	Timer.delay(0.4);
      	addSequential(new C_SetRobotHeading(45));
      	Timer.delay(0.4);

    }
}
