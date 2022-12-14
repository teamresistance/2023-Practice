// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.io.hdw_io.IO;
import frc.io.joysticks.JS_IO;
import frc.robot.subsystem.Drive;
import frc.robot.subsystem.LEDBlink;
import frc.robot.subsystem.LEDBlink1;
import frc.robot.subsystem.LEDBlink2;
import frc.robot.subsystem.LEDBlink3;
import frc.robot.subsystem.LEDBlink4;
import frc.robot.subsystem.LEDBlink5;
import frc.robot.subsystem.LEDControl;

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

	// public LEDControl ledCtl = new LEDControl();
	// public LEDControl ledCtl = new LEDControl(0, 1, 2);

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any
	 * initialization code.
	 */
	@Override
	public void robotInit() {
		IO.init();
		JS_IO.init();
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
		IO.update();
		JS_IO.update();
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
		// LEDControl.init();	   //Initialize LED Control
		LEDBlink.init();		//Initialize LED Blink
		Drive.init();
	}

	/** This function is called periodically during operator control. */
	@Override
	public void teleopPeriodic() {
		// LEDControl.update();	//Update LED Control
		LEDBlink.update();		//Update LED Blink
		Drive.update();
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

}
