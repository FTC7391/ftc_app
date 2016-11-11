package org.firstinspires.ftc.team7391;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * GyroOp Mode  (For testing orientiation)
 *
 */

public class GyroOp extends OpMode{

    private static final String TAG = GyroOp.class.getSimpleName();
    private FTC7391PrintWriter dataWriter1;
    private Gyro gyro;


    public void init () {
        dataWriter1 = new FTC7391PrintWriter("Gyro" , "GyroOp");
        dataWriter1.print("INIT");
        gyro = new Gyro(hardwareMap.appContext);
        gyro.start();
    }

    public void update () {

    }

    public void loop () {
        dataWriter1.print("LOOP ");
    }

    public void stop() {
        gyro.stop();
    }




}

