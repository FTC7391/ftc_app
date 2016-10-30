/* Copyright (c) 2014 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package org.firstinspires.ftc.team7391;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * TeleOp Mode
 * <p>
 * Enables control of the robot via the gamepad
 */
@TeleOp(name = "LiftTest" + "", group = "Practice")
//@Disabled
public class LiftTest extends OpMode {

    private static final String TAG = Lift.class.getSimpleName();
    private double powerLift = 0;
    private double powerDriveTrain = 0;
    /**
     * Constructor
     */
    public LiftTest() {

    }

    /*
     * Code to run when the op mode is initialized goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#init()
     */
    @Override
    public void init() {
        Lift.init(hardwareMap);
        Lift.runUsingEncoders();
        DriveTrain.init(hardwareMap);
        //DriveTrainAuto.moveInches(15, .5);
		/*
		 * Use the hardwareMap to get the dc motors and servos by name. Note
		 * that the names of the devices must match the names used when you
		 * configured your robot and created the configuration file.
		 */
		
		/*
		 * For the demo Tetrix K9 bot we assume the following,
		 *   There are two motors "motor_1" and "motor_2"
		 *   "motor_1" is on the right side of the bot.
		 *   "motor_2" is on the left side of the bot and reversed.
		 *   
		 * We also assume that there are two servos "servo_1" and "servo_6"
		 *    "servo_1" controls the arm joint of the manipulator.
		 *    "servo_6" controls the claw joint of the manipulator.
		 */

        // assign the starting position of the wrist and claw

    }

    /*
     * This method will be called repeatedly in a loop
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
     */
    @Override
    public void loop() {

        telemetry.addData(TAG, "OpMode Started");

		/*
		 * Gamepad 1
		 * 
		 * Gamepad 1 controls the motors via the left stick, and it controls the
		 * wrist/claw via the a,b, x, y buttons
		 */

        // throttle: left_stick_y ranges from -1 to 1, where -1 is full up, and
        // 1 is full down
        // direction: left_stick_x ranges from -1 to 1, where -1 is full left
        // and 1 is full right
		/*
		float throttle = -gamepad1.left_stick_y;
		float direction = gamepad1.left_stick_x;
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

        powerLift = scaleInput(gamepad1.left_stick_y);
        powerDriveTrain = scaleInput(gamepad1.right_stick_y);

        if (gamepad1.y) {
            //DriveTrain.testRotateDegrees(positiveNumber);
            Lift.setTestMode(Lift.TestModes.MODE_MOVE_HIGH, powerLift);
            if (gamepad1.dpad_left) {
                Lift.liftHigh.setPower(0);
                Lift.liftLow.setPower(0);
                Lift.liftShoulder.setPower(0);
                Lift.liftWrist.setPower(0);
            }
        }
        else{
            Lift.liftHigh.setPower(0);
        }


        if (gamepad1.b) {
            //DriveTrain.testRotateDegrees(negativeNumber);
            Lift.setTestMode(Lift.TestModes.MODE_MOVE_LOW, powerLift);
            if (gamepad1.dpad_left) {
                Lift.liftLow.setPower(0);
                Lift.liftHigh.setPower(0);
                Lift.liftShoulder.setPower(0);
                Lift.liftWrist.setPower(0);
            }
        }
        else{
            Lift.liftLow.setPower(0);
        }


        if (gamepad1.x) {
            Lift.setTestMode(Lift.TestModes.MODE_MOVE_ANGLE, powerLift/3);
            if (gamepad1.dpad_left) {
                Lift.liftShoulder.setPower(0);
                Lift.liftHigh.setPower(0);
                Lift.liftLow.setPower(0);
                Lift.liftWrist.setPower(0);
            }
        }
        else{
            Lift.liftShoulder.setPower(0);
        }


        if (gamepad1.a) {
            Lift.setTestMode(Lift.TestModes.MODE_MOVE_HOOK, powerLift/3);
            if (gamepad1.dpad_left) {
                Lift.liftWrist.setPower(0);
                Lift.liftHigh.setPower(0);
                Lift.liftLow.setPower(0);
                Lift.liftShoulder.setPower(0);
            }
        }
        else{
            Lift.liftWrist.setPower(0);
        }

        if(gamepad1.dpad_up){
            Lift.setTestMode(Lift.TestModes.MODE_GO_TO_INIT, powerLift);
        }


//        if(gamepad1.dpad_up) {
//            Lift.setTestMode(Lift.TestModes.MODE_MOVE_BOTH, powerLift);
//        }
//
//
//
//        if(gamepad1.dpad_down){
//
//        }
//       // if(powerDriveTrain != 0) {
//            DriveTrainTele.moveAxial(powerDriveTrain);
//       // }

        telemetry.addData("High", "original: " + Lift.originalTicksHigh + "|| end: " + Lift.getTicksLiftHigh());
        telemetry.addData("Low", "original: " + Lift.originalTicksLow + "|| end: " + Lift.getTicksLiftLow());
        telemetry.addData("Shoulder", "original: " + Lift.originalTicksShoulder + "|| end: " + -Lift.getTicksliftShoulder());
        telemetry.addData("Wrist", "original: " + Lift.originalTicksWrist + "|| end: " + Lift.getTicksliftWrist());

        // clip the position values so that they never exceed their allowed range.
        // armPosition = Range.clip(armPosition, ARM_MIN_RANGE, ARM_MAX_RANGE);
        //clawPosition = Range.clip(clawPosition, CLAW_MIN_RANGE, CLAW_MAX_RANGE);

		/*
		 * Send telemetry data back to driver station. Note that if we are using
		 * a legacy NXT-compatible motor controller, then the getPower() method
		 * will return a null value. The legacy NXT-compatible motor controllers
		 * are currently write only.
		 */

    }

    /*
     * Code to run when the op mode is first disabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
     */
    @Override
    public void stop() {

    }

    /*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        //double[] scaleArray = { 0.0, 0.07, 0.15, 0.20, 0.26,
        //        0.32, 0.38, 0.44, 0.50, 0.56, 0.62, 0.68, 0.74, 0.80, 0.86, 0.95, 1.00 };


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
