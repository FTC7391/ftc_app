package org.firstinspires.ftc.team7391;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Allana on 10/3/2015.
 */
public class DriveTrainAuto extends DriveTrain{

    private static int frontRightTargetTick = 0;
    private static int frontLeftTargetTick = 0;
    private static int backRightTargetTick = 0;
    private static int backLeftTargetTick = 0;
    private static int[] cummulativeError = {0,0,0,0};
    private static int ticks = 0;
    private static boolean isRotating = false;

    private static final int MOTOR_POSITION_THESHOLD = 20;

    public static void init (HardwareMap hardwareMap) {
        DriveTrain.init(hardwareMap);

        resetEncoders();
     }

    public static void stop(){
        DriveTrain.resetEncoders();
        DriveTrainAuto.setPowerOfMotors(0, 0);
    }

    public static String getPosition(TestModes mode){

        switch (mode) {
            case MODE_MOVE_RIGHT:
                return "Current: "+ motorRight.getCurrentPosition() + " Target: " + motorRight.getTargetPosition();
            case MODE_MOVE_LEFT:
                return "Current: "+ motorLeft.getCurrentPosition() + " Target: " + motorLeft.getTargetPosition();
            default:
                return "Invalid DriveTrain getPosition() mode = " + mode;
        }
    }

    /**Move Inches.
     *
     * @param distance    Set distance negative to move backwards
     * @param power       Magnitude only so typically pass in a positive value.
     */
    public static void moveInches(double distance, double power) {

        isRotating = false;

        Log.i("FTC7391", "Auto: " + "moveInches:" + "inches:" + distance + " power:" + power);

        power = Math.abs(power);
        ticks = (int) (distance * TICKS_PER_INCH);
        setMotorTargetPosition(ticks, ticks, ticks, ticks);
        if (distance > 0)
            setPowerOfMotors(power, power);
        else if (distance < 0)
            setPowerOfMotors(-power, -power);

//        else
//            //setPowerOfMotors(0.0, 0.0, 0.0, 0.0);
     }

    public static void moveLateralInches(boolean right, int distance, double power) {
        if (right) {
            setPowerOfMotors(power, -power);
            setMotorTargetPosition(distance, -distance, -distance, distance);
        } else { // if (!right)
            setPowerOfMotors(-power, power);
            setMotorTargetPosition(-distance, distance, distance, -distance);
        }
    }

    /**
     *
     * @param angle          angle counterclockwise from right
     * @param distance       distance diagonally to move
     */
    public static void moveDiagonalInches(double angle, int distance){
        double frontLeft = (Math.cos(angle) + Math.sin(angle));
        double frontRight = (Math.sin(angle) - Math.cos(angle));
        setPowerOfMotors(frontRight / Math.sqrt(2), frontLeft / Math.sqrt(2));
        //setMotorTargetPosition(frontRight/distance, frontLeft/distance, frontLeft/distance, frontRight/distance);

    }

    /**
     *
     * @param degrees        positive is counterclockwise
     * @param power          always positive
     */
    public static void rotateDegrees(double degrees, double power) {
        isRotating = true;
        //I changed ticks to degrees and changed the thing under the first else
        //With no fudge factor,
        // for degrees> 0, get 235deg when want 360deg
        // for degress< 0, get -190deg when want -360deg
        if (degrees > 0)
           ticks = (int) (degrees * TICKS_PER_DEGREE);
        else {
            //ticks = (int) (degrees * TICKS_PER_DEGREE * 1.5425);
            ticks = (int) (degrees * TICKS_PER_DEGREE);
        }
        setMotorTargetPosition(-ticks, ticks, -ticks, ticks);

        if (degrees > 0) {
            //Rotate to the left,frontRight & backRight postive
            setPowerOfMotors(power, -power);
        } else if (degrees < 0)
            //Rotate to the right,frontLeft & backLeft postive
            setPowerOfMotors(-power, power);
//        } else {
//            //setPowerOfMotors(0.0, 0.0, 0.0, 0.0);
//        }

    }

    private static void setMotorTargetPosition(int frontRightPosition, int frontLeftPosition, int backRightPosition, int backLeftPosition) {
        runToPosition();
        motorRight.setTargetPosition(motorRight.getCurrentPosition() + frontRightPosition);
        motorLeft.setTargetPosition(motorLeft.getCurrentPosition() + frontLeftPosition);
    }

    public static boolean isDone(){
        return isAtPosition();
        //return isEncoderDone();
    }

    public static boolean isAtPosition(){
        boolean frDone = false, flDone = false;

        Log.v("FTC7391", "Auto: " + "DriveTrain " + "         motorR " + motorRight.isBusy() + " " + motorRight.getCurrentPosition() + " " + motorRight.getTargetPosition() +
              "        motorL " + motorLeft.isBusy() + " " + motorLeft.getCurrentPosition() + " " + motorLeft.getTargetPosition());

        if (/*!motorRight.isBusy() &&*/ Math.abs(motorRight.getCurrentPosition() - motorRight.getTargetPosition()) < MOTOR_POSITION_THESHOLD ){
        //if ( Math.abs(motorRight.getCurrentPosition() - motorRight.getTargetPosition()) < MOTOR_POSITION_THESHOLD ){
              //motorRight.setPower(0);
                frDone = true;
         }
        if (/*!motorLeft.isBusy() &&*/ Math.abs(motorLeft.getCurrentPosition() - motorLeft.getTargetPosition()) < MOTOR_POSITION_THESHOLD ){
        //if ( Math.abs(motorLeft.getCurrentPosition() - motorLeft.getTargetPosition()) < MOTOR_POSITION_THESHOLD ){
                //motorLeft.setPower(0);
                flDone = true;
         }


        if (frDone && flDone){
             return true;
        }
        else return false;
    }

    /* Not using this during Autonomous */
    public static boolean isEncoderDone() {
        if (!isRotating &&
                ( (ticks > 0 && motorRight.getCurrentPosition() >= motorRight.getTargetPosition()) ||
                        (ticks < 0 && motorRight.getCurrentPosition() <= motorRight.getTargetPosition()))   ){
            setPowerOfMotors(0.0, 0.0);
            return true;
        }
        else return false;
    }

    private static void fixStability(){
        int[] positions = {motorRight.getCurrentPosition(), motorLeft.getCurrentPosition()};

    }
}


// Test Forward/Backward Movement

// Test Lateral Right/Left Movement

// Test Rotate Degrees (Left and Right)

