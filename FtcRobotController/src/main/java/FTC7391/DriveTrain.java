package FTC7391;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Dylan on 7/19/2015.
 */
public class DriveTrain {

    protected static DcMotor motorFrontRight;
    protected static DcMotor motorFrontLeft;
    protected static DcMotor motorBackRight;
    protected static DcMotor motorBackLeft;

    protected static boolean initialized = false;

    protected static final double AXLE_LENGTH = 15;
    protected static final double WHEEL_DIAMETER = 4.0;
    protected static final int TICKS_PER_REVOLUTION = 1120;
    protected static final double GEAR_RATIO = .5;
    protected static final int DEGREES_PER_REVOLUTION = 360;
    protected static final double TICKS_PER_INCH = .94 * (TICKS_PER_REVOLUTION / (Math.PI * WHEEL_DIAMETER * GEAR_RATIO));
    protected static final double TICKS_PER_DEGREE = ((TICKS_PER_INCH * Math.PI * AXLE_LENGTH) / DEGREES_PER_REVOLUTION);

    //init
    public static void init (HardwareMap hardwareMap) {
        if (initialized) return;
        initialized = true;

        motorFrontRight = hardwareMap.dcMotor.get("motor_front_right");
        motorFrontLeft = hardwareMap.dcMotor.get("motor_front_left");
        motorBackRight = hardwareMap.dcMotor.get("motor_back_right");
        motorBackLeft = hardwareMap.dcMotor.get("motor_back_left");

        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
        motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);

    }

    protected static void resetEncoders(){
        motorFrontRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorFrontLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorBackRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorBackLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);
    }

    protected static void runUsingEncoders(){
        resetEncoders();
        motorFrontRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorFrontLeft.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorBackRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorBackLeft.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    }

    protected static void runWithoutEncoders(){
        motorFrontRight.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        motorFrontLeft.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        motorBackRight.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        motorBackLeft.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
    }

    protected static void setPowerOfMotors(double frontRightPower, double frontLeftPower, double backRightPower, double backLeftPower) {
        motorFrontRight.setPower(frontRightPower);
        motorFrontLeft.setPower(frontLeftPower);
        motorBackRight.setPower(backRightPower);
        motorBackLeft.setPower(backLeftPower);
    }


    public enum TestModes {
        MODE_MOVE_FRONT_RIGHT,
        MODE_MOVE_FRONT_LEFT,
        MODE_MOVE_BACK_RIGHT,
        MODE_MOVE_BACK_LEFT,
        MODE_STOP,
    }

    public static void setTestMode(TestModes mode, double power) {
        switch (mode) {
            case MODE_MOVE_FRONT_RIGHT:
                setPowerOfMotors(power, 0, 0, 0);
                break;
            case MODE_MOVE_FRONT_LEFT:
                setPowerOfMotors(0, power, 0, 0);
                break;
            case MODE_MOVE_BACK_RIGHT:
                setPowerOfMotors(0, 0, power, 0);
                break;
            case MODE_MOVE_BACK_LEFT:
                setPowerOfMotors(0, 0, 0, power);
                break;
            case MODE_STOP:
                setPowerOfMotors(0.0,0.0,0.0,0.0);
                break;
        }
    }

}


// Test Forward/Backward Movement

// Test Lateral Right/Left Movement

// Test Rotate Degrees (Left and Right)

