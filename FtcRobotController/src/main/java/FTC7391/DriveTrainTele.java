package FTC7391;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Allana on 10/3/2015.
 */
public class DriveTrainTele extends DriveTrain{

    enum TestModes {
        MODE_MOVE_FORWARD,
        MODE_MOVE_BACKWARD,
        MODE_MOVE_RIGHT,
        MODE_MOVE_LEFT,
        MODE_ROTATE_RIGHT,
        MODE_ROTATE_LEFT,
    }

    public static void setTestMode(TestModes mode, double power) {
        switch (mode) {
            case MODE_MOVE_FORWARD:
                moveAxial(1 * power);
                break;
            case MODE_MOVE_BACKWARD:
                moveAxial(-1 * power);    //negative power = backwards
                break;
            case MODE_MOVE_RIGHT:
                moveLateral(1 * power);
                break;
            case MODE_MOVE_LEFT:
                moveLateral(-1 * power);    //negative power = left
                break;
            case MODE_ROTATE_RIGHT:
                rotate(1 * power);
                break;
            case MODE_ROTATE_LEFT:
                rotate(-1 * power);    //negative power = counter clockwise
                break;
        }
    }

    public static void moveAxial(double power) {
        setPowerOfMotors(power,power,power,power);
    }
    public static void moveLateral(double power) {
        setPowerOfMotors(power, -power, -power, power);
    }
    public static void rotate(double power) {
        setPowerOfMotors(power, power, -power, -power);
    }

}


// Test Forward/Backward Movement

// Test Lateral Right/Left Movement

// Test Rotate Degrees (Left and Right)
