package org.usfirst.frc.team1218.subsystem.swerve;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;

class O_TurnEncoder implements PIDSource{
    
    Encoder encoder;
    DigitalInput zeroSensor;
  
    double zeroOffset; //how far the groove is away from "true zero" (wheels facing north)
    
    public O_TurnEncoder(int APort, int BPort, int zeroPort, double zeroOffset, boolean reverseEncoder){       
        zeroSensor = new DigitalInput(zeroPort);
        this.zeroOffset = zeroOffset;
        encoder = new Encoder(APort, BPort, reverseEncoder, CounterBase.EncodingType.k4X) {{
            setDistancePerPulse(360.0 / 500.0);
        }};
    }
    
    /**
     * 
     * @return Wheel angle
     */
    public double pidGet() {
        double angle = (getDistance() + zeroOffset) % 360.0;
        angle += (angle < 0.0) ? 360 : 0.0; //Add 360 if less than 0
        return (angle - 180.0);
    }
    
    public void zero() {
        encoder.reset();
    }
    
    public double getDistance() {
        return 360.0 * (encoder.getRaw() / 3500.0);
    }
    
    
}
