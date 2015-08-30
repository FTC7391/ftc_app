package FTC7391;

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

    @Override
    public void init()
    {
        irSeeker = hardwareMap.irSeekerSensor.get("irSeeker");
    }

    @Override
    public void loop()
    {
        double angle = 0;
        double strength = 0;

        angle = irSeeker.getAngle();
        strength = irSeeker.getStrength();

        telemetry.addData("TAG", angle);
        telemetry.addData("TAG", strength);

    }

    @Override
    public void stop()
    {
        telemetry.addData("TAG", "Test Stopped");
    }

}
