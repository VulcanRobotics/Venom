package org.usfirst.frc.team1218.subsystem.swerve;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.usfirst.frc.team1218.commands.swerve.Swerve;
import org.usfirst.frc.team1218.robot.OI;
import org.usfirst.frc.team1218.subsystem.swerve.math.Angle;
import org.usfirst.frc.team1218.subsystem.swerve.math.Vector;

import com.kauailabs.nav6.frc.IMUAdvanced;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Provides Mid/High level module control code.
 * Coordinates the actions of all swerve modules.
 * @author afiolmahon
 */

public class SwerveDrive extends Subsystem implements PIDOutput, PIDSource {
	
	public Vector totalDistanceDriven;
	
	protected static final double DEFAULT_DRIVE_POWER = 0.4;
	protected static final double MAX_DRIVE_POWER = 0.8;
	
    protected List<SwerveModule> module;
    
    private final SerialPort navSerialPort;
    private final IMUAdvanced navModule;
    
    private double fieldCentricHeading = 0;
    
    //Relates to dimensions of drivetrain, used so that swerve understands what wheel orientation faces towards the center of the robot, ex. different for a square vs rectangular drivetrain
	private static final double X_PERPENDICULAR_CONSTANT = 0.546;
	private static final double Y_PERPENDICULAR_CONSTANT = 0.837;
	
	private static final double[] ALPHA_MODULE_ANGLE_OFFSET = {6.0, 161.0, -66.5, 128.0};
	private static final double[] BETA_MODULE_ANGLE_OFFSET = {-4.76, -166.0, -13.0, -160.28 - 12.0};
	
	private final PIDController headingController;
	
	private boolean fieldCentricDriveMode = true;
	
	private boolean headingControllerEnabled;
	private double headingControllerOutput;
	
	private static final double HEADING_CONTROLLER_P = 0.03;
	private static final double HEADING_CONTROLLER_I = 0.0;
	private static final double HEADING_CONTROLLER_D = 0.0;
	
