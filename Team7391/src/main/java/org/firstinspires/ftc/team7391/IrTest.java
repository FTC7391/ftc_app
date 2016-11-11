package org.firstinspires.ftc.team7391;

/**
 * Created by ArjunVerma on 8/29/15.
 */

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;


public class IrTest extends OpMode
{
    IrSeekerSensor irSeeker;
    private static final String TAG = AutoOp.class.getSimpleName();

    private double angle = 0;
    private double strength = 0;

    @Override
    public void init()
    {

        irSeeker = hardwareMap.irSeekerSensor.get("irSeeker");
    }

    @Override
    public void loop()
    {
        angle = irSeeker.getAngle();
        strength = irSeeker.getStrength();

        telemetry.addData("TAG", "angle: " + angle + "strength: " + strength);

    }

    @Override
    public void stop()
    {
        telemetry.addData("TAG", "Test Stopped");
    }

}
