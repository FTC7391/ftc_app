package FTC7391;

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

    public static String getPosition(TestModes mode){

        switch (mode) {
            case MODE_MOVE_FRONT_RIGHT:
                return "Current: "+ motorFrontRight.getCurrentPosition() + " Target: " + motorFrontRight.getTargetPosition();
            case MODE_MOVE_FRONT_LEFT:
                return "Current: "+ motorFrontLeft.getCurrentPosition() + " Target: " + motorFrontLeft.getTargetPosition();
            case MODE_MOVE_BACK_RIGHT:
                return "Current: "+ motorBackRight.getCurrentPosition() + " Target: " + motorBackRight.getTargetPosition();
            case MODE_MOVE_BACK_LEFT:
                return "Current: "+ motorBackLeft.getCurrentPosition() + " Target: " + motorBackLeft.getTargetPosition();
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

        power = Math.abs(power);
        ticks = (int) (distance * TICKS_PER_INCH);
        setMotorTargetPosition(ticks, ticks, ticks, ticks);
        if (distance > 0)
            setPowerOfMotors(power, power, power, power);
        else if (distance < 0)
            setPowerOfMotors(-power, -power, -power, -power);

//        else
//            //setPowerOfMotors(0.0, 0.0, 0.0, 0.0);
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

    /**
     *
     * @param angle          angle counterclockwise from right
     * @param distance       distance diagonally to move
     */
    public static void moveDiagonalInches(double angle, int distance){
        double frontLeft = (Math.cos(angle) + Math.sin(angle));
        double frontRight = (Math.sin(angle) - Math.cos(angle));
        setPowerOfMotors(frontRight / Math.sqrt(2), frontLeft / Math.sqrt(2), frontLeft / Math.sqrt(2), frontRight / Math.sqrt(2));
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
           ticks = (int) (degrees * TICKS_PER_DEGREE * 1.825);
        else {
            //ticks = (int) (degrees * TICKS_PER_DEGREE * 1.5425);
            ticks = (int) (degrees * TICKS_PER_DEGREE * 1.825);
        }
        setMotorTargetPosition(ticks, -ticks, ticks, -ticks);

        if (degrees > 0) {
            //Rotate to the left,frontRight & backRight postive
            setPowerOfMotors(power, -power, power, -power);
        } else if (degrees < 0)
            //Rotate to the right,frontLeft & backLeft postive
            setPowerOfMotors(-power, power, -power, power);
//        } else {
//            //setPowerOfMotors(0.0, 0.0, 0.0, 0.0);
//        }

    }

    private static void setMotorTargetPosition(int frontRightPosition, int frontLeftPosition, int backRightPosition, int backLeftPosition) {
        runToPosition();
        motorFrontRight.setTargetPosition(motorFrontRight.getCurrentPosition() + frontRightPosition);
        motorFrontLeft.setTargetPosition(motorFrontLeft.getCurrentPosition() + frontLeftPosition);
        motorBackRight.setTargetPosition(motorBackRight.getCurrentPosition() + backRightPosition);
        motorBackLeft.setTargetPosition(motorBackLeft.getCurrentPosition() + backLeftPosition);
    }

    public static boolean isDone(){
        return isAtPosition();
        //return isEncoderDone();
    }

    public static boolean isAtPosition(){
        boolean frDone = false, flDone = false, brDone = false, blDone = false;

        Log.v("Auto", "motorFR " + motorFrontRight.isBusy() + " " + motorFrontRight.getCurrentPosition() + " " + motorFrontRight.getTargetPosition());
        Log.v("Auto", "motorFR " + motorFrontRight.isBusy() + " " + motorFrontRight.getCurrentPosition() + " " + motorFrontRight.getTargetPosition());
        Log.v("Auto", "motorFL                          " + motorFrontLeft.isBusy() + " " + motorFrontLeft.getCurrentPosition() + " " + motorFrontLeft.getTargetPosition());
        Log.v("Auto", "motorBR                                                    " + motorBackRight.isBusy() + " " + motorBackRight.getCurrentPosition() + " " + motorBackRight.getTargetPosition());
        Log.v("Auto", "motorBL                                                                               " + motorBackLeft.isBusy() + " " + motorBackLeft.getCurrentPosition() + " " + motorBackLeft.getTargetPosition());

        if (!motorFrontRight.isBusy() && Math.abs(motorFrontRight.getCurrentPosition() - motorFrontRight.getTargetPosition()) < MOTOR_POSITION_THESHOLD ){
              //motorFrontRight.setPower(0);
                frDone = true;
         }

        if (!motorFrontLeft.isBusy() && Math.abs(motorFrontLeft.getCurrentPosition() - motorFrontLeft.getTargetPosition()) < MOTOR_POSITION_THESHOLD ){
                //motorFrontLeft.setPower(0);
                flDone = true;
         }

        if (!motorBackRight.isBusy() && Math.abs(motorBackRight.getCurrentPosition() - motorBackRight.getTargetPosition()) < MOTOR_POSITION_THESHOLD ){
                //motorBackRight.setPower(0);
                brDone = true;
        }

        if (!motorBackLeft.isBusy()  && Math.abs(motorBackLeft.getCurrentPosition() - motorBackLeft.getTargetPosition()) < MOTOR_POSITION_THESHOLD ){
            //motorFrontLeft.setPower(0);
            blDone = true;
        }


        if (frDone && flDone && brDone && blDone){
            return true;
        }
        else return false;
    }

    /* Not using this during Autonomous */
    public static boolean isEncoderDone() {
        if (isRotating &&
                ( (ticks > 0 && motorBackLeft.getCurrentPosition() <= motorBackLeft.getTargetPosition()) ||
                        (ticks < 0 && motorBackRight.getCurrentPosition() <= motorBackRight.getTargetPosition()))   ) {
            setPowerOfMotors(0.0, 0.0, 0.0, 0.0);
            return true;
        }
        else if (!isRotating &&
                ( (ticks > 0 && motorFrontRight.getCurrentPosition() >= motorFrontRight.getTargetPosition()) ||
                        (ticks < 0 && motorFrontRight.getCurrentPosition() <= motorFrontRight.getTargetPosition()))   ){
            setPowerOfMotors(0.0, 0.0, 0.0, 0.0);
            return true;
        }
        else return false;
    }

    private static void fixStability(){
        int[] positions = {motorFrontRight.getCurrentPosition(), motorFrontLeft.getCurrentPosition(), motorBackRight.getCurrentPosition(), motorBackLeft.getCurrentPosition()};

    }
}


// Test Forward/Backward Movement

// Test Lateral Right/Left Movement

// Test Rotate Degrees (Left and Right)

