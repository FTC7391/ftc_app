package FTC7391;


import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Created by nikashkhanna on 11/14/15.
 */

public class DriveJoystick {
    private static double axialPower;
    private static double rotatePower;

    public void update(Gamepad gamepad) {

        axialPower = scaleInput(gamepad.left_stick_y);
        rotatePower = scaleInput(gamepad.right_stick_x);

		/*
		 * Gamepad 1
		 *
		 * Gamepad 1 controls the motors via the left stick, and it controls the
		 * wrist/claw via the a,b, x, y buttons
		 */

        if (axialPower > 0) {
            if (rotatePower > 0) {
                DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_MOVE_ARC, axialPower, rotatePower);
            } else if (rotatePower < 0) {
                DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_MOVE_ARC, axialPower, -rotatePower);
            } else {
                DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_MOVE_FORWARD, axialPower, 0);
            }
        } else if (axialPower < 0) {
            DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_MOVE_BACKWARD, -axialPower, 0);
        } else if (rotatePower > 0) {
            DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_ROTATE_RIGHT, rotatePower, 0);
        } else if (rotatePower < 0) {
            DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_ROTATE_LEFT, -rotatePower, 0);
        } else {
            DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_STOP, 0, 0);
        }
    }

    double scaleInput(double dVal) {
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