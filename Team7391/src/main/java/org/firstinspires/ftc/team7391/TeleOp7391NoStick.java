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

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * TeleOp Mode
 * <p>
 * Enables control of the robot via the gamepad
 */
public class TeleOp7391NoStick extends OpMode {

	private static final String TAG = TeleOp7391NoStick.class.getSimpleName();
	protected FTC7391PrintWriter dbgWriter = new FTC7391PrintWriter("Tele", "Debug");

	private static double axialPower;
	private static double rotatePower;
	private static double liftPower;
	private static double liftLowPower;

	//private Stick stick;
	//private Zipline ziplineBlue;
	//private Zipline ziplineRed;

	private DriveJoystick driveJoystick;
	private LiftJoystick liftJoystick;

	private static int nTeleLoop = 0;


	/**
	 * Constructor
	 */
	public TeleOp7391NoStick() {

	}

	/*
	 * Code to run when the op mode is initialized goes here
	 * 
	 * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#init()
	 */
	@Override
	public void init() {
		DriveTrainTele.init(hardwareMap);
		Lift.init(hardwareMap);
		Lift.runUsingEncoders();
		driveJoystick = new DriveJoystick();
		liftJoystick = new LiftJoystick();
		//stick = new Stick(hardwareMap);
		//stick.setRetractedPosition();
//		ziplineBlue = new Zipline(hardwareMap, 1,1, .5, "zipline_blue");
//		ziplineRed = new Zipline(hardwareMap, 0,0, .5, "zipline_red");
//		ziplineBlue.setDrivePosition();
//		ziplineRed.setDrivePosition();
	}

