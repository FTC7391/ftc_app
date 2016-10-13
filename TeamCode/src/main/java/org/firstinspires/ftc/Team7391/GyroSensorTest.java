package FTC7391;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.GyroSensor;

/**
 * Created by ArjunVerma on 9/7/15.
 */
public class GyroSensorTest extends OpMode {

    GyroSensor gyroSensor;

    private double angle = 0.0;
    private double nullVal= 0.0;
    private double rawVal= 0.0;
    private static final String TAG = AutoOp.class.getSimpleName();

    public void init(){
        telemetry.addData("TAG", "Initialized");
        gyroSensor = hardwareMap.gyroSensor.get("Gyro");
        nullVal = gyroSensor.getRotation();
    }

    public void loop(){
        rawVal = gyroSensor.getRotation();
        angle = rawVal - nullVal;
        telemetry.addData("TAG", "angle: " + angle);
    }

    public void stop(){

    }

}
