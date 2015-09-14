package FTC7391;

/**
 * Created by ArjunVerma on 8/29/15.
 */

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;


public class TestOpticalDistanceSensorOp extends OpMode
{
    OpticalDistanceSensor opticalSensor;
    private static final String TAG = AutoOp.class.getSimpleName();

    @Override
    public void init()
    {
        opticalSensor = hardwareMap.opticalDistanceSensor.get("optical");
    }

    @Override
    public void loop()
    {
        double lightDetected;
        int lightDetectedRaw;


        lightDetected = opticalSensor.getLightDetected();
        lightDetectedRaw = opticalSensor.getLightDetectedRaw();


        telemetry.addData("TAG", "lightDetected " + lightDetected + "lightDetectedRaw " + lightDetectedRaw);

    }

    @Override
    public void stop()
    {
        telemetry.addData("TAG", "Test Stopped");
    }

}
