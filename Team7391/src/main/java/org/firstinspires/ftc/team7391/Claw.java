package org.firstinspires.ftc.team7391;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by nikashkhanna on 10/23/16.
 */
public class Claw{

    private static final double LEFT_RETRACTED_POS = .5; //.5 for sure
    private static final double LEFT_DRIVE_POS = .5; //drive and retracted same?
    private static final double LEFT_DEPLOYED_POS = .6; //.6 for sure

    private static final double RIGHT_RETRACTED_POS = .6; //.6 for sure
    private static final double RIGHT_DRIVE_POS = .6; //drive and retracted same?
    private static final double RIGHT_DEPLOYED_POS = .5; //.5 for sure

    private static ServoAttachment clawLeft;
    private static ServoAttachment clawRight;


    public static void init(HardwareMap hardwareMap){
        clawLeft = new ServoAttachment(LEFT_RETRACTED_POS,LEFT_DRIVE_POS,LEFT_DEPLOYED_POS);
        clawLeft.servo = hardwareMap.servo.get("claw_left");

        clawRight = new ServoAttachment(RIGHT_RETRACTED_POS,RIGHT_DRIVE_POS,RIGHT_DEPLOYED_POS);
        clawRight.servo = hardwareMap.servo.get("claw_right");
        setRetractedPosition();

    }

    public static void setRetractedPosition(){

        clawLeft.setRetractedPosition();
        clawRight.setRetractedPosition();
    }

    public static void setDrivePosition(){

        clawLeft.setDrivePosition();
        clawRight.setDrivePosition();
    }

    public static void setDeployedPosition(){

        clawLeft.setDeployedPosition();
        clawRight.setDeployedPosition();
    }


}
