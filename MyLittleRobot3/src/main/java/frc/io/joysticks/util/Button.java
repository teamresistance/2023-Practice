package frc.io.joysticks.util;
/*
Original Author: Sherya
Rewite Author: Jim Hofmann
History:
JCH - 11/6/2019 - rework
S - 3/6/2017 - Original release
TO DO: more testing.  maybe add an array handler?
Desc: Allows use of various joystick/gamepad configurations.
Constructor get JS_ID and axisID.  However, if the it needs to pass a default (button may not
exist for some combinations) then in buttonID pass 100 (even) for true, 101 (odd for false).
*/

import edu.wpi.first.wpilibj.Joystick;

/**
 * Allows use of various joystick/gamepad configurations.
 * <p>Constructor gets JS_ID and axisID.  However, if the it needs to pass a default (button
 * may not exist for some combinations) then if buttonID pass is greater or equal to 0 the default 
 * value returned is false, else true.
 */
public class Button{
	
	private Joystick joystick;
	private int buttonID;
	// public int buttonID;
	private boolean exists;
	private boolean existDflt;
	
	/** Constructor, normal.
	 * 
	 * @param injoystick - joystick button is on
	 * @param inbuttonID - button number
	 */
	public Button(Joystick injoystick, int inbuttonID) {
		joystick = injoystick;
		buttonID = inbuttonID;
		exists = joystick != null;
		existDflt = buttonID != -1 ? false : true;	// default to false, if -1 default to true
	}

	/** Constructor, set to does not exist & default to false. */
	public Button() {
		exists = false;
		existDflt = false;
	}

	/** Constructor, set to does not exist & passed value as default. */
	public Button(boolean exDefault) {
		exists = false;
		existDflt = exDefault;
	}

	/** Assign a new joystick & button. */
	public void setButton(Joystick injoystick, int inbuttonID){
		joystick = injoystick;
		buttonID = inbuttonID;
		exists = joystick != null;
		existDflt = buttonID >= 0 ? false : true;	// default to false, if -1 default to true
	}

	/** Clear assignment.  Joystick = null & buttonID = 0. */
	public void setButton(){
		setButton(null, 0);
	}

	/** Return current value. */
	public boolean isDown() {
		return exists ? joystick.getRawButton(buttonID) : existDflt;
	}

	/** Return inverse of the current value. */
	public boolean isUp() {
		return exists ? !joystick.getRawButton(buttonID) : !existDflt;
	}

	/** Return true only once when button pressed, false even if still pressed.  */
	public boolean onButtonPressed() {
		return exists ? joystick.getRawButtonPressed(buttonID) : existDflt;
	}

	/** Return true only once when button is released, false even if still released.  */
	public boolean onButtonReleased() {
		return exists ? joystick.getRawButtonReleased(buttonID) : existDflt;
	}

	/** Get the button ID (number) */
	public int getButtonID(){ return buttonID; }
	/** Return true if this button is assigned, exists, else false. */
	public boolean getExists(){ return exists; }
	/** Return the default value returned if button isn't assigned, exists. */
	public boolean getExistDflt(){ return existDflt; }

}