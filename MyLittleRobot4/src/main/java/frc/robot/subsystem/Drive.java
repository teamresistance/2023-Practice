package frc.robot.subsystem;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.io.hdw_io.IO;
import frc.io.joysticks.JS_IO;
import frc.io.joysticks.util.Axis;

public class Drive {
    private static DifferentialDrive jrDrv = IO.diffDrv_M;

    private static Axis tankDvrL = JS_IO.leftY;
    private static Axis tankDvrR = JS_IO.rightY;

    public static void init(){

    }

    public static void update(){
        jrDrv.tankDrive(tankDvrL.get(), tankDvrR.get());
    }
}
