package org.firstinspires.ftc.team7391;

import com.qualcomm.hardware.motors.NeveRest60Gearmotor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.MotorConfigurationType;

/**
 * Created by Dylan on 7/19/2015.
 */
public class DriveTrain {

    //@interface MotorTypeConfiguration NeveRest60GearMotor{}
    protected static DcMotor motorFrontLeft;


    //@interface MotorType NeveRest60GearMotor
    protected static DcMotor motorFrontRight;

    //@interface MotorType NeveRest60GearMotor
    protected static DcMotor motorBackRight;

    //@interface MotorType NeveRest60GearMotor
    protected static DcMotor motorBackLeft;




    protected static boolean initialized = false;

    //TBD ASK HARDWARE GUYS
    protected static final double AXLE_LENGTH = 15.8;
    protected static final double WHEEL_DIAMETER = 2.42;
    protected static final int TICKS_PER_REVOLUTION = 1680;
    protected static final double GEAR_RATIO = 1;
    protected static final int DEGREES_PER_REVOLUTION = 360;
    protected static final double TICKS_PER_INCH = 0.43 * (TICKS_PER_REVOLUTION / (Math.PI * WHEEL_DIAMETER * GEAR_RATIO));   //132.585
    protected static final double TICKS_PER_LATERAL_INCH = 1 * (TICKS_PER_REVOLUTION / (Math.PI * WHEEL_DIAMETER * GEAR_RATIO));   //132.585
    protected static final double TICKS_PER_DEGREE = 1 * ((TICKS_PER_INCH * Math.PI * AXLE_LENGTH) / DEGREES_PER_REVOLUTION); //27.421

    // protected static final double TICKS_PER_INCH = .87 * (TICKS_PER_REVOLUTION / (Math.PI * WHEEL_DIAMETER * GEAR_RATIO));
    //protected static final double TICKS_PER_DEGREE = .65 * ((TICKS_PER_INCH * Math.PI * AXLE_LENGTH) / DEGREES_PER_REVOLUTION);

    //init
    public static void init (HardwareMap hardwareMap) {
        if (initialized) {
            //Bug in FTC Code.  Reinits for FORWARD when Init forthe same Opmode or different Opmode is run
            setMotorDirection();
            return;
        }
        initialized = true;


        motorFrontLeft = hardwareMap.dcMotor.get("motor_front_left");
        motorFrontRight = hardwareMap.dcMotor.get("motor_front_right");
        motorBackLeft = hardwareMap.dcMotor.get("motor_back_left");
        motorBackRight = hardwareMap.dcMotor.get("motor_back_right");

        /*
        motorFrontLeft.setMotorType(DcMotor);
        motorFrontRight.setMotorType(NeveRest60Gearmotor);
        motorBackLeft.setMotorType(NeveRest60Gearmotor);
        motorBackRight.setMotorType(NeveRest60Gearmotor);
*/

        setMotorDirection();
        resetEncoders();

    }

    public static void setMotorDirection(){
        motorFrontLeft.setDirection(DcMotor.Direction.FORWARD);
        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
        motorBackRight.setDirection(DcMotor.Direction.FORWARD);
    }

    protected static void resetEncoders(){
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    protected static void runToPosition(){
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    protected static void runUsingEncoders(){
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    protected static void runWithoutEncoders(){
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    protected static void setPowerOfMotors(double frontrightPower, double frontleftPower, double backrightPower, double backleftPower) {
        motorFrontLeft.setPower(frontleftPower);
        motorFrontRight.setPower(frontrightPower);
        motorBackLeft.setPower(backleftPower);
        motorBackRight.setPower(backrightPower);
    }


    public enum TestModes {
        MODE_MOVE_RIGHT,
        MODE_MOVE_LEFT,
        MODE_STOP,
    }

    public static void setTestMode(TestModes mode, double power) {
        switch (mode) {
            case MODE_MOVE_RIGHT:
                setPowerOfMotors(power, 0, 0, 0);
                break;
            case MODE_MOVE_LEFT:
                setPowerOfMotors(0, power, 0, 0);
                break;
            case MODE_STOP:
                setPowerOfMotors(0.0,0.0, 0, 0);
                break;
        }
    }

}


// Test Forward/Backward Movement

// Test Lateral Right/Left Movement

// Test Rotate Degrees (Left and Right)

