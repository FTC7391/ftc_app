package org.firstinspires.ftc.team7391;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by JoshuaHaines on 11/15/15.
 */
public class ServoAttachment {

    protected Servo servo;

    private final double RETRACTED_POS;
    private final double DRIVE_POS;
    private final double DEPLOYED_POS;

    public ServoAttachment(double retractedPos, double drivePos, double deployedPos){
        RETRACTED_POS = retractedPos;
        DRIVE_POS = drivePos;
        DEPLOYED_POS = deployedPos;
    }

    protected void setRetractedPosition(){
        
        servo.setPosition(RETRACTED_POS);
    }
    
    protected void setDrivePosition(){

        servo.setPosition(DRIVE_POS);

    }

    protected void setDeployedPosition(){

        servo.setPosition(DEPLOYED_POS);
        
    }

    protected enum TestModes {
        SERVO_RETRACT,
        SERVO_DRIVE,
        SERVO_DEPLOY
    }


    protected void setTestMode(TestModes mode) {
        switch (mode) {
            case SERVO_RETRACT:
                setRetractedPosition();
                break;
            case SERVO_DRIVE:
                setDrivePosition();    //negative power = backwards
                break;
            case SERVO_DEPLOY:
                setDeployedPosition();    //negative power = backwards
                break;
        }
    }


}
