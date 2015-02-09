
package org.usfirst.frc.team1218.robot;

import org.usfirst.frc.team1218.subsystem.elevator.Elevator;
import org.usfirst.frc.team1218.subsystem.escalator.Escalator;
import org.usfirst.frc.team1218.subsystem.hooks.Hooks;
import org.usfirst.frc.team1218.subsystem.swerve.SwerveDrive;
import org.usfirst.frc.team1218.subsystem.toteIntake.ToteIntake;
import org.usfirst.frc.team1218.subsystem.binIntake.BinIntake;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 * @author afiol-mahon
 */
public class Robot extends IterativeRobot {
	
	public static SwerveDrive swerveSystem;
	public static Escalator escalator;
	public static Elevator elevator;
	public static Hooks hooks;
	public static ToteIntake toteIntake;
	public static BinIntake binIntake;
	public static OI oi;
	
    Command autonomousCommand;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	swerveSystem = new SwerveDrive();
    	escalator = new Escalator();
    	elevator = new Elevator();
    	hooks = new Hooks();
    	toteIntake = new ToteIntake();
    	binIntake = new BinIntake();
		oi = new OI();
		
        //instantiate the command used for the autonomous period
        autonomousCommand = new C_AutonCommand();
        System.out.println("Robot Initialized");
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        if (autonomousCommand != null) autonomousCommand.cancel();
        System.out.println("Teleop Initialized");
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	Scheduler.getInstance().run();
    	Robot.swerveSystem.syncDashboard();
    	Robot.elevator.syncDashboard();
    	Robot.escalator.syncDashboard();
    	Robot.hooks.syncDashboard();
    	Robot.toteIntake.syncDashboard();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
