package org.firstinspires.ftc.team7391;


import com.qualcomm.robotcore.hardware.*;

/**
 * Created by nikashkhanna on 11/14/15.
 */

public class DriveJoystick {

    private static final String TAG = TeleOp7391.class.getSimpleName();
    protected FTC7391PrintWriter dbgWriter = new FTC7391PrintWriter("Tele", "Debug");

    private static double axialPower;
    private static double rotatePower;

    private static Zipline ziplineBlue;
    private static Zipline ziplineRed;
    private Claw claw;

    private DriveJoystick driveJoystick;
    private LiftJoystick liftJoystick;

    private static int nTeleLoop = 0;

    public DriveJoystick(HardwareMap hardwareMap) {
        ziplineBlue = new Zipline(hardwareMap, 1,1, .5, "pusher_left");
        ziplineRed = new Zipline(hardwareMap, 0,0, .5, "pusher_right");
        ziplineBlue.setRetractedPosition();
        ziplineRed.setRetractedPosition();
    }

    public static void update(Gamepad gamepad1) {

        axialPower = scaleInput(gamepad1.left_stick_y);
        rotatePower = scaleInput(gamepad1.right_stick_x);

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

        if (++ nTeleLoop == 10) {
            nTeleLoop = 0;
            //showTelemetry();
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
            DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_ROTATE_RIGHT, -rotatePower, 0);
        } else if (rotatePower < 0) {
            DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_ROTATE_LEFT, rotatePower, 0);
        } else {
            DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_STOP, 0, 0);
        }

        if (gamepad1.x){
            ziplineRed.setDrivePosition();
            ziplineBlue.setDrivePosition();
            //ziplineBlue.setDeployedPosition();
            //Lift.setTestMode(Lift.TestModes.MODE_MOVE_WRIST_TO_POSITION, -.25);
        }

        if (gamepad1.y){
            //there was something to do with Stick here
        }

        if (gamepad1.b){
            ziplineRed.setDeployedPosition();
            ziplineBlue.setDrivePosition();
        }
        if (gamepad1.a){
            ziplineRed.setDrivePosition();
            ziplineBlue.setDeployedPosition();
        }
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

        return dScale;
    }
}