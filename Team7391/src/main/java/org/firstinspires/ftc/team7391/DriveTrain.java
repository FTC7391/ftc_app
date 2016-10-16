package org.firstinspires.ftc.team7391;


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
    protected static final double WHEEL_DIAMETER = 2.42;
    protected static final int TICKS_PER_REVOLUTION = 1120;
    protected static final double GEAR_RATIO = 1;
    protected static final int DEGREES_PER_REVOLUTION = 360;
    protected static final double TICKS_PER_INCH = .87 * (TICKS_PER_REVOLUTION / (Math.PI * WHEEL_DIAMETER * GEAR_RATIO));
    protected static final double TICKS_PER_DEGREE = .65 * ((TICKS_PER_INCH * Math.PI * AXLE_LENGTH) / DEGREES_PER_REVOLUTION);

    //init
    public static void init (HardwareMap hardwareMap) {
        if (initialized) return;
        initialized = true;


        motorFrontRight = hardwareMap.dcMotor.get("motor_front_right");
        motorFrontLeft = hardwareMap.dcMotor.get("motor_front_left");
        motorBackRight = hardwareMap.dcMotor.get("motor_back_right");
        motorBackLeft = hardwareMap.dcMotor.get("motor_back_left");

//        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
//        motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        motorBackRight.setDirection(DcMotor.Direction.REVERSE);
        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);

        resetEncoders();
    }

    protected static void resetEncoders(){
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    protected static void runToPosition(){
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    protected static void runUsingEncoders(){
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    protected static void runWithoutEncoders(){
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
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

