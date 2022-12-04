package frc.io.hdw_io;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.io.hdw_io.util.*;


public class IO {
    //NavX
    public static NavX navX = new NavX(SPI.Port.kMXP);
    //Drive
    public static WPI_TalonSRX drvTSRX_L = new WPI_TalonSRX(56); // Cmds left wheels. Includes encoders
    public static WPI_TalonSRX drvTSRX_R = new WPI_TalonSRX(57); // Cmds right wheels. Includes encoders
    public static DifferentialDrive diffDrv_M = new DifferentialDrive(IO.drvTSRX_L, IO.drvTSRX_R);

    // DigitalInputs
    public static InvertibleDigitalInput diBtnRed = new InvertibleDigitalInput(4, false);
    public static InvertibleDigitalInput diBtnYel = new InvertibleDigitalInput(5, false);
    public static InvertibleDigitalInput diBtnGrn = new InvertibleDigitalInput(6, false);

    // DigitalOutputs
    public static DigitalOutput doLedRed = new DigitalOutput(0);
    public static DigitalOutput doLedYel = new DigitalOutput(1);
    public static DigitalOutput doLedGrn = new DigitalOutput(2);

    // Solendoid Values

    /**
     * Initialize any hardware
     */
    public static void init() {
        sdbInit();
    }

    /**
     * Update hardware.  Usually called from Robot.java robotInit().
     */
    public static void update() {
        sdbUpdate();
    }

    /**
     * Initialize drive configuration setup.
     */
    public static void drvsInit() {
    }

    /**
     * Initialize other motors besides the drive motors.
     */
    private static void motorsInit() {
    }

    /**
     * Initialize climber motors for rotation.
     * --- Due to issues with the CAN buss ---
     * <p>This is called from here & Climber case 1, to reinitialize again. ---
     */
    public static void climberMtrsInit() {
    }

    public static void sdbInit() {
    }

    public static void sdbUpdate() {
    }

    private static void updTPF(){
    }
}