    public SwerveDrive() {
    	totalDistanceDriven = new Vector(0, 0);
    	boolean isBeta = Preferences.getInstance().getBoolean("isBeta", false);
    	module = new ArrayList<SwerveModule>(Arrays.asList(
    				new SwerveModule(0, (isBeta) ? BETA_MODULE_ANGLE_OFFSET[0] : ALPHA_MODULE_ANGLE_OFFSET[0]),
    				new SwerveModule(1, (isBeta) ? BETA_MODULE_ANGLE_OFFSET[1] : ALPHA_MODULE_ANGLE_OFFSET[1]),
    				new SwerveModule(2, (isBeta) ? BETA_MODULE_ANGLE_OFFSET[2] : ALPHA_MODULE_ANGLE_OFFSET[2]),
    				new SwerveModule(3, (isBeta) ? BETA_MODULE_ANGLE_OFFSET[3] : ALPHA_MODULE_ANGLE_OFFSET[3])
    				));	
		navSerialPort = new SerialPort(57600, SerialPort.Port.kMXP);
		navModule = new IMUAdvanced(navSerialPort);
		navModule.zeroYaw();

		headingController = new PIDController(
				HEADING_CONTROLLER_P,
				HEADING_CONTROLLER_I,
				HEADING_CONTROLLER_D,
				this,
				this);
		headingController.setOutputRange(-1.0, 1.0);
		headingController.setInputRange(-180, 180);
		headingController.setContinuous();
		headingController.setAbsoluteTolerance(10);
		
        System.out.println("Swerve System Initialized");
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new Swerve());   
    }
    
    public void syncDashboard() {
    	SmartDashboard.putNumber("SwerveDrive: Robot_Heading", Angle.get360Angle(navModule.getYaw()));
    	SmartDashboard.putNumber("SwerveDrive: Average Distance Driven", getAverageDistanceDriven());
    	module.stream().forEach(m -> m.syncDashboard());
    	SmartDashboard.putBoolean("SwerveDrive: HeadingControllerEnabled", headingControllerEnabled);
    	SmartDashboard.putBoolean("SwerveDrive: FieldCentricDrive", isFieldCentricDriveMode());
    }
    
    /**
     * Start all module angle encoders at an offset for beginning a match with any wheel orientation
     * @param offset the position of the wheels from 0-360
     */
    public void setInitalOffset(double offset){
    	module.forEach(m -> m.setInitialOffset(offset));
    }
    
    /**
     * Set an absolute direction for the modules to face, without spoofing the direction by flipping the wheel direction
     * @param angle
     */
    public void setRawWheelAngle(double angle){
    	module.forEach(m -> m.setRawWheelAngle(angle));
    }
    
    /**
     * @return true if the robot is facing the pidcontrollers target direction
     */
    public boolean isHeadingOnTarget() {
    	return headingController.onTarget();
    }
    
    /**
     * @return true if robot drive is field centric, false if the drive is robot centric
     */
    public boolean isFieldCentricDriveMode() {
    	return fieldCentricDriveMode;
    }
    
    public void setFieldCentricDriveMode(boolean enabled) {
    	fieldCentricDriveMode = enabled;
    }
    
    public void setRawWheelPower(double power) {
    	module.stream().forEach(m -> m.setRawWheelPower(power));
    }
    
    public void addToDistanceDriven(Vector vectorToAdd){
    	totalDistanceDriven.add(vectorToAdd);
    }
    
    /**
     * Creates angle and power for all swerve modules
     * @param translationVector vector with magnitude <= 1
     * @param rotation a value from 1 to -1 representing the amount of rotation to add to the robot angle
     * @param fieldCentricCompensator a gyroscope output that can let the robot drive field-centric, pass 0 if robot centric drive is desired.
     */
    public List<Vector> swerveVectorCalculator(Vector translationVector, double rotation) {
    	double xPerpendicular = X_PERPENDICULAR_CONSTANT;
    	double yPerpendicular = Y_PERPENDICULAR_CONSTANT;
    	if (headingControllerEnabled) {
    		xPerpendicular *= this.headingControllerOutput;
        	yPerpendicular *= this.headingControllerOutput;	
    	} else {
    		xPerpendicular *= rotation;
        	yPerpendicular *= rotation;	
    	}
    	
    	if (isFieldCentricDriveMode()) {
        	translationVector.pushAngle(-getHeading());
    	}    	
    	
    	List<Vector> moduleVector = new ArrayList<Vector>(Arrays.asList(
    			new Vector(translationVector.getX() + xPerpendicular, translationVector.getY() - yPerpendicular),
    			new Vector(translationVector.getX() - xPerpendicular, translationVector.getY() - yPerpendicular),
    			new Vector(translationVector.getX() - xPerpendicular, translationVector.getY() + yPerpendicular),
    			new Vector(translationVector.getX() + xPerpendicular, translationVector.getY() + yPerpendicular)
    			));
    	
    	moduleVector.stream().forEach(v -> v.scaleMagnitude((DEFAULT_DRIVE_POWER + ((MAX_DRIVE_POWER - DEFAULT_DRIVE_POWER) * OI.getTurboPower()))));
    	
    	double maxMagnitude = 0;
    	
    	for (int i = 0; i < 4; i++) maxMagnitude = (moduleVector.get(i).getMagnitude() > maxMagnitude) ? moduleVector.get(i).getMagnitude() : maxMagnitude;
    	
    	double scaleFactor = ((maxMagnitude > 1.0) ? 1.0 / maxMagnitude : 1.0);
    	
    	moduleVector.stream().forEach(v -> {
    		v.scaleMagnitude(scaleFactor);
    		if (v.getMagnitude() > 1.0) v.setMagnitude(1.0);
    	});
    	return moduleVector;
    }
    
    public boolean isAnglePIDOnTarget() {
    	return (module.get(0).isAngleCorrect()
    			&& module.get(1).isAngleCorrect()
    			&& module.get(2).isAngleCorrect()
    			&& module.get(3).isAngleCorrect()
    			);
    }
    
    public void faceForward(){
    	module.stream().forEach(m -> m.faceForward());
    }
    
    public double pidGet(){
    	return getHeading();
    }
    
    public double getHeading() {
    	return (navModule.getYaw() - fieldCentricHeading); 
    }
    
    public void setFieldHeading(double heading) {
    	this.fieldCentricHeading = heading;
    }
    
    public void enableHeadingController(double heading) {
    	headingController.enable();
    	headingController.setSetpoint(heading);
    	headingControllerEnabled = true;
    	System.out.println("[Swerve Drive]: Heading Controller Enabled with setpoint " + heading + " degrees");
    }
    
    public void disableHeadingController() {
    	headingControllerEnabled = false;
    	headingController.disable();
    	headingControllerOutput = 0;
    	System.out.println("[Swerve Drive]: Heading Controller Disabled");
    }
    
    public void resetDistanceDriven() {
    	module.forEach(m -> m.resetDistanceDriven());
    }
    
    //TODO use navx to discount wheels that are off the ground
    public double getAverageDistanceDriven() {
    	double totalDistance = 0;
    	double n = 0;
    	for (int i = 0; i<4; i++) {
    		if (module.get(i).getAbsoluteDistanceDriven() > 0.05) {
    			totalDistance += Math.abs(module.get(i).getAbsoluteDistanceDriven());
    			n++;
    		}
    	}
    	if (n == 0) {
    		//System.out.println("no swerve modules have gone more than .3 feet");
    		n = 1;
    	}
    	return totalDistance / n;
    }
    
    public void powerDrive(Vector translationVector, double rotation) {
    	List<Vector> moduleVectors = swerveVectorCalculator(translationVector, rotation);
    	module.stream().forEach(m -> m.setAngleAndPower(moduleVectors.get(m.moduleNumber)));
    }
    
    /**
     * @deprecated
     * @param translationVector
     * @param rotation
     * @param fieldCentricCompensator
     */
    public void velocityDrive(Vector translationVector, double rotation) {
    	List<Vector> moduleVectors = swerveVectorCalculator(translationVector, rotation);
    	module.stream().forEach(m -> m.setAngleAndVelocity(moduleVectors.get(m.moduleNumber)));
    }
    
    public void zeroHeading(double offsetAngle) {
    	navModule.setYawWithOffset(offsetAngle);
    }
    
    public void enableIndexing(boolean enabled) {
    	module.stream().forEach(m -> m.enableIndexing(enabled));
    }
    
    public void zeroHeading() {
    	navModule.zeroYaw();
    }
    
    
    public boolean hasBeenIndexed(){
    	return module.get(0).hasBeenIndexed();
    }
    
    public void autonZeroHeading() {
    	//TODO should this pass a number from the dashboard instead?
    	String direction = SmartDashboard.getString("Calibration_Orientation", "North");
    	switch (direction) {
    		default: zeroHeading();
    			break;
    		case "North": zeroHeading(0.0);
    			break;
    		case "South": zeroHeading(180.0);
    			break;
    		case "East": zeroHeading(-90.0);
    			break;
    		case "West": zeroHeading(90.0);
    			break;
    		case "-135" : zeroHeading(135.0);
    			break;
    	}
    }
    
    protected void setModuleAngles(double angle0, double angle1, double angle2, double angle3) {
    	module.get(0).setAngle(angle0);
    	module.get(1).setAngle(angle1);
    	module.get(2).setAngle(angle2);
    	module.get(3).setAngle(angle3);
    }
    
    public void setModuleAngles(double angle) {
    	module.stream().forEach(m -> m.setAngle(angle));
    }
    
    public List<SwerveModule> getModuleList() {
    	return module;
    }
    
    /**
     * @param rotationMagnitude rotationPower to write to PIDController
     */
    @Override
	public void pidWrite(double rotationMagnitude) {
		headingControllerOutput = rotationMagnitude;
	}
}