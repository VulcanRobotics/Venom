package org.usfirst.frc.team1218.subsystem.fourBar;

import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class C_GoToPosition extends CommandGroup {
    
    public  C_GoToPosition(double setpoint) {
    	int i = 0;
        while (!Robot.fourBar.dartMasterPositionController.onTarget() && i<6) {
        	i++;
        	System.out.println("about to seek position");
        	addSequential(new C_SeekPosition(setpoint));
        	System.out.println("one position seek completer");
        	Timer.delay(0.25);
        }
    }
}
