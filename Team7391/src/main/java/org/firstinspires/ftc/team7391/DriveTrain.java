package org.firstinspires.ftc.team7391;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Dylan on 7/19/2015.
 */
public class DriveTrain {

    protected static DcMotor motorRight;
    protected static DcMotor motorLeft;

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


        motorRight = hardwareMap.dcMotor.get("motor_right");
        motorLeft = hardwareMap.dcMotor.get("motor_left");

//        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
//        motorLeft.setDirection(DcMotor.Direction.REVERSE);

        resetEncoders();

    }

    protected static void resetEncoders(){
        motorRight.setMode(DcMotor.RunMode.RESET_ENCODERS);
        motorLeft.setMode(DcMotor.RunMode.RESET_ENCODERS);
    }

    protected static void runToPosition(){
        motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    protected static void runUsingEncoders(){
        motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
    }

    protected static void runWithoutEncoders(){
        motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
    }

    protected static void setPowerOfMotors(double rightPower, double leftPower) {
        motorRight.setPower(rightPower);
        motorLeft.setPower(leftPower);
    }


    public enum TestModes {
        MODE_MOVE_RIGHT,
        MODE_MOVE_LEFT,
        MODE_STOP,
    }

    public static void setTestMode(TestModes mode, double power) {
        switch (mode) {
            case MODE_MOVE_RIGHT:
                setPowerOfMotors(power, 0);
                break;
            case MODE_MOVE_LEFT:
                setPowerOfMotors(0, power);
                break;
            case MODE_STOP:
                setPowerOfMotors(0.0,0.0);
                break;
        }
    }

}


// Test Forward/Backward Movement

// Test Lateral Right/Left Movement

// Test Rotate Degrees (Left and Right)

