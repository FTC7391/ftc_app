package org.firstinspires.ftc.team7391;


import android.util.Log;

import com.qualcomm.robotcore.hardware.*;

/**
 * Created by nikashkhanna on 11/14/15.
 */

public class DriveJoystick {

    private static final String TAG = TeleOp7391.class.getSimpleName();
    protected FTC7391PrintWriter dbgWriter = new FTC7391PrintWriter("Tele", "Debug");

    private static double axialPower;
    private static double rotatePower;

    private static Zipline pusher_left;
    private static Zipline pusher_right;
    private static ColorSensor colorRight;
    private static ColorSensor colorLeft;

    private static int nTeleLoop = 0;


    static double positionR  = 0;
    static double positionL  = 1.0;
    static boolean bNewCommand = true;


    public DriveJoystick(HardwareMap hardwareMap) {
        pusher_left = new Zipline(hardwareMap, 1,1, .35, "pusher_left"); //.5?
        pusher_right = new Zipline(hardwareMap, 0,0, .7, "pusher_right"); //.5?
        pusher_left.setRetractedPosition();
        pusher_right.setRetractedPosition();

        colorRight = hardwareMap.colorSensor.get("color_right");
        colorRight.enableLed(false);
        colorLeft = hardwareMap.colorSensor.get("color_left");
        colorLeft.enableLed(false);
    }

    public static void update(Gamepad gamepad1) {

        axialPower = scaleInput(gamepad1.left_stick_y)/2;
        rotatePower = scaleInput(gamepad1.right_stick_x)/2;

		/*
		 * Gamepad 1
		 *
		 * Gamepad 1 controls the motors via the left stick, and it controls the
		 * wrist/claw via the a,b, x, y buttons
		 */

        /*
        if (axialPower > 0) {
            if (rotatePower > 0) {
                DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_MOVE_ARC, axialPower, rotatePower);
            } else if (rotatePower < 0) {
                DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_MOVE_ARC, axialPower, -rotatePower);
            } else {
                DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_MOVE_FORWARD, axialPower, 0);
            }
        } else if (axialPower < 0) {
            if (rotatePower > 0) {
                DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_MOVE_ARC, axialPower, rotatePower);
            } else if (rotatePower < 0) {
                DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_MOVE_ARC, axialPower, -rotatePower);
            } else {
                DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_MOVE_BACKWARD, axialPower, 0);
            }
        } else if (rotatePower > 0) {
            DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_ROTATE_RIGHT, rotatePower, 0);
        } else if (rotatePower < 0) {
            DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_ROTATE_LEFT, -rotatePower, 0);
        } else {
            DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_STOP, 0, 0);
        }
        */

        if (++ nTeleLoop%1000 == 0) {
            //showTelemetry();
            Log.d("FTC7391", "COLOR: " + "Clear(Alpha)" + "" + colorLeft.alpha() + "   " + colorRight.alpha());
            Log.d("FTC7391", "COLOR: " + "Red         " + "" + colorLeft.red() + "   " + colorRight.red());
            Log.d("FTC7391", "COLOR: " + "Green       " + "" + colorLeft.green() + "   " + colorRight.green());
            Log.d("FTC7391", "COLOR: " + "Blue        " + "" + colorLeft.blue() + "   " + colorRight.blue());
        }

		/*
		driveJoystick.update(gamepad1);
		telemetry.addData("Drive", DriveTrainTele.getPosition());
		liftJoystick.update(gamepad2);
		telemetry.addData("Lift", DriveTrainTele.getPosition());
		*/



        if (axialPower > 0) {
            DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_MOVE_FORWARD, axialPower, 0);
        } else if (axialPower < 0) {
            DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_MOVE_BACKWARD, -axialPower, 0);
        } else if (rotatePower > 0) {
            DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_ROTATE_RIGHT, rotatePower, 0);
        } else if (rotatePower < 0) {
            DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_ROTATE_LEFT, -rotatePower, 0);
        } else {
            DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_STOP, 0, 0);
        }

        if (gamepad1.x){
        }

        if (gamepad1.y){
            //there was something to do with Stick here
        }

        if (gamepad1.b){
        }
        if (gamepad1.a){

        }

        if (gamepad1.dpad_right) {
            Log.i("FTC7391", "PUSHER: right: " + "DEPLOYED");
            pusher_right.setDeployedPosition();
            pusher_left.setDrivePosition();
        }
        if (gamepad1.dpad_left) {
            Log.i("FTC7391", "PUSHER: left: " + "DEPLOYED");
            pusher_right.setDrivePosition();
            pusher_left.setDeployedPosition();
        }
        if (gamepad1.dpad_up){
            pusher_right.setDrivePosition();
            pusher_left.setDrivePosition();
        }


    /* ---   To test  pusher positions ----
        if (gamepad1.dpad_down) {
            bNewCommand = true;
        }

        if (gamepad1.dpad_right) {
            if (bNewCommand) {
                positionR = positionR + .05;
                pusher_right.setPosition(positionR);
                bNewCommand = false;
                Log.i("FTC7391", "PUSHER: right: " + positionR);
            }

            //pusher_right.setDeployedPosition();
            //pusher_left.setDrivePosition();
        }
        if (gamepad1.dpad_left) {
            if (bNewCommand) {
                positionL = positionL - .05;
                pusher_left.setPosition(positionL);
                bNewCommand = false;
                Log.i("FTC7391", "PUSHER: left: " + positionL);
            }
            //pusher_right.setDrivePosition();
            //pusher_left.setDeployedPosition();
        }

     */


    }

    private static double scaleInput(double dVal) {
        double[] scaleArray = {0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00};

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);
        if (index < 0) {
            index = -index;
        } else if (index > 16) {
            index = 16;
        }

        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        return -dScale;
    }
}