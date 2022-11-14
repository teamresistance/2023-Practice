package frc.io.hdw_io;

import edu.wpi.first.wpilibj.DigitalOutput;
import frc.io.hdw_io.util.*;


public class IO {
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
