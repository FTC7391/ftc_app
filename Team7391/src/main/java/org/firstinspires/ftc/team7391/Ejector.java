package org.firstinspires.ftc.team7391;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Ishaa on 11/26/2017.
 */

public class Ejector {

    private static CRServo ejectorLeft;
    private static CRServo ejectorRight;

    public Ejector(HardwareMap hardwareMap){
        ejectorLeft = hardwareMap.crservo.get("ejector_left");
        ejectorRight = hardwareMap.crservo.get("ejector_right");

        stop();
    }

    public void grab(){
        ejectorLeft.setDirection(CRServo.Direction.FORWARD);
        ejectorLeft.setPower(1.0);

        ejectorRight.setDirection(CRServo.Direction.REVERSE);
        ejectorRight.setPower(1.0);
    }

    public void release(){
        ejectorLeft.setDirection(CRServo.Direction.REVERSE);
        ejectorLeft.setPower(1.0);

        ejectorRight.setDirection(CRServo.Direction.FORWARD);
        ejectorRight.setPower(1.0);
    }

    public void stop(){
        ejectorLeft.setPower(0.0);
        ejectorRight.setPower(0.0);
    }
}
