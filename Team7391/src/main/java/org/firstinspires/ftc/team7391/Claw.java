package org.firstinspires.ftc.team7391;

import android.util.Log;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by nikashkhanna on 10/23/16.
 */
public class Claw{

    private static final double LEFT_RETRACTED_POS = .45; //  //init
    private static final double LEFT_DRIVE_POS = .6; //drive and retracted same?
    private static final double LEFT_DEPLOYED_POS = .7; //.6 for sure   before grabbing ball/wide

    private static final double RIGHT_RETRACTED_POS = .6; //.6 for sure     //init
    private static final double RIGHT_DRIVE_POS = .5; //drive and retracted same?
    private static final double RIGHT_DEPLOYED_POS = .4; //     before grabbing ball/wide

    private static ServoAttachment clawLeft;
    private static ServoAttachment clawRight;

    private static boolean bInitialFlag = false;


    public static void init(HardwareMap hardwareMap){
        Log.i("FTC7391", "Claw: setRetractedPosition" + bInitialFlag);

        //if (!bInitialFlag) {  //== true first time for some unknown reasone
            bInitialFlag = true;

            Log.i("FTC7391", "Claw: setRetractedPosition" + bInitialFlag);
            clawLeft = new ServoAttachment(LEFT_RETRACTED_POS, LEFT_DRIVE_POS, LEFT_DEPLOYED_POS);
            clawLeft.servo = hardwareMap.servo.get("claw_left");

            clawRight = new ServoAttachment(RIGHT_RETRACTED_POS, RIGHT_DRIVE_POS, RIGHT_DEPLOYED_POS);
            clawRight.servo = hardwareMap.servo.get("claw_right");
            setRetractedPosition();
        //}

    }

    public static void setRetractedPosition(){

        Log.i("FTC7391", "Claw: setRetractedPosition");

        clawLeft.setRetractedPosition();
        clawRight.setRetractedPosition();
    }

    public static void setDrivePosition(){

        Log.i("FTC7391", "Claw: setDrivePosition");

        clawLeft.setDrivePosition();
        clawRight.setDrivePosition();
    }

    public static void setDeployedPosition(){

        Log.i("FTC7391", "Claw: setDeployedPosition");

        clawLeft.setDeployedPosition();
        clawRight.setDeployedPosition();
    }


}
