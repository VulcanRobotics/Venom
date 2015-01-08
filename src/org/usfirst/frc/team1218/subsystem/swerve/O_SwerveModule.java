package org.usfirst.frc.team1218.subsystem.swerve;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.usfirst.frc.team1218.math.O_Point;
import org.usfirst.frc.team1218.robot.RobotMap;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 *
 * @author 1218
 */
public class O_SwerveModule {
    Talon cim;
    Talon turnMotor;
    O_TurnEncoder turnEncoder;
    PIDController turn;
    O_Point location;
    double speed;
    double angle;
    final double[] zeroingSpeed = {0.2, 0.24, 0.4, 0.15};
    double zeroSpeedOutput = 0.20;
    double desiredZeroSpeed = 40.0;
    
    boolean isZeroing = false;
    
    double shouldReverse = 1.0;
        
    final int maxTurnDegrees  = 150;
    
    public O_SwerveModule(O_Point center, int CimPort, int turnPort, int turnEncoderA, int turnEncoderB, int zeroPort, double zeroOffset, boolean reverseEncoder){
        location = center;
        cim = new Talon(CimPort);
        cim.setExpiration(0.5);
        turnMotor = new Talon(turnPort);
        turnMotor.setExpiration(0.5);
        turnEncoder = new O_TurnEncoder(turnEncoderA, turnEncoderB, zeroPort, zeroOffset, reverseEncoder);
        turn = new PIDController(1.0, 0.1, 0.1, turnEncoder, turnMotor, 0.0010) {{
            setInputRange(-180.0, 180.0);
            setOutputRange(-0.85, 0.85);
            setContinuous();
            enable();
        }};
    }
    
    void update(double angle, double speed) {
    	this.angle = angle;
    	this.speed = speed;
        setAngle(angle);
        setPower(speed);
        turn.setPID(SmartDashboard.getNumber("TurningP", 0.01),
                    SmartDashboard.getNumber("TurningI", 0.0),
                    SmartDashboard.getNumber("TurningD", 0.0));
        if (isZeroing) zero();
    }
    
    void zero() {   
        if(turnEncoder.zeroSensor.get()) {
            //zero mark reached
            turnMotor.set(0);
            turnEncoder.zero();
            turn.enable();
            isZeroing = false;
        } else { 
            //zero mark not reached
            turn.disable();
            zeroSpeedOutput = zeroSpeedOutput + 0.00 * (desiredZeroSpeed - turnEncoder.encoder.getRate()) * (turnEncoder.encoder.getDirection() ? 1.0 : -1.0);
            if(zeroSpeedOutput > 1.0) {
                zeroSpeedOutput = 1.0;
            } else if(zeroSpeedOutput < 0.0) {
                zeroSpeedOutput = 0.0;
            }
            turnMotor.set(zeroSpeedOutput);
            isZeroing = true;
        }
    }
    
    public void setAngle(double angle) {
        if(turnMotor.getChannel() == RobotMap.SM0_banebot | turnMotor.getChannel() == RobotMap.SM1_banebot ) angle = -angle;
        if (speed > 0.1) {
            int requiredTravel = (int)(angle - turnEncoder.pidGet());
            if (requiredTravel > 180) requiredTravel = requiredTravel - 360;
            if (requiredTravel < -180) requiredTravel = requiredTravel + 360;
            if (Math.abs(requiredTravel) > maxTurnDegrees) {
                //Reverses motor and changes angle
                if (angle > 0) angle = angle - 180;
                if (angle < 0) angle = angle + 180;
                shouldReverse = -1.0;
            } else {
                shouldReverse = 1.0;
            }
            turn.setSetpoint(angle);
        }
    }
    
    public void setPower(double power) {
        cim.set(power * 0.5 * shouldReverse);
    }
}

