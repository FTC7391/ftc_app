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

package FTC7391;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;


/**
 * TeleOp Mode
 * <p>
 * Enables control of the robot via the gamepad
 */
public class DriveTrainTest extends OpMode {

    private static final String TAG = DriveTrainTest.class.getSimpleName();
    private static double power;


    /**
     * Constructor
     */
    public DriveTrainTest() {

    }

    /*
     * Code to run when the op mode is initialized goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#init()
     */
    @Override
    public void init() {
        DriveTrainTele.init(hardwareMap);
    }
    /*
     * This method will be called repeatedly in a loop
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
     */
    @Override
    public void loop() {

        telemetry.addData(TAG, "OpMode Started");


        power = DriveTrainTele.scaleInput(gamepad1.left_stick_y);
//		if (gamepad1.left_stick_y > 0)
//		power = .25;
//		else
//		power = -.25;



        if (gamepad1.y) {
            telemetry.addData(TAG, "Y Button Pressed.");
            telemetry.addData(TAG, "MODE_MOVE_FRONT_RIGHT Power:" + String.format("%.2f", power));
            DriveTrain.setTestMode(DriveTrain.TestModes.MODE_MOVE_FRONT_RIGHT, power);
        }

        else if (gamepad1.x) {
            telemetry.addData(TAG, "X Button Pressed.");
            telemetry.addData(TAG, "MODE_MOVE_FRONT_LEFT Power:" + String.format("%.2f", power));
            DriveTrain.setTestMode(DriveTrain.TestModes.MODE_MOVE_FRONT_LEFT, power);
        }

        else if (gamepad1.b) {
            telemetry.addData(TAG, "B Button Pressed.");
            telemetry.addData(TAG, "MODE_MOVE_BACK_RIGHT Power:" + String.format("%.2f", power));
            DriveTrain.setTestMode(DriveTrain.TestModes.MODE_MOVE_BACK_RIGHT, power);
        }

        else if (gamepad1.a) {
            telemetry.addData(TAG, "A Button Pressed.");
            telemetry.addData(TAG, "MODE_MOVE_BACK_LEFT Power:" + String.format("%.2f", power));
            DriveTrain.setTestMode(DriveTrain.TestModes.MODE_MOVE_BACK_LEFT, power);
        }


        else if (gamepad1.dpad_up) {
            telemetry.addData(TAG, "Dpad UP");
            telemetry.addData(TAG, "MODE_MOVE_FORWARD Power:" + String.format("%.2f", power));
            DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_MOVE_FORWARD, power,0);
        }
        else if (gamepad1.dpad_down) {
            telemetry.addData(TAG, "Dpad DOWN");
            telemetry.addData(TAG, "MODE_MOVE_BACKWARD Power:" + String.format("%.2f", power));
            DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_MOVE_BACKWARD, power,0);
        }
        else if (gamepad1.dpad_right) {
            telemetry.addData(TAG, "Dpad DOWN");
            telemetry.addData(TAG, "MODE_MOVE_BACKWARD Power:" + String.format("%.2f", power));
            DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_MOVE_RIGHT, power,0);
        }
        else if (gamepad1.dpad_left) {
            DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_MOVE_LEFT, power,0);
        }

        else if (gamepad1.right_bumper) {
            DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_ROTATE_RIGHT, power,0);
        }


        else if (gamepad1.left_bumper) {
            DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_ROTATE_LEFT, power,0);
        }
        else {
            stop();
        }

//		if (!gamepad1.y && !gamepad1.x && !gamepad1.b && !gamepad1.a) {
//			DriveTrain.setTestMode(DriveTrain.TestModes.MODE_STOP,0.0);
//		}

		/*
		 * Send telemetry data back to driver station. Note that if we are using
		 * a legacy NXT-compatible motor controller, then the getPower() method
		 * will return a null value. The legacy NXT-compatible motor controllers
		 * are currently write only.
		 */
        //telemetry.addData("Text", "*** Robot Data***");

    }

    /*
     * Code to run when the op mode is first disabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
     */
    @Override
    public void stop() {
        DriveTrain.setTestMode(DriveTrain.TestModes.MODE_STOP,0.0);
    }

}
