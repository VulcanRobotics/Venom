package org.usfirst.frc.team1218.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    //SM0
    	public static int SM0_CIM = 0;
        public static int SM0_banebot = 1;
        
        public static int SM0_EncoderA = 0;
        public static int SM0_EncoderB = 1;
        public static int SM0_Zero = 8;
    //SM1
        public static int SM1_CIM = 2;
        public static int SM1_banebot = 3;
        
        public static int SM1_EncoderA = 2;
        public static int SM1_EncoderB = 3;
        public static int SM1_Zero = 9;
    //SM2
        public static int SM2_CIM = 4;
        public static int SM2_banebot = 5;
        
        public static int SM2_EncoderA = 4;
        public static int SM2_EncoderB = 5;
        public static int SM2_Zero = 10;
    //SM3
        public static int SM3_CIM = 6;
        public static int SM3_banebot = 7;
        
        public static int SM3_EncoderA = 6;
        public static int SM3_EncoderB = 7;
        public static int SM3_Zero = 11;

    //Joysticks
        public static int J1 = 0;
    //Drivetrain senors
        public static int Gyro = 1; //Analog input
}
