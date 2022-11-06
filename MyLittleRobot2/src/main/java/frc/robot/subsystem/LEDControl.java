// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystem;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/** Interlock 3 DI's to control 3 DO's. */
public class LEDControl {
	//Declare Hardware Inputs
    private static DigitalOutput ledRed = new DigitalOutput(0);
	private static DigitalOutput ledYel = new DigitalOutput(1);
	private static DigitalOutput ledGrn = new DigitalOutput(2);
	//Declare and define hardware outputs
	private static DigitalInput btnRed = new DigitalInput(4);
	private static DigitalInput btnYel = new DigitalInput(5);
	private static DigitalInput btnGrn = new DigitalInput(6);

	//Joystick(s)
	public static Joystick js1 = new Joystick(3);
	public static XboxController xb1 = new XboxController(4);
	// public static JoystickButton btnR = new JoystickButton(js1, 2);
	// public static JoystickButton btnY = new JoystickButton(js1, 4);
	// public static JoystickButton btnG = new JoystickButton(js1, 1);

	public static Joystick nt1 = new Joystick(5);
	public static JoystickButton btnR; // = new JoystickButton(nt1, 2);
	public static JoystickButton btnY; // = new JoystickButton(nt1, 3);
	public static JoystickButton btnG; // = new JoystickButton(nt1, 4);

	//Variables
	private static int btnEncode = 0;

	/**
	 * Constructor, default.  Assigns red, yel & grn 
	 * DI channels to 0, 1 & 2, respectfully.
	 */
	// public LEDControl(){
	// 	//Define Inputs
	// 	// ledRed = new DigitalOutput(0);
	// 	// ledYel = new DigitalOutput(1);
	// 	// ledGrn = new DigitalOutput(2);
	// 	this(0, 1, 2);
	// }

	// /**
	//  * Constructor.  Assigns red, yel & grn 
	//  * DI channels to passed values, respectfully 
	//  * 
	//  * @param inpChlRed DIO input channel for the Red button
	//  * @param inpChlYel DIO input channel for the Yellow button
	//  * @param inpChlGrn DIO input channel for the Green button
	//  */
	// public LEDControl(int inpChlRed, int inpChlYel, int inpChlGrn){
	// 	//Define Inputs
	// 	ledRed = new DigitalOutput(inpChlRed);
	// 	ledYel = new DigitalOutput(inpChlYel);
	// 	ledGrn = new DigitalOutput(inpChlGrn);
	// }

	public static void init(){
	}
	/**
	 * Update the LEDs based on associated DI's.
	 */
	public static void update(){
		btnEncode = enc3Bool(btnRed.get(), btnYel.get(), btnGrn.get());

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
