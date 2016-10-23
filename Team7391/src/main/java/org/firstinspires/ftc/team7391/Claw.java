package org.firstinspires.ftc.team7391;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by nikashkhanna on 10/23/16.
 */
public class Claw extends ServoAttachment{

    //    private final double ARETRACTED_POS = 0;
//    private final double DRIVE_POS = 10;
//    private final double DEPLOYED_POS = 86;


    public Claw(HardwareMap hardwareMap, double retracted, double drive, double deploy, String name) {
        super(retracted, drive, deploy);
        servo = hardwareMap.servo.get(name);
    }

}
