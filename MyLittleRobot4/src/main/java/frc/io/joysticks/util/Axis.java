package frc.io.joysticks.util;
/*
Original Author: Sherya
Rewite Author: Jim Hofmann
History:
JCH - 11/6/2019 - rework
S - 3/6/2017 - Original release
TO DO: more testing.  maybe add an array handler?
Desc: Allows use of various joystick/gamepad configurations.
Constructor get JS_ID and axisID.  However, if the it needs to pass a default (axis may not
exist for some combinations) then in axisID pass 10 * default value + 100 ( 10 * -default - 100).
IE., to default 0.0 pass 100.  For -1.0 pass -110.  For 1.0 pass 110.
*/

import edu.wpi.first.wpilibj.Joystick;

/**
 * Allows use of various joystick/gamepad configurations.
 * <p>Constructor get JS_ID and axisID.  However, if it needs to pass a default (axis may not
 * exist for some combinations) then in axisID pass 10 * default value + 100 ( 10 * -default - 100).
 * IE., to default 0.0 pass 100.  For -1.0 pass -110.  For 1.0 pass 110.
 */
public class Axis{
	
	private Joystick joystick;
	private int axisID;
	private boolean exists;
	private double exDefault;
	
	/**
	 * Constructor, normal
	 * <p>Exists muxed with axisID, if GT 100 (LT 0) does not exist
	 * @param injoystick
	 * @param inaxisID
	 */
	public Axis(Joystick injoystick, int inaxisID) {
		joystick = injoystick;
		axisID = inaxisID;
		exists = joystick != null;
		exDefault = 0;	// default to 0
	}

	/**
	 * Constructor, set to does not exist & default to 0.0.
	 */
	public Axis() {
		this.exists = false;
		this.exDefault = 0.0;
	}

	/**
	 * Constructor, set to does not exist & the default value to passed value.
	 * @param exDefault Value returned when axis does not exist.
	 */
	public Axis(double exDefault) {
		this.exists = false;
		this.exDefault = exDefault;
	}

	/**
	 * Assign a new joystick & button.
	 * <p> If joystick is null, buttonID is default value return.
	 * @param injoystick
	 * @param inAxisID
	 */
	public void setAxis(Joystick injoystick, int inAxisID){
		joystick = injoystick;
		axisID = inAxisID;
		exists = joystick != null;
		exDefault = 0;
	}

	/**
	 * Joystick does not exist, set to null, and set the default value returned to value passed.
	 * @param dfltValue
	 */
	public void setAxis(double dfltValue){
		joystick = null;
		axisID = 0;
		exists = false;
		exDefault = dfltValue;
	}

	/**Clear assignment.  Joystick = null & axisID = 0. */
	public void setAxis(){
		setAxis(null, 0);
	}

	/** Get the axis value. */
	public double get() {
		return exists ? joystick.getRawAxis(axisID) : exDefault;
	}

	/** Get the Axis ID (number) */
	public int getAxisID(){ return axisID; }
	/** Return true if this axis is assigned, exists, else false. */
	public boolean getExists(){ return exists; }
	/** Return the default value if axis isn't assigned, exists. */
	public double getExistDflt(){ return exDefault; }

}