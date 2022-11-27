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
public class LEDBlink6 {
	//Declare Hardware outputs and reference hdw defined in IO.java.
    private static DigitalOutput ledRed = IO.doLedRed;
	private static DigitalOutput ledYel = IO.doLedYel;
	private static DigitalOutput ledGrn = IO.doLedGrn;
	//Declare & reference buttons defined JS_IO.java.
	private static Button tgrRed = JS_IO.jsBtnRed;
	private static Button tgrYel = JS_IO.jsBtnYel;
	private static Button tgrGrn = JS_IO.jsBtnGrn;
	private static Button tgrLeft = JS_IO.jsBtnLeft;
	private static Button tgrRight = JS_IO.jsBtnRight;

	//Variables
	private static int state = 0;
	private static int newState = 0;
	private static double delay = 1.0;
	private static Timer stateTmr = new Timer(delay);

	/**
	 * Constructor, default.  No action, for standards only.
	 */
	private LEDBlink6(){}
	
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
	 * <p>When Red trigger is pressed blink Red for 1.0 sec. then Yel then Grn for 0.5 sec.
	 * <p>Oh, Repeat sequences for Yel, Grn, Red & Grn, Red, Yel.
	 * <p>BUT don't switch sequence until running sequence is complete.
	 * <p>AND button should only need to be pressed, not held for upto 2 sec.
	 * <p>AND After safety Left is released resume last sequence.  Right cancels all seq when done.
	 * <p>OH while you're at it, make the 1.0 sec. time adjustable from the SDB.
	 * <p>Usually called from robot.java.
	 */
	public static void update(){
		if(tgrRight.isDown()) newState = 0;	//Shutdown at end of present sequence
		if(tgrLeft.isDown()) state = 90;	//Safety, all off now.  Restart at prv seq. when released.
		if(tgrRed.isDown())  newState = 1;	//Do Red sequence when running sequence is complete
		if(tgrYel.isDown())  newState = 11;	//Do Yel sequence when running sequence is complete
		if(tgrGrn.isDown())  newState = 21;	//Do Grn sequence when running sequence is complete

		smUpdate();		//Update state machine
		sdbUpdate();	//Update Smartdashboard
	}

	/**
	 * State Machine Update.
	 * <p>0 - Default, All off
	 * <p>1 - Start, Do Red trigger, Red LED 1.0s
	 * <p>2 - next Yel, Yel LED 0.5s
	 * <p>3 - next Grn, Grn LED 0.5s
	 * <p>4 - next, Chk for other triggers else Return to 1
	 * <p>11-14 - Yel Seq, Yel 1.0s, Grn 0.5s, Red 0.5
	 * <p>21-24 - Grn Seq, Grn 1.0s, Red 0.5s, Yel 0.
	 * <p>90 - Left Safety.  All off but return to last seq.
	 */
	public static void smUpdate(){
		switch(state){
			case 0:	//All off
			//         Red    Yel    Grn  Cmds
			if(newState != 0) state = newState;
			cmdUpdate(false, false, false);
			break;
			//--- Do Red trigger ---
			case 1:	//Red for 1 sec.
			if(stateTmr.hasExpired(delay, state)) state++;
			cmdUpdate(true, false, false);
			break;
			case 2:	//Yel for 0.5 sec.
			if(stateTmr.hasExpired(0.5, state)) state++;
			cmdUpdate(false, true, false);
			break;
			case 3:	//Grn for 0.5 sec.  Return to Red
			if(stateTmr.hasExpired(0.5, state)) state++;
			cmdUpdate(false, false, true);
			break;
			case 4:	//Chk for other trigger else Return to Red
			state = newState != 1 ? newState : 1;
			cmdUpdate(false, false, true);
			break;
			//--- Do Yel trigger ---
			case 11:	//Yel for 1 sec.
			if(stateTmr.hasExpired(delay, state)) state++;
			cmdUpdate(false, true, false);
			break;
			case 12:	//Grn for 0.5 sec.
			if(stateTmr.hasExpired(0.5, state)) state++;
			cmdUpdate(false, false, true);
			break;
			case 13:	//Red for 0.5 sec.  Return to Yel
			if(stateTmr.hasExpired(0.5, state)) state++;
			cmdUpdate(true, false, false);
			break;
			case 14:	//Chk for other trigger else Return to Yel
			state = newState != 11 ? newState : 11;
			cmdUpdate(true, false, false);
			break;
			//--- Do Grn trigger ---
			case 21:	//Grn for 1 sec.
			if(stateTmr.hasExpired(delay, state)) state++;
			cmdUpdate(false, false, true);
			break;
			case 22:	//Red for 0.5 sec.
			if(stateTmr.hasExpired(0.5, state)) state++;
			cmdUpdate(true, false, false);
			break;
			case 23:	//Yel for 0.5 sec.  Return to Grn
			if(stateTmr.hasExpired(0.5, state)) state++;
			cmdUpdate(false, true, false);
			break;
			case 24:	//Chk for other trigger else Return to Red
			state = newState != 21 ? newState : 21;
			cmdUpdate(false, true, false);
			break;
			//Left Safety.  All off but return to last seq.
			case 90:	//All off but return.
			if(!tgrLeft.isUp()) state++;
			cmdUpdate(false, false, false);
			break;
			case 91:	//Clear the timer and return
			stateTmr.clearTimer();
			state = newState;
			cmdUpdate(false, false, false);
			//Opps, bad state
			default:	//Bad State
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
		SmartDashboard.putNumber("LED/Blink/Time delay", delay);
	}

	/**
	 * Update to Smartdashboard.
	 */
	private static void sdbUpdate(){
        SmartDashboard.putNumber("LED/Blink/state", state);
		delay = SmartDashboard.getNumber("LED/Blink/Time delay", delay);

        SmartDashboard.putBoolean("LED/Tgr/Red", tgrRed.isDown());
        SmartDashboard.putBoolean("LED/Tgr/Yel", tgrYel.isDown());
        SmartDashboard.putBoolean("LED/Tgr/Grn", tgrGrn.isDown());
        SmartDashboard.putBoolean("LED/Tgr/Left", tgrLeft.isDown());
        SmartDashboard.putBoolean("LED/Tgr/Right", tgrRight.isDown());

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
