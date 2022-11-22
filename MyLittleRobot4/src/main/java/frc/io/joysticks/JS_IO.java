package frc.io.joysticks;
/*
Original Author: Joey & Anthony
Rewite Author: Jim Hofmann
History:
J&A - 11/6/2019 - Original Release
JCH - 11/6/2019 - Original rework
JCH - 2/13/2022 - Got rid of jsConfig num.  Use chooser
TODO: Exception for bad or unattached devices.
      Auto config based on attached devices and position?
      Add enum for jsID & BtnID?  Button(eLJS, eBtn6) or Button(eGP, eBtnA)
Desc: Reads joystick (gamePad) values.  Can be used for different stick configurations
    based on feedback from Smartdashboard.  Various feedbacks from a joystick are
    implemented in classes, Button, Axis & Pov.
    This version is using named joysticks to istantiate axis, buttons & axis
*/

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.io.joysticks.util.Axis;
import frc.io.joysticks.util.Button;
import frc.io.joysticks.util.Pov;

//TODO: ASSIGN BUTTON PORTS FOR EACH BUTTON INITIALIZED !!!

//Declares all joysticks, buttons, axis & pov's.
public class JS_IO {
    private static int jsConfig;
    private static String prvJSAssign;

    // Declare all possible Joysticks
    public static Joystick leftJoystick = new Joystick(0);  // Left JS
    public static Joystick rightJoystick = new Joystick(1); // Right JS
    public static Joystick coJoystick = new Joystick(2);    // Co-Dvr JS
    public static Joystick gamePad = new Joystick(3);       // Normal mode only (not Dual Trigger mode)
    public static Joystick neoPad = new Joystick(4);        // Nintendo pamepad

    // Drive
    // Misc
    public static Button jsBtnRed= new Button();
    public static Button jsBtnYel = new Button();
    public static Button jsBtnGrn = new Button();
    public static Button jsBtnLeft = new Button();
    public static Button jsBtnRight = new Button();

     // Constructor not needed, bc
    public JS_IO() {
        init();
    }

    public static void init() {
        chsrInit(); //Setup JS chooser and set JS assignments to default.
    }

    //---- Joystick controller chooser ----
    private static SendableChooser<String> chsr = new SendableChooser<String>();
    /**Joystick strings to ID chooser selections. */
    private static final String[] chsrDesc = {"3-Joysticks", "2-Joysticks", "Gamepad", "Nintendo"};

    /** Setup the JS Chooser. <p>Called from Robot.java/robot.init. */
    public static void chsrInit(){
        for(int i = 0; i < chsrDesc.length; i++){
            chsr.addOption(chsrDesc[i], chsrDesc[i]);
        }
        chsr.setDefaultOption(chsrDesc[2], chsrDesc[2]);    //Chg index to select chsrDesc[] for default
        SmartDashboard.putData("JS/Choice", chsr);
        update();   //Update the JS assignments
    }

    /**HMMmm?  Called from below.  Might have been called from somewhere else 
     * but deleted since then.  Might need to do some cleanup.
     */
    public static void sdbUpdChsr(){
        SmartDashboard.putString("JS/Choosen", chsr.getSelected());   //Put selected on sdb
    }

    /**Needs some serious cleanup.  Calls caseDefault() then calls configJS()
     * which then calls caseDefault() again.
     */
    public static void update() { // Chk for Joystick configuration
        // System.out.println("JS btn " + jsBtnRed.isDown());
        // System.out.println("Prv JS Assn: " + prvJSAssign + " =? "+ chsr.getSelected());
        if (prvJSAssign != (chsr.getSelected() == null ? chsrDesc[0] : chsr.getSelected())) {
            prvJSAssign = chsr.getSelected();
            sdbUpdChsr();
            caseDefault();      //Clear exisitng jsConfig
            System.out.println("JS Chsn: " + chsr.getSelected());
            configJS();         //then assign new jsConfig
        }
    }

    /**Configure a new JS assignment */
    public static void configJS() { // Configure JS controller assignments
        caseDefault();          //Clear exisitng jsConfig

        // // Convert selected to a integer, index
        // for(jsConfig = 0; jsConfig < chsrDesc.length; jsConfig++){
        //     if(prvJSAssign == chsrDesc[jsConfig]) break;
        // }

        switch (prvJSAssign) {  //then assign new assignments
            case "3-Joysticks": // Normal 3 joystick config
                norm3JS();
                break;
            case "2-Joysticks": // Normal 2 joystick config No CoDrvr
                norm2JS();
                break;
            case "Gamepad":     // Gamepad only
                a_GP();
                break;
            case "Nintendo":    // Nintendo only
                a_NP();
                break;
            default:            // Bad assignment
                System.out.println("Bad JS choice - " + prvJSAssign);
                break;
        }
    }

    // ================ Controller actions ================

    /**
     * ---- Normal 3 Joysticks ----<p>This is the normal left, right & co-driver driver sticks.
     */
    private static void norm3JS() {
        System.out.println("JS assigned to 3JS");

        // All stick axisesssss

        // Buttons
        jsBtnRed.setButton(leftJoystick, 3);
        jsBtnYel.setButton(rightJoystick, 3);
        jsBtnGrn.setButton(coJoystick, 3);
        jsBtnLeft.setButton(coJoystick, 4);
        jsBtnRight.setButton(coJoystick, 5);

    }

    /**
     * ---- Normal 2 Joysticks ----<p>Usually just the left & right driver sticks.
     */
    private static void norm2JS() {
        System.out.println("JS assigned to 2JS");

    }

    /**
     * ---- One Xbox Gamepad only ----<p>Usually for testing or demostrations.
     */
    private static void a_GP() {
        System.out.println("JS assigned to GP");

        // Button
        jsBtnRed.setButton(gamePad, 2);
        jsBtnYel.setButton(gamePad, 4);
        jsBtnGrn.setButton(gamePad, 1);
        jsBtnLeft.setButton(gamePad, 5);
        jsBtnRight.setButton(gamePad, 6);

    }

    /**
     * ---- One Nintendo gamepad ----<p>Usually for just testing.
     */
    private static void a_NP() {
        System.out.println("JS assigned to NP");

        // Button
        jsBtnRed.setButton(neoPad, 2);
        jsBtnYel.setButton(neoPad, 3);
        jsBtnGrn.setButton(neoPad, 4);
        jsBtnLeft.setButton(neoPad, 5);
        jsBtnRight.setButton(neoPad, 7);

    }

    /**
     * ----- Case Default -----
     * <p>Clear ALL Joystick assignments & set defaults.
     * <p>This sets up axis, button or pov to return a default value when they are not assigned.
     * Prevents returning an undetermined value.
     */
    private static void caseDefault() {
        System.out.println("JS assigned to null");

        // Buttons
        jsBtnRed.setButton(null, 0);
        jsBtnYel.setButton(null, 0);
        jsBtnGrn.setButton(null, 0);
        jsBtnLeft.setButton(null, 0);
        jsBtnRight.setButton(null, 0);

    }
}