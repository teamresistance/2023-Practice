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

/** Interlock 3 DI's to control 3 DO's. */
public class LEDControl {
	//Declare Hardware Inputs
    private static DigitalOutput ledRed = IO.doLedRed;  //new DigitalOutput(0);
	private static DigitalOutput ledYel = IO.doLedYel;  //new DigitalOutput(1);
	private static DigitalOutput ledGrn = IO.doLedGrn;  //new DigitalOutput(2);
	//Declare and define hardware outputs
	private static InvertibleDigitalInput btnRed = IO.diBtnRed; //new DigitalInput(4);
	private static InvertibleDigitalInput btnYel = IO.diBtnYel; //new DigitalInput(5);
	private static InvertibleDigitalInput btnGrn = IO.diBtnGrn; //new DigitalInput(6);
	//Declare & define joystick axises, buttons & pov's
	private static Button tgrRed = JS_IO.jsBtnRed; // = new JoystickButton(nt1, 2);
	private static Button tgrYel = JS_IO.jsBtnYel; // = new JoystickButton(nt1, 3);
	private static Button tgrGrn = JS_IO.jsBtnGrn; // = new JoystickButton(nt1, 4);

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
		//Define Inputs  --- CANNOT do this if defineing hdw in hdw_IO/IO.java ---
		// ledRed = new DigitalOutput(inpChlRed);
		// ledYel = new DigitalOutput(inpChlYel);
		// ledGrn = new DigitalOutput(inpChlGrn);
		// btnRed = IO.diBtnRed; //new DigitalInput(4);
		// btnYel = IO.diBtnYel; //new DigitalInput(5);
		// btnGrn = IO.diBtnGrn; //new DigitalInput(6);
	
	}

	public static void init(){
		// tgrRed = JS_IO.jsBtnRed; // = new JoystickButton(nt1, 2);
		// tgrYel = JS_IO.jsBtnYel; // = new JoystickButton(nt1, 3);
		// tgrGrn = JS_IO.jsBtnGrn; // = new JoystickButton(nt1, 4);
	}
	
	/**
	 * Update the LEDs based on associated DI's.
	 */
	public static void update(){
		btnEncode = enc3Bool(tgrRed.isDown(), tgrYel.isDown(), tgrGrn.isDown());
		// btnEncode = tgrRed.isDown() ? 1 : 0;
		// System.out.print("jsRed isDn? " + tgrRed.isDown());
		// System.out.print("\tjsRed btnID? " + tgrRed.getButtonID());
		// System.out.print("\tjsRed ex? " + tgrRed.getExists());
		// System.out.println("\tjsRed exDflt? " + tgrRed.getExistDflt());
        SmartDashboard.putBoolean("LED/Red Btn", tgrRed.isDown());
        SmartDashboard.putBoolean("LED/Yel Btn", tgrYel.isDown());
        SmartDashboard.putBoolean("LED/Grn Btn", tgrGrn.isDown());

		switch(btnEncode){
			case 0:
			ledRed.set(false);
			ledYel.set(false);
			ledGrn.set(false);
			break;
			case 1:
			ledRed.set(false);
			ledYel.set(false);
			ledGrn.set(true);
			break;
			case 2:
			ledRed.set(false);
			ledYel.set(true);
			ledGrn.set(false);
			break;
			case 3:
			ledRed.set(false);
			ledYel.set(true);
			ledGrn.set(true);
			break;
			case 4:
			ledRed.set(true);
			ledYel.set(false);
			ledGrn.set(false);
			break;
			case 5:
			ledRed.set(true);
			ledYel.set(false);
			ledGrn.set(true);
			break;
			case 6:
			ledRed.set(true);
			ledYel.set(true);
			ledGrn.set(false);
			break;
			case 7:
			ledRed.set(true);
			ledYel.set(true);
			ledGrn.set(true);
			break;
			default:
			ledRed.set(false);
			ledYel.set(false);
			ledGrn.set(false);
			System.out.println("Bad selection");

		}

		// int a = 0;  //temp, used to add break point.

		// //Simple way to do the above.  Above is easier to modify quickly.
        // if (btnRed.get()) {
		// 	ledRed.set(true);
		// } else {
		// 	if(btnGrn.get()) ledRed.set(false);
		// }
		// ledYel.set((btnYel.get() ? true : false));	//Leson 33, Selection Op
		// ledGrn.set(btnGrn.get());

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
