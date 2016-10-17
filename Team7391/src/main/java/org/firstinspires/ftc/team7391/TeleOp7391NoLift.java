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

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * TeleOp Mode
 * <p>
 * Enables control of the robot via the gamepad
 */
public class TeleOp7391NoLift extends OpMode {


	private static final String TAG = TeleOp7391NoLift.class.getSimpleName();
	private static double axialPower;
	private static double rotatePower;
	private static double powerLift;
	private Stick stick;
	private Zipline ziplineBlue;
	private Zipline ziplineRed;

	/*
	 * Note: the configuration of the servos is such that
	 * as the arm servo approaches 0, the arm position moves up (away from the floor).
	 * Also, as the claw servo approaches 0, the claw opens up (drops the game element).
	 */
	// TETRIX VALUES.
	final static double ARM_MIN_RANGE  = 0.20;
	final static double ARM_MAX_RANGE  = 0.90;
	final static double CLAW_MIN_RANGE  = 0.20;
	final static double CLAW_MAX_RANGE  = 0.7;

	private DriveJoystick driveJoystick;
	private LiftJoystick liftJoystick;

	// position of the arm servo.
	double armPosition;

	// amount to change the arm servo position.
	double armDelta = 0.1;

	// position of the claw servo
	double clawPosition;

	// amount to change the claw servo position by
	double clawDelta = 0.1;

	/**
	 * Constructor
	 */
	public TeleOp7391NoLift() {

	}

	/*
	 * Code to run when the op mode is initialized goes here
	 * 
	 * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#init()
	 */
	@Override
	public void init() {
		DriveTrainTele.init(hardwareMap);
		//Lift.init(hardwareMap);
		driveJoystick = new DriveJoystick();
		liftJoystick = new LiftJoystick();
		stick = new Stick(hardwareMap);
		stick.setDrivePosition();
		ziplineBlue = new Zipline(hardwareMap, 1,1,.5, "zipline_blue");
		ziplineRed = new Zipline(hardwareMap, 0,0,.5, "zipline_red");
		ziplineBlue.setDrivePosition();
		ziplineRed.setDrivePosition();
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
		driveJoystick.update(gamepad1);
		telemetry.addData("Drive", DriveTrainTele.getPosition());
		liftJoystick.update(gamepad2);
		telemetry.addData("Lift", DriveTrainTele.getPosition());
		*/

		axialPower = DriveTrainTele.scaleInput(gamepad1.left_stick_y);
		rotatePower = DriveTrainTele.scaleInput(gamepad1.right_stick_x);
		powerLift = -DriveTrainTele.scaleInput(gamepad2.left_stick_y);


		/*
		 * Gamepad 1
		 *
		 * Gamepad 1 controls the motors via the left stick, and it controls the
		 * wrist/claw via the a,b, x, y buttons
		 */



		if (gamepad1.y){
			stick.setDeployedPosition();
		}

		if (gamepad1.x){
			ziplineBlue.setDeployedPosition();
		}
		if (gamepad1.b){
			ziplineRed.setDeployedPosition();
		}

		/*
		if (gamepad2.y) {
			//DriveTrain.testRotateDegrees(positiveNumber);
			Lift.setTestMode(Lift.TestModes.MODE_MOVE_HIGH, -powerLift);
			stick.setDrivePosition();
			ziplineRed.setDrivePosition();
			ziplineBlue.setDrivePosition();
		}
		else{
			Lift.liftHigh.setPower(0);
		}


		if (gamepad2.b) {
			//DriveTrain.testRotateDegrees(negativeNumber);
			Lift.setTestMode(Lift.TestModes.MODE_MOVE_LOW, powerLift);
			stick.setDrivePosition();
			ziplineRed.setDrivePosition();
			ziplineBlue.setDrivePosition();
		}
		else{
			Lift.liftLow.setPower(0);
		}


		if (gamepad2.a) {
			Lift.setTestMode(Lift.TestModes.MODE_MOVE_ANGLE, powerLift);
			stick.setDrivePosition();
			ziplineRed.setDrivePosition();
			ziplineBlue.setDrivePosition();
		}
		else{
			Lift.liftShoulder.setPower(0);
		}


		if (gamepad2.x) {
			Lift.setTestMode(Lift.TestModes.MODE_MOVE_HOOK, powerLift/6);
			stick.setDrivePosition();
			ziplineRed.setDrivePosition();
			ziplineBlue.setDrivePosition();
		}
		else{
			Lift.leftWrist.setPower(0);
		}


		if(gamepad2.dpad_up) {
			Lift.setTestMode(Lift.TestModes.MODE_MOVE_BOTH, powerLift);
		} */







		if (axialPower > 0) {
			DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_MOVE_FORWARD, axialPower, 0);
		} else if (axialPower < 0) {
			DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_MOVE_BACKWARD, -axialPower, 0);
		} else if (rotatePower > 0) {
			DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_ROTATE_RIGHT, -rotatePower, 0);
		} else if (rotatePower < 0) {
			DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_ROTATE_LEFT, rotatePower, 0);
		} else {
			DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_STOP, 0, 0);
		}
	}

	/*
	 * Code to run when the op mode is first disabled goes here
	 *
	 * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
	 */
	@Override
	public void stop() {

	}
}
