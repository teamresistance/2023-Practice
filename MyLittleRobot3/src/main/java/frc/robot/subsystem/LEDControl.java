// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystem;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.io.hdw_io.IO;
import frc.io.hdw_io.util.InvertibleDigitalInput;
import frc.io.joysticks.JS_IO;
import frc.io.joysticks.util.Button;

/** Interlock 3 DI's (or joystick buttons) to control 3 DO's. */
public class LEDControl {
	//Declare Hardware Inputs.  Or reference hdw defined in IO.java.
    private static DigitalOutput ledRed = IO.doLedRed;  //new DigitalOutput(0);
	private static DigitalOutput ledYel = IO.doLedYel;  //new DigitalOutput(1);
	private static DigitalOutput ledGrn = IO.doLedGrn;  //new DigitalOutput(2);
	//Declare and define hardware outputs  Or reference hdw defined in IO.java.
	private static InvertibleDigitalInput btnRed = IO.diBtnRed; //new DigitalInput(4);
	private static InvertibleDigitalInput btnYel = IO.diBtnYel; //new DigitalInput(5);
	private static InvertibleDigitalInput btnGrn = IO.diBtnGrn; //new DigitalInput(6);
	private static InvertibleDigitalInput btnLeft; // = IO.diBtnGrn; //new DigitalInput(6);	
	//Declare & define joystick axises, buttons & pov's.  Or reference buttons defined JS_IO.java.
	private static Button tgrRed = JS_IO.jsBtnRed; // = new JoystickButton(nt1, 2);
	private static Button tgrYel = JS_IO.jsBtnYel; // = new JoystickButton(nt1, 3);
	private static Button tgrGrn = JS_IO.jsBtnGrn; // = new JoystickButton(nt1, 4);
	private static Button tgrLeft = JS_IO.jsBtnLeft; // = new JoystickButton(nt1, 1);

	//Variables
	private static int btnEncode = 0;

	/**
	 * Constructor, default.  Assigns red, yel & grn 
	 * DI channels to 0, 1 & 2, respectfully.
	 */
	public LEDControl(){
		//Define Inputs
		// ledRed = new DigitalOutput(0);
		// ledYel = new DigitalOutput(1);
		// ledGrn = new DigitalOutput(2);
		//or
		this(0, 1, 2);
	}

	/**
	 * Constructor.  Assigns red, yel & grn 
	 * DI channels to passed values, respectfully 
	 * 
	 * @param inpChlRed DIO input channel for the Red button
	 * @param inpChlYel DIO input channel for the Yellow button
	 * @param inpChlGrn DIO input channel for the Green button
	 */
	public LEDControl(int inpChlRed, int inpChlYel, int inpChlGrn){
		//Define Inputs  --- CANNOT do this if defining hdw in hdw_IO/IO.java ---
		// ledRed = new DigitalOutput(inpChlRed);
		// ledYel = new DigitalOutput(inpChlYel);
		// ledGrn = new DigitalOutput(inpChlGrn);
		// btnRed = IO.diBtnRed; //new DigitalInput(4);
		// btnYel = IO.diBtnYel; //new DigitalInput(5);
		// btnGrn = IO.diBtnGrn; //new DigitalInput(6);
	
	}


	/**
	 * Initialize anything needed for LEDControl.
	 * <p>Usually called from robot.java.
	 */
	public static void init(){
		// tgrRed = JS_IO.jsBtnRed; // = new JoystickButton(nt1, 2);
		// tgrYel = JS_IO.jsBtnYel; // = new JoystickButton(nt1, 3);
		// tgrGrn = JS_IO.jsBtnGrn; // = new JoystickButton(nt1, 4);
		cmdUpdate(false, false, false);
	}
	
	/**
	 * Update the LEDs based on associated DI's or joysticks.
	 * <p>Usually called from robot.java.
	 */
	public static void update(){
		sdbUpdate();
		btnEncode = enc3Bool(tgrRed.isDown(), tgrYel.isDown(), tgrGrn.isDown());
		// btnEncode = enc3Bool(btnRed.get(), btnYel.get(), btnGrn.get());
		// btnEncode = tgrRed.isDown() ? 1 : 0;
		// System.out.print("jsRed isDn? " + tgrRed.isDown());
		// System.out.print("\tjsRed btnID? " + tgrRed.getButtonID());
		// System.out.print("\tjsRed ex? " + tgrRed.getExists());
		// System.out.println("\tjsRed exDflt? " + tgrRed.getExistDflt());

		switch(btnEncode){
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
			System.out.println("Bad selection");

		}

		// int a = 0;  //temp, used to add break point.

		// //Simple way to do the above.  Above is easier to modify quickly.
        // if (btnRed.get()) {							//Ex. if/then/else
		// 	ledRed.set(true);
		// } else {
		// 	if(btnGrn.get()) ledRed.set(false);
		// }
		// ledYel.set((btnYel.get() ? true : false));	//Ex. BPJ Lesson 33, Selection Op
		// ledGrn.set(btnGrn.get());					//Ex. Just pass DI value.

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
			System.out.println("Here1");
			ledRed.set(false);
			ledYel.set(false);
			ledGrn.set(false);	
		}else{
			System.out.println("Here2");
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
        SmartDashboard.putBoolean("LED/Red Trigger", tgrRed.isDown());
        SmartDashboard.putBoolean("LED/Yel Trigger", tgrYel.isDown());
        SmartDashboard.putBoolean("LED/Grn Trigger", tgrGrn.isDown());
        SmartDashboard.putBoolean("LED/Left Trigger", tgrLeft.isDown());
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
