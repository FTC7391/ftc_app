package FTC7391;

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

    //Move Inches. Calculates the target tick
    public static void moveInches(int distance, double power) {

        setPowerOfMotors(power, power, power, power);
        setMotorTargetPosition(distance, distance, distance, distance);

    }

    public static void moveLateralInches(boolean right, int distance, double power) {
        if (right) {
            setPowerOfMotors(power, -power, -power, power);
            setMotorTargetPosition(distance, -distance, -distance, distance);
        } else { // if (!right)
            setPowerOfMotors(-power, power, power, -power);
            setMotorTargetPosition(-distance, distance, distance, -distance);
        }
    }

    //angle is angle counterclockwise from right
    public static void moveDiagonalInches(double angle, int distance){
        double frontLeft = (Math.cos(angle) + Math.sin(angle));
        double frontRight = (Math.sin(angle) - Math.cos(angle));
        setPowerOfMotors(frontRight / Math.sqrt(2), frontLeft / Math.sqrt(2), frontLeft / Math.sqrt(2), frontRight / Math.sqrt(2));
        //setMotorTargetPosition(frontRight/distance, frontLeft/distance, frontLeft/distance, frontRight/distance);

    }

    public static boolean isDone() {
        if(motorFrontRight.getPower() > 0 && motorFrontRight.getCurrentPosition() >= motorFrontRight.getTargetPosition())
            return true;
        else if(motorFrontRight.getPower() < 0 && motorFrontRight.getCurrentPosition() <= motorFrontRight.getTargetPosition())
            return true;
        else return false;
    }

    public static void rotateDegrees(double degrees, double power) {
        if (degrees > 0) {
            setPowerOfMotors(power, power, -power, -power);
        } else if (degrees < 0) {
            setPowerOfMotors(-power, -power, power, power);
        } else {
            setPowerOfMotors(0.0, 0.0, 0.0, 0.0);
        }

        //targetTick = currentTick + (int)(degrees*(TICKS_PER_REVOLUTION/(DEGREES_PER_REVOLUTION)));
        //setMotorTargetPosition(targetTick, targetTick, targetTick, targetTick);
    }

    private static void setMotorTargetPosition(int frontRightPosition, int frontLeftPosition, int backRightPosition, int backLeftPosition) {
        motorFrontRight.setTargetPosition(motorFrontRight.getCurrentPosition() + frontRightPosition * TICKS_PER_INCH);
        motorFrontLeft.setTargetPosition(motorFrontLeft.getCurrentPosition() + frontLeftPosition * TICKS_PER_INCH);
        motorBackRight.setTargetPosition(motorBackRight.getCurrentPosition() + backRightPosition * TICKS_PER_INCH);
        motorBackLeft.setTargetPosition(motorBackLeft.getCurrentPosition() + backLeftPosition * TICKS_PER_INCH);
    }

    private static void fixStability(){
        int[] positions = {motorFrontRight.getCurrentPosition(), motorFrontLeft.getCurrentPosition(), motorBackRight.getCurrentPosition(), motorBackLeft.getCurrentPosition()};

    }
}


// Test Forward/Backward Movement

// Test Lateral Right/Left Movement

// Test Rotate Degrees (Left and Right)

