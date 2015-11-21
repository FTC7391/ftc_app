package FTC7391;

import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Created by nikashkhanna on 11/14/15.
 */
public class LiftJoystick {
    private static double liftPower;

    public void update(Gamepad gamepad) {

        //Use the gamepad to move the lift.
        // Here add, the if statements
        // the scale input method is used to get the power.
        liftPower = scaleInput(gamepad.left_stick_y);   //and example of how you would get the power.
        DriveTrainTele.setTestMode(DriveTrainTele.TestModes.MODE_MOVE_BACKWARD, -liftPower, 0);
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
