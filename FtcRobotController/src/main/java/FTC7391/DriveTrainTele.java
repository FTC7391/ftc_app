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
        MODE_MOVE_DIAGONAL_RIGHT,
        MODE_MOVE_DIAGONAL_22,
        MODE_MOVE_DIAGONAL_45,
        MODE_MOVE_DIAGONAL_67,
        MODE_MOVE_DIAGONAL_FORWARD,
        MODE_ROTATE_RIGHT,
        MODE_ROTATE_LEFT,
        MODE_STOP,
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
            case MODE_MOVE_DIAGONAL_RIGHT:
                moveDiagonal(0); break;
            case MODE_MOVE_DIAGONAL_22:
                moveDiagonal(22.5); break;
            case MODE_MOVE_DIAGONAL_45:
                moveDiagonal(45); break;
            case MODE_MOVE_DIAGONAL_67:
                moveDiagonal(67.5); break;
            case MODE_MOVE_DIAGONAL_FORWARD:
                moveDiagonal(90); break;
            case MODE_ROTATE_RIGHT:
                rotate(-1 * power);
                break;
            case MODE_ROTATE_LEFT:
                rotate(1 * power);    //negative power = counter clockwise
                break;
            case MODE_STOP:
                setPowerOfMotors(0.0,0.0,0.0,0.0);
                break;
        }
    }

    public static void moveAxial(double power) {
        setPowerOfMotors(power,power,power,power);
    }
    public static void moveLateral(double power) {
        setPowerOfMotors(power, -power, -power, power);
    }

    //angle is angle counterclockwise from right
    public static void moveDiagonal(double angle){
        double frontLeft = (Math.cos(angle) + Math.sin(angle)) / (Math.sqrt(2));
        double frontRight = (Math.sin(angle) - Math.cos(angle)) / (Math.sqrt(2));
        setPowerOfMotors(frontRight, frontLeft, frontLeft, frontRight);
    }

    public static void rotate(double power) {
        //Positive power, rotate to the left, frontRight & backRight postive
        setPowerOfMotors(power, -power, power, -power);
    }

}


// Test Forward/Backward Movement

// Test Lateral Right/Left Movement

// Test Rotate Degrees (Left and Right)

