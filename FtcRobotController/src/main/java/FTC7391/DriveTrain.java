package FTC7391;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Dylan on 7/19/2015.
 */
public class DriveTrain {

    private static DcMotor motorFrontRight;
    private static DcMotor motorFrontLeft;
    private static DcMotor motorBackRight;
    private static DcMotor motorBackLeft;

    private static int frontRightTargetTick = 0;
    private static int frontLeftTargetTick = 0;
    private static int backRightTargetTick = 0;
    private static int backLeftTargetTick = 0;

    private static final double AXLE_LENGTH = 14.5;
    private static final double WHEEL_DIAMETER = 4.0;
    private static final int TICKS_PER_REVOLUTION = 1440;
    private static final int DEGREES_PER_REVOLUTION = 360;

    private static final int TICKS_PER_INCH = (int) (TICKS_PER_REVOLUTION / (Math.PI * WHEEL_DIAMETER));

    //Constructor
    public DriveTrain(HardwareMap hardwareMap) {
        motorFrontRight = hardwareMap.dcMotor.get("motor_2");
        motorFrontLeft = hardwareMap.dcMotor.get("motor_1");
        motorBackRight = hardwareMap.dcMotor.get("motor_3");
        motorBackLeft = hardwareMap.dcMotor.get("motor_4");

        //run_using_encoders();

    }

    //Move Inches. Calculates the target tick
    public static void moveInches(int distance, double power) {

        setPowerOfMotors(power, power, power, power);
        frontRightTargetTick = motorFrontRight.getCurrentPosition() + distance * TICKS_PER_INCH;
        frontLeftTargetTick = motorFrontLeft.getCurrentPosition() + distance * TICKS_PER_INCH;
        backRightTargetTick = motorBackRight.getCurrentPosition() + distance * TICKS_PER_INCH;
        backLeftTargetTick = motorBackLeft.getCurrentPosition() + distance * TICKS_PER_INCH;

        setMotorTargetPosition(frontRightTargetTick, frontLeftTargetTick, backRightTargetTick, backLeftTargetTick);
    }

    public static void moveLaterallyInches(boolean right, int distance, double power) {
        if (right) {
            setPowerOfMotors(power, -power, -power, power);
        } else  {  //if (!right)
            setPowerOfMotors(-power, power, power, -power);
        }

        frontRightTargetTick = motorFrontRight.getCurrentPosition() + distance * TICKS_PER_INCH;
        frontLeftTargetTick = motorFrontLeft.getCurrentPosition() + distance * TICKS_PER_INCH;
        backRightTargetTick = motorBackRight.getCurrentPosition() + distance * TICKS_PER_INCH;
        backLeftTargetTick = motorBackLeft.getCurrentPosition() + distance * TICKS_PER_INCH;

        setMotorTargetPosition(frontRightTargetTick, frontLeftTargetTick, backRightTargetTick, backLeftTargetTick);
    }

    public boolean isDone() {
//        if(getCurrentPosition() >= targetMotorPosition)
        return true;
    }

    public static void rotateDegrees(double degrees, double power) {
        if (degrees > 0) {
            setPowerOfMotors(power, power, -power, -power);
        } else if (degrees < 0) {
            setPowerOfMotors(-power, -power, power, power);
        } else {
            setPowerOfMotors(0.0, 0.0, 0.0, 0.0);
        }

        //targetTick = currentTick + (int)(degrees*(TOTAL_TICKS/(TOTAL_DEGREES)));
        //setMotorTargetPosition(targetTick, targetTick, targetTick, targetTick);
    }


    private static void setPowerOfMotors(double frontRightPower, double frontLeftPower, double backRightPower, double backLeftPower) {
        motorFrontRight.setPower(frontRightPower);
        motorFrontLeft.setPower(frontLeftPower);
        motorBackRight.setPower(backRightPower);
        motorBackLeft.setPower(backLeftPower);
    }

    private static void setMotorTargetPosition(int frontRightTick, int frontLeftTick, int backRightTick, int backLeftTick) {
        motorFrontRight.setTargetPosition(frontRightTick);
        motorFrontLeft.setTargetPosition(frontLeftTick);
        motorBackRight.setTargetPosition(backRightTick);
        motorBackLeft.setTargetPosition(backLeftTick);
    }

    enum TestModes {
        MODE_MOVE_FORWARD,
        MODE_MOVE_BACKWARD,
        MODE_MOVE_RIGHT,
        MODE_MOVE_LEFT,
        MODE_ROTATE_RIGHT,
        MODE_ROTATE_LEFT,
    }

    public void setTestMode(TestModes mode, double power) {
        switch (mode) {
            case MODE_MOVE_FORWARD:
                moveAxially(1 * power);    //negative power = backwards
                break;
            case MODE_MOVE_BACKWARD:
                moveAxially(-1 * power);    //negative power = backwards
                break;
            case MODE_MOVE_RIGHT:
                moveLaterally(1 * power);    //negative power = left
                break;
            case MODE_MOVE_LEFT:
                moveLaterally(-1 * power);    //negative power = left
                break;
            case MODE_ROTATE_RIGHT:
                rotate(1 * power);    //negative power = backwards
                break;
            case MODE_ROTATE_LEFT:
                rotate(-1 * power);    //negative power = backwards
                break;
        }
    }

    private void moveAxially(double power) {
        setPowerOfMotors(power,power,power,power);
    }
    private void moveLaterally(double power) {
        setPowerOfMotors(power, -power, -power, power);
    }
    private void rotate(double power) {
        setPowerOfMotors(power, power, -power, -power);
    }
}


// Test Forward/Backward Movement

// Test Lateral Right/Left Movement

// Test Rotate Degrees (Left and Right)

