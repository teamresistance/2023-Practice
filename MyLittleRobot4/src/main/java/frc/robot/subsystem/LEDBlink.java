// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystem;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.io.hdw_io.IO;
import frc.io.joysticks.JS_IO;
import frc.io.joysticks.util.Button;
import frc.util.Timer;

/** Have joystick buttons trigger various LED patterns */
public class LEDBlink {
	//Declare Hardware outputs and reference hdw defined in IO.java.
    private static DigitalOutput ledRed = IO.doLedRed;
	private static DigitalOutput ledYel = IO.doLedYel;
	private static DigitalOutput ledGrn = IO.doLedGrn;
	//Declare & reference buttons defined JS_IO.java.
	private static Button tgrRed = JS_IO.jsBtnRed;
	private static Button tgrYel = JS_IO.jsBtnYel;
	private static Button tgrGrn = JS_IO.jsBtnGrn;
	private static Button tgrLeft = JS_IO.jsBtnLeft;

	//Variables
	private static int state = 0;
	private static Timer stateTmr = new Timer();

	/**
	 * Constructor, default.  No action, for standards only.
	 */
	private LEDBlink(){}
	
	/**
	 * Initialize anything needed for LEDControl.
	 * <p>Usually called from robot.java.
	 */
	public static void init(){
		sdbInit();
		state = 0;
		stateTmr.clearTimer();
		cmdUpdate(false, false, false);
	}
	
	/**
	 * Update the LEDs based on associated triggers.
	 * <p>Usually called from robot.java.
	 */
	public static void update(){
		state = enc3Bool(tgrRed.isDown(), tgrYel.isDown(), tgrGrn.isDown());

		smUpdate();
		sdbUpdate();
	}

	/**
	 * State Machine Update.
	 */
	public static void smUpdate(){
		switch(state){
			case 0:
			cmdUpdate(false, false, false);
			break;
			case 1:
			cmdUpdate(false, false, true);
			break;
			case 2:
			cmdUpdate(false, true, false);
			break;
			case 3:
			cmdUpdate(false, true, true);
			break;
			case 4:
			cmdUpdate(true, false, false);
			break;
			case 5:
			cmdUpdate(true, false, true);
			break;
			case 6:
			cmdUpdate(true, true, false);
			break;
			case 7:
			cmdUpdate(true, true, true);
			break;
			default:
			cmdUpdate(false, false, false);
			System.out.println("LED Blink bad state - " + state);

		}
	}

	/**
	 * Issue ALL hdw commands from here.
	 * <p>trgLeft is a safety!  IF true all LEDs must turn off!
	 * @param redCmd
	 * @param yelCmd
	 * @param grnCmd
	 */
	private static void cmdUpdate(boolean redCmd, boolean yelCmd, boolean grnCmd){
		if(tgrLeft.isDown()){
			ledRed.set(false);
			ledYel.set(false);
			ledGrn.set(false);	
		}else{
			ledRed.set(redCmd);
			ledYel.set(yelCmd);
			ledGrn.set(grnCmd);
		}
	}

	/**
	 * Initialize the Smartdashboard with items that need to be read from later.
	 * Items that we may want to adjust.
	 */
	private static void sdbInit(){
	}

	/**
	 * Update to Smartdashboard.
	 */
	private static void sdbUpdate(){
        SmartDashboard.putBoolean("LED/Tgr/Red", tgrRed.isDown());
        SmartDashboard.putBoolean("LED/Tgr/Yel", tgrYel.isDown());
        SmartDashboard.putBoolean("LED/Tgr/Grn", tgrGrn.isDown());
        SmartDashboard.putBoolean("LED/Tgr/Left", tgrLeft.isDown());

        SmartDashboard.putBoolean("LED/Out/Red", ledRed.get());
        SmartDashboard.putBoolean("LED/Out/Yel", ledYel.get());
        SmartDashboard.putBoolean("LED/Out/Grn", ledGrn.get());
	}

	/**
	 * 
	 * @param b2, most significant bit
	 * @param b1
	 * @param b0, least significant bit
	 * @return the encoded value between 0 to 7. 3 bits - b2=4, b1=2, b0=1
	 */
	private static int enc3Bool(boolean b2, boolean b1, boolean b0) {
		//Add values of bits
		// int d = b2 ? 4 : 0;
		// d += b1 ? 2 : 0;
		// d += b0 ? 1 : 0;

		//Another way to do it. 2^bit
		int d = b2 ? 1 : 0;	//Set b2
		d <<= 1;			//Multiply by 2
		d += b1 ? 1 : 0;	//Add the next bit
		d <<= 1;			//Multiply by 2
		d += b0 ? 1 : 0;	//Add last bit

		return d;
	}
}
