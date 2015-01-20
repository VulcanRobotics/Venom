package org.usfirst.frc.team1218.subsystem.escalator;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Escalator extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	Solenoid clampL;
	Solenoid clampR;
	
	CANTalon intakeL;
	CANTalon intakeR;
	
	CANTalon dartL; //2 limit 1 pot
	CANTalon dartR;

    public void initDefaultCommand() {
       setDefaultCommand(new C_EscalatorDefault());
    }
}