	/*
	 * This method will be called repeatedly in a loop
	 * 
	 * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
	 */
	@Override
	public void loop() {

		if (++ nTeleLoop == 10) {
			nTeleLoop = 0;
			showTelemetry();
		}


		/*
		driveJoystick.update(gamepad1);
		telemetry.addData("Drive", DriveTrainTele.getPosition());
		liftJoystick.update(gamepad2);
		telemetry.addData("Lift", DriveTrainTele.getPosition());
		*/

		axialPower = DriveTrainTele.scaleInput(gamepad1.left_stick_y);
		rotatePower = DriveTrainTele.scaleInput(gamepad1.right_stick_x);
		liftPower = DriveTrainTele.scaleInput(gamepad2.left_stick_y);
		liftLowPower = DriveTrainTele.scaleInput(gamepad2.right_stick_y);

		//Use right stick for low power for lift
		if (liftPower == 0 && liftLowPower != 0)
			liftPower = liftLowPower/8;


		/************ GAMEPAD 1 ***************************************/
		/*
		 * Gamepad 1
		 *
		 * Gamepad 1 controls the motors via the left stick, and it controls
		 * the stick and ziplines
		 */


		if (gamepad1.x){
//			stick.setDrivePosition();
//			ziplineRed.setDrivePosition();
//			ziplineBlue.setDrivePosition();
			//ziplineBlue.setDeployedPosition();
			//Lift.setTestMode(Lift.TestModes.MODE_MOVE_HOOK_TO_POSITION, -.25);
		}

		if (gamepad1.y){
			//stick.setDeployedPosition();
		}

		if (gamepad1.b){
//			stick.setRetractedPosition();
//			ziplineRed.setDeployedPosition();
//			ziplineBlue.setDrivePosition();
		}
		if (gamepad1.a){
//			ziplineRed.setDrivePosition();
//			ziplineBlue.setDeployedPosition();
		}



		if (axialPower > 0) {
			DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_MOVE_FORWARD, axialPower, 0);
			//stick.setDrivePosition();
		} else if (axialPower < 0) {
			DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_MOVE_BACKWARD, -axialPower, 0);
			//stick.setDrivePosition();
		} else if (rotatePower > 0) {
			DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_ROTATE_RIGHT, -rotatePower, 0);
			//stick.setDrivePosition();
		} else if (rotatePower < 0) {
			DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_ROTATE_LEFT, rotatePower, 0);
			//stick.setDrivePosition();
		} else {
			DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_STOP, 0, 0);
		}


		/************ GAMEPAD 2 ***************************************/
		if (gamepad2.left_bumper) {
			if (gamepad2.y) {
				Lift.setTestMode(Lift.TestModes.MODE_GOTO_DRIVE_POSITION1, 1);
			}
			if (gamepad2.x) {
				Lift.setTestMode(Lift.TestModes.MODE_GOTO_DRIVE_POSITION2, 1);
			}
			if(gamepad2.b) {
				Lift.setTestMode(Lift.TestModes.MODE_GOTO_STRAIGHT_HOOK, 1);
			}

		}
		else {


//			if(gamepad2.dpad_up) {
//				Lift.setTestMode(Lift.TestModes.MODE_MOVE_BOTH, liftPower);
//			}
//

			if (gamepad2.y) {
				//DriveTrain.testRotateDegrees(positiveNumber);
				Lift.setTestMode(Lift.TestModes.MODE_MOVE_HIGH, liftPower);
			}
			else {
				Lift.highRunToPosition();
			}


			if (gamepad2.b) {
				//DriveTrain.testRotateDegrees(negativeNumber);
				Lift.setTestMode(Lift.TestModes.MODE_MOVE_LOW, liftPower);
			}
			else {
				Lift.lowRunToPosition();
			}


			if (gamepad2.a) {
				Lift.setTestMode(Lift.TestModes.MODE_MOVE_ANGLE, liftPower);
			}
			else {
				Lift.shoulderRunToPosition();
			}


			if (gamepad2.x) {
				Lift.setTestMode(Lift.TestModes.MODE_MOVE_HOOK, liftPower);
			}
			else {
				Lift.wristRunToPosition();
			}

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


	private void showTelemetry(){
		showTelemetryPower();
		showTelemetryDrivetrain();
		showTelemetryLift();

	}
	private void showTelemetryPower() {
		telemetry.addData("10  ", String.format("AxialPower  : %.2f", axialPower));
		telemetry.addData("11  ", String.format("RotatePower : %.2f", rotatePower));
		telemetry.addData("12 ", String.format("LiftPower   : %.2f", liftPower));

	}

	private void showTelemetryDrivetrain() {
		telemetry.addData("20 " , String.format("DriveTrain Right %s", DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_RIGHT)));
		telemetry.addData("21 " , String.format("DriveTrain Left  %s", DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_LEFT)));
		dbgWriter.printf("r:%s l:%s \n",
				DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_RIGHT),
				DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_LEFT));

		Log.d("DriveTrain", "Right" + DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_RIGHT));
		Log.d("DriveTrain", "Left " + DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_LEFT));

	}

	private void showTelemetryLift() {
		telemetry.addData("30 " , String.format("High  : original: %d current: %d", Lift.originalTicksHigh, Lift.getTicksLiftHigh()));
		telemetry.addData("31 " , String.format("Low   : original: %d current: %d", Lift.originalTicksLow, Lift.getTicksLiftLow()));
		telemetry.addData("32 " , String.format("Angle : original: %d current: %d", Lift.originalTicksShoulder, Lift.getTicksliftShoulder()));
		telemetry.addData("33 " , String.format("Hook  : original: %d currnet: %d", Lift.originalTicksWrist, Lift.getTicksleftWrist()));
		dbgWriter.printf("High %d %d    Low %d %d    Angle %d %d     Hook %d %d \n",
			Lift.originalTicksHigh, Lift.getTicksLiftHigh(),
			Lift.originalTicksLow, Lift.getTicksLiftLow(),
			Lift.originalTicksShoulder, Lift.getTicksliftShoulder(),
			Lift.originalTicksWrist, Lift.getTicksleftWrist()
		);

		Log.d("Lift", "High  : original:" + Lift.originalTicksHigh + "|| end: " + Lift.getTicksLiftHigh());
		Log.d("Lift", "Low   : original:" + Lift.originalTicksLow + "|| end: " + Lift.getTicksLiftLow());
		Log.d("Lift", "Angle : original:" + Lift.originalTicksShoulder + "|| end: " + Lift.getTicksliftShoulder());
		Log.d("Lift", "Hook  : original:" + Lift.originalTicksWrist + "|| end: " + Lift.getTicksleftWrist());

	}




}
