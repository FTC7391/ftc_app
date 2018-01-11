package org.firstinspires.ftc.team7391;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Allana on 10/3/2015.
 */
public class DriveTrainTele extends DriveTrain{

    public static void init (HardwareMap hardwareMap) {
        DriveTrain.init(hardwareMap);
        //runToPosition();
        runUsingEncoders();
   }

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
        MODE_MOVE_ARC,
        MODE_STOP,
    }

    public static String getPosition(){
        return "Current: " + motorFrontRight.getCurrentPosition() + " Target: " + motorFrontRight.getTargetPosition() + " Current: " + motorFrontLeft.getCurrentPosition();
    }

    public static void setTestMode(TestModes mode, double power, double lateralPower) {
        switch (mode) {
            case MODE_MOVE_FORWARD:
                Log.i("DriveTrain", "move forward " + power);
                moveAxial(1 * power);
                break;
            case MODE_MOVE_BACKWARD:
                Log.i("DriveTrain", "move backward " + power);
                moveAxial(-1 * power);    //negative power = backwards
                break;
            case MODE_MOVE_RIGHT:
                moveLateral(1 * power);
                break;
            case MODE_MOVE_LEFT:
                moveLateral(-1 * power);    //negative power = left
                break;
            case MODE_MOVE_ARC:
                //moveArc(power, lateralPower);
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
                rotate(-1 * power); //negative power = counter clockwise
                break;
            case MODE_ROTATE_LEFT:
                rotate(1 * power);
                break;
            case MODE_STOP:
                setPowerOfMotors(0.0,0.0,0.0,0.0);
                break;
        }
    }

    public static void updatePower(double axialPower, double strafingPower, double rotatePower) {
        // Handle Strafing Movement
        double LF = 0;
        double RF = 0;
        double LB = 0;
        double RB = 0;

        RF += axialPower;
        LF += axialPower;
        RB += axialPower;
        LB += axialPower;
         // Handle Regular Movement
        RF += strafingPower;
        LF -= strafingPower;
        RB -= strafingPower;
        LB += strafingPower;
        // Handle Turning Movement
        RF += rotatePower;
        LF -= rotatePower;
        RB += rotatePower;
        LB -= rotatePower;

        setPowerOfMotors(RF, LF, RB, LB);
    }

    public static void moveAxial(double power){
        setPowerOfMotors(power, power, power, power);
    }
    public static void moveLateral(double power) {
        setPowerOfMotors(-power, power, power, -power);
    }

    //angle is angle counterclockwise from right

    //TBD!!!
    public static void moveDiagonal(double angle){
        double frontLeft = (Math.cos(angle) + Math.sin(angle)) / (Math.sqrt(2));
        double frontRight = (Math.sin(angle) - Math.cos(angle)) / (Math.sqrt(2));
        //setPowerOfMotors(frontRight, frontLeft, );
    }
    public static void moveArc(double axialPower, double rotatePower) {
        if (rotatePower > 0) {
            //setPowerOfMotors(axialPower, rotatePower);
        }
        else {
            //setPowerOfMotors(axialPower, rotatePower);
        }
    }

    public static void rotate(double power) {
        //Positive power, rotate to the left, frontRight & backRight postive
        setPowerOfMotors(-power, power, -power, power);

    }

    /*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
    public static double scaleInput(double dVal)  {
//        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
//                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        double[] scaleArray = { 0.0, 0.0, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
            0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };


        int index = (int) (dVal * 16.0);
        if (index < 0) {
            index = -index;
        } else if (index > 16) {
            index = 16;
        }

        double dScale = 0.0;
        if (dVal < 0) {
            dScale = scaleArray[index];
        } else {
            dScale = -scaleArray[index];
        }

        return dScale;
    }


}


// Test Forward/Backward Movement

// Test Lateral Right/Left Movement

// Test Rotate Degrees (Left and Right)

