package org.firstinspires.ftc.team7391;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Dylan on 7/19/2015.
 */
public class DriveTrain {

    protected static DcMotor motorLeft;
    protected static DcMotor motorRight;

    protected static boolean initialized = false;

    protected static final double AXLE_LENGTH = 15.8;
    protected static final double WHEEL_DIAMETER = 2.42;
    protected static final int TICKS_PER_REVOLUTION = 1120;
    protected static final double GEAR_RATIO = 1;
    protected static final int DEGREES_PER_REVOLUTION = 360;
    protected static final double TICKS_PER_INCH = .9 * (TICKS_PER_REVOLUTION / (Math.PI * WHEEL_DIAMETER * GEAR_RATIO));   //132.585
    protected static final double TICKS_PER_DEGREE = 1.5 * ((TICKS_PER_INCH * Math.PI * AXLE_LENGTH) / DEGREES_PER_REVOLUTION); //27.421

    // protected static final double TICKS_PER_INCH = .87 * (TICKS_PER_REVOLUTION / (Math.PI * WHEEL_DIAMETER * GEAR_RATIO));
    //protected static final double TICKS_PER_DEGREE = .65 * ((TICKS_PER_INCH * Math.PI * AXLE_LENGTH) / DEGREES_PER_REVOLUTION);

    //init
    public static void init (HardwareMap hardwareMap) {
        if (initialized) {
            //Bug in FTC Code.  Reinits for FORWARD when Init forthe same Opmode or different Opmode is run
            motorLeft.setDirection(DcMotor.Direction.FORWARD);
            motorRight.setDirection(DcMotor.Direction.REVERSE);
            return;
        }
        initialized = true;


        motorLeft = hardwareMap.dcMotor.get("motor_left");
        motorRight = hardwareMap.dcMotor.get("motor_right");

        motorLeft.setDirection(DcMotor.Direction.FORWARD);
        motorRight.setDirection(DcMotor.Direction.REVERSE);

        resetEncoders();

    }

    protected static void resetEncoders(){
        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    protected static void runToPosition(){
        motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    protected static void runUsingEncoders(){
        motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    protected static void runWithoutEncoders(){
        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    protected static void setPowerOfMotors(double rightPower, double leftPower) {
        motorLeft.setPower(rightPower);
        motorRight.setPower(leftPower);
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

