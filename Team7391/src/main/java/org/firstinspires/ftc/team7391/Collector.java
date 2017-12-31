package org.firstinspires.ftc.team7391;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Ishaa on 11/26/2017.
 */

public class Collector {

    private static DcMotor collectorLeft;
    private static DcMotor collectorRight;

    public Collector(HardwareMap hardwareMap){
        collectorLeft = hardwareMap.dcMotor.get("collector_left");
        collectorRight = hardwareMap.dcMotor.get("collector_right");

        collectorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        collectorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        stop();
    }

    public void grab(){
        collectorLeft.setDirection(DcMotor.Direction.FORWARD);
        collectorLeft.setPower(1.0);

        collectorRight.setDirection(DcMotor.Direction.REVERSE);
        collectorRight.setPower(1.0);
    }

    public void release(){
        collectorLeft.setDirection(DcMotor.Direction.REVERSE);
        collectorLeft.setPower(1.0);

        collectorRight.setDirection(DcMotor.Direction.FORWARD);
        collectorRight.setPower(1.0);
    }

    public void stop(){
        collectorLeft.setPower(0.0);
        collectorRight.setPower(0.0);
    }
}
