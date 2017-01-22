package org.firstinspires.ftc.team7391;

import android.util.Log;

import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Created by nikashkhanna on 11/14/15.
 */
public class LiftJoystick {
    private static int nLoop = 0;
    private static double liftPower = 0;
    private static double liftLowPower = 0;

    public static double getLiftPower() { return liftPower;}

    public static void update(Gamepad gamepad2) {
        /*
		 * Gamepad 2
		 *
		 * Gamepad 1 controls the motors via the left stick, and it controls the
		 * wrist/claw via the a,b, x, y buttons
		 */
        //Use the gamepad to move the lift.
        // Here add, the if statements
        // the scale input method is used to get the adjusted  power.
        liftPower =scaleInput(gamepad2.left_stick_y);
        liftLowPower = scaleInput(gamepad2.right_stick_y) / 8;
        if (liftPower == 0 && liftLowPower !=0) {
            liftPower = liftLowPower;
        }

       // Log.i("Lift", "gamepad2.left_stick_y" + gamepad2.left_stick_y + " liftPower"+ liftPower);

         //Choose RUN_TO_POSITON or RUN_USING_ECODER mode
        if (gamepad2.right_bumper) {
            Lift.setTestMode(Lift.TestModes.MODE_STOP, 1); //runToPostion mode
        } else if (gamepad2.left_bumper) {
            Lift.runUsingEncoders();
        }

        if (Lift.isModeRunToPosition()) {

            if (gamepad2.back)
                Lift.stop();

            if (gamepad2.dpad_up) {
                if (gamepad2.y) {
                    Lift.grabPosition();
                }
                if (gamepad2.b) {
                    Lift.raiseBallPosition1();
                }
                if (gamepad2.a) {
                    Lift.raiseBallPosition2();
                }
                if (gamepad2.x) {
                    Lift.dropBall1();
                    //wait???
                    Lift.dropBall2();
                }

            }
            else if (gamepad2.dpad_down) {

                if (gamepad2.y) {
                    Lift.initPosition();
                }
                if (gamepad2.x) {
                    Lift.drivePosition1();
                }
                if (gamepad2.a) {
                    //Lift.grabPosition();
                }
                if (gamepad2.b) {
                    Lift.holdBall();
                }
            }


        }
        else {
            if (gamepad2.y) {
                Lift.setTestMode(Lift.TestModes.MODE_MOVE_TOP, liftPower);
            }
            else {
                Lift.setTestMode(Lift.TestModes.MODE_MOVE_TOP, 0);
            }
            if (gamepad2.b) {
                Lift.setTestMode(Lift.TestModes.MODE_MOVE_HIGH, liftPower);
            }
            else {
                Lift.setTestMode(Lift.TestModes.MODE_MOVE_HIGH, 0);
            }
            if (gamepad2.x) {
                Lift.setTestMode(Lift.TestModes.MODE_MOVE_MID, liftPower);
            }
            else {
                Lift.setTestMode(Lift.TestModes.MODE_MOVE_MID, 0);
            }
            if (gamepad2.a) {
                Lift.setTestMode(Lift.TestModes.MODE_MOVE_LOW, liftPower);
            }
            else {
                Lift.setTestMode(Lift.TestModes.MODE_MOVE_LOW, 0);
            }
            if (gamepad2.dpad_up) {
                Lift.setTestMode(Lift.TestModes.MODE_MOVE_WRIST, liftPower);
            }
            else {
                Lift.setTestMode(Lift.TestModes.MODE_MOVE_WRIST, 0);
            }

        }

    }




    // throttle: left_stick_y ranges from -1 to 1, where -1 is full up, and
    // 1 is full down
    // direction: left_stick_x ranges from -1 to 1, where -1 is full left
    // and 1 is full right
		/*
		float throttle = -gamepad2.left_stick_y;
		float direction = gamepad2.left_stick_x;
		float right = throttle - direction;
		float left = throttle + direction;

		// clip the right/left values so that the values never exceed +/- 1
		right = Range.clip(right, -1, 1);
		left = Range.clip(left, -1, 1);

		// scale the joystick value to make it easier to control
		// the robot more precisely at slower speeds.
		right = (float)scaleInput(right);
		left =  (float)scaleInput(left);

		*/
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

        return dScale;
    }


}
