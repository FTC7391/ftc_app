package org.firstinspires.ftc.team7391;

/**
 * Created by ArjunVerma on 8/29/15.
 */

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

//
//public class TestOpticalDistanceSensorOp extends OpMode
//{
//    OpticalDistanceSensor opticalSensor;
//    private static final String TAG = AutoOp.class.getSimpleName();
//
//    @Override
//    public void init()
//    {
//        opticalSensor = hardwareMap.opticalDistanceSensor.get("optical");
//    }
//
//    @Override
//    public void loop()
//    {
//        double lightDetected;
//        int lightDetectedRaw;
//
//
//        lightDetected = opticalSensor.getLightDetected();
//        lightDetectedRaw = opticalSensor.getLightDetectedRaw();
//
//        int calculatedInches = calculateInches(lightDetectedRaw);
//
//        telemetry.addData("TAG", "lightDetected: " + lightDetected + " lightDetectedRaw: " + lightDetectedRaw + " inchesFromObject: " + calculatedInches);
//
//    }
//
//    @Override
//    public void stop()
//    {
//        telemetry.addData("TAG", "Test Stopped");
//    }
//
//    public int calculateInches(int brightness){
//        //assuming we look at red
//        if(brightness>=120 && brightness<=180) return 1;
//        else if(brightness>=60) return 2;
//        else if(brightness>=50) return 3;
//        else if(brightness>=40) return 4;
//        else if(brightness>=30) return 5;
//        else return 0;
//
//    }
//
//}
