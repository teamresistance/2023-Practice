// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
	//Declare Hardware Inputs
    private static DigitalOutput ledRed = new DigitalOutput(0);
	private static DigitalOutput ledYel = new DigitalOutput(1);
	private static DigitalOutput ledGrn = new DigitalOutput(2);
	//Declare and define hardware outputs
	private static DigitalInput btnRed = new DigitalInput(4);
	private static DigitalInput btnYel = new DigitalInput(5);
	private static DigitalInput btnGrn = new DigitalInput(6);

	//Variables
	private static int btnEncode = 0;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any
	 * initialization code.
	 */
	@Override
	public void robotInit() {
	}

	/**
	 * This function is called every 20 ms, no matter the mode. Use this for items
	 * like diagnostics that you want ran during disabled, autonomous, 
	 * teleoperated and test.
	 * <p>
	 * This runs after the mode specific periodic functions, but before LiveWindow
	 * and SmartDashboard integrated updating.
	 */
	@Override
	public void robotPeriodic() {
	}

	/** This function is called once when autonomous is enabled. */
	@Override
	public void autonomousInit() {
	}

	/** This function is called periodically during autonomous. */
	@Override
	public void autonomousPeriodic() {
	}

	/** This function is called once when teleop is enabled. */
	@Override
	public void teleopInit() {
	}

	/** This function is called periodically during operator control. */
	@Override
	public void teleopPeriodic() {
		updateLED();
		// ledCtl.ledRed.set(false);
	}

	/** This function is called once when the robot is disabled. */
	@Override
	public void disabledInit() {
	}

	/** This function is called periodically when disabled. */
	@Override
	public void disabledPeriodic() {
	}

	/** This function is called once when test mode is enabled. */
	@Override
	public void testInit() {
	}

	/** This function is called periodically during test mode. */
	@Override
	public void testPeriodic() {
	}

	/** This function is called once when the robot is first started up. */
	@Override
	public void simulationInit() {
	}

	/** This function is called periodically whilst in simulation. */
	@Override
	public void simulationPeriodic() {
	}

	/**
	 * Update the LEDs based on associated DI's.
	 */
	public static void updateLED(){
		// btnEncode = enc3Bool(btnRed.get(), btnYel.get(), btnGrn.get());
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
