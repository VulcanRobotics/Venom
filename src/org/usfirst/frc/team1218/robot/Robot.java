
package org.usfirst.frc.team1218.robot;

import org.usfirst.frc.team1218.auton.C_JustDrive;
import org.usfirst.frc.team1218.auton.C_OneToteAuton;
import org.usfirst.frc.team1218.auton.C_StepAuton;
import org.usfirst.frc.team1218.auton.C_TwoToteAuton;
import org.usfirst.frc.team1218.auton.C_ZeroAuton;
import org.usfirst.frc.team1218.subsystem.autonHooks.Hooks;
import org.usfirst.frc.team1218.subsystem.binIntake.BinIntake;
import org.usfirst.frc.team1218.subsystem.elevator.Elevator;
import org.usfirst.frc.team1218.subsystem.fourBar.FourBar;
import org.usfirst.frc.team1218.subsystem.swerve.SwerveDrive;
import org.usfirst.frc.team1218.subsystem.toteIntake.ToteIntake;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 * @author afiol-mahon
 */
public class Robot extends IterativeRobot {
	
	public static SwerveDrive swerveDrive;
	public static FourBar fourBar;
	public static Elevator elevator;
	public static Hooks hooks;
	public static ToteIntake toteIntake;
	public static BinIntake binIntake;
	public static OI oi;
	private static String autonName;

	
	public Command robotAuton;
	
    Command autonomousCommand;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	swerveDrive = new SwerveDrive();
    	fourBar = new FourBar();
    	elevator = new Elevator();
    	hooks = new Hooks();
    	toteIntake = new ToteIntake();
    	binIntake = new BinIntake();
		oi = new OI();		
        System.out.println("Robot Initialized");
    }
	
	public void disabledPeriodic() {
		autonName = SmartDashboard.getString("Auton_Select", "Not Set");
		SmartDashboard.putString("Current_Auton_Selector", autonName);
		Scheduler.getInstance().run();
		syncDashboard();
	}
	
    public void autonomousInit() {
    	autonName = SmartDashboard.getString("Auton_Select", "Not Set");
		SmartDashboard.putString("Current_Auton_Selector", autonName);
    	System.out.println("Auton " + autonName + " selected.");
    	switch (autonName) {
    		default:
    			autonomousCommand = new C_ZeroAuton();
    		case "No Auton":
    			autonomousCommand = new C_ZeroAuton();
    			break;
    		case "TwoToteAuton":
    			autonomousCommand = new C_TwoToteAuton();
    			break;
    		case "OneToteAuton":
    			autonomousCommand = new C_OneToteAuton();
    			break;
    		case "StepAuton":
    			autonomousCommand = new C_StepAuton();
    			break;
    		case "JustDrive":
    			autonomousCommand = new C_JustDrive();
    			break;
    	}
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
    	syncDashboard();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
    public void syncDashboard() {
    	Robot.swerveDrive.syncDashboard();
    	Robot.elevator.syncDashboard();
    	Robot.fourBar.syncDashboard();
    	Robot.hooks.syncDashboard();
    	Robot.toteIntake.syncDashboard();
    	Robot.binIntake.syncDashboard();
    	SmartDashboard.putBoolean("isBeta", Preferences.getInstance().getBoolean("isBeta", false));
    }
}
