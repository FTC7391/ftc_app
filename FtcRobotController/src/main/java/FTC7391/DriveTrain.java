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
    private static final double DIAMETER = 4.0;
    private static final int TOTAL_TICKS = 1440;
    private static final int TOTAL_DEGREES = 360;

    private static final int OFFSET = (int)(TOTAL_TICKS/(Math.PI*DIAMETER));

    //Constructor
    public DriveTrain(HardwareMap hardwareMap)
    {
        motorFrontRight = hardwareMap.dcMotor.get("motor_2");
        motorFrontLeft = hardwareMap.dcMotor.get("motor_1");
        motorBackRight = hardwareMap.dcMotor.get("motor_3");
        motorBackLeft = hardwareMap.dcMotor.get("motor_4");

        //run_using_encoders();

    }
    //Move Inches. Calculates the target tick
    public static void moveInches(int distance, int power) {

        setPowerOfMotors(power, power, power, power);
        frontRightTargetTick = motorFrontRight.getCurrentPosition() + distance*OFFSET;
        frontLeftTargetTick = motorFrontLeft.getCurrentPosition() + distance*OFFSET;
        backRightTargetTick = motorBackRight.getCurrentPosition() + distance*OFFSET;
        backLeftTargetTick = motorBackLeft.getCurrentPosition() + distance*OFFSET;

        setMotorTargetPosition(frontRightTargetTick, frontLeftTargetTick, backRightTargetTick, backLeftTargetTick);
    }

    public static void lateraMoveInches(boolean right, int distance, int power) {
        if (right) {
            setPowerOfMotors(power, -power, -power, power);
        }
        else if (!right) {
            setPowerOfMotors(-power, power, power, -power);
        }

        frontRightTargetTick = motorFrontRight.getCurrentPosition() + distance*OFFSET;
        frontLeftTargetTick = motorFrontLeft.getCurrentPosition() + distance*OFFSET;
        backRightTargetTick = motorBackRight.getCurrentPosition() + distance*OFFSET;
        backLeftTargetTick = motorBackLeft.getCurrentPosition() + distance*OFFSET;

        setMotorTargetPosition(frontRightTargetTick, frontLeftTargetTick, backRightTargetTick, backLeftTargetTick);
    }
    public boolean isDone()
    {
//        if(getCurrentPosition() >= targetMotorPosition)
        return true;
    }
    public static void rotateDegrees(double degrees, double power) {
        if (degrees > 0) {
            setPowerOfMotors(power,power,-power,-power);
        }
        else if (degrees < 0) {
            setPowerOfMotors(-power,-power,power,power);
        }
        else {
           setPowerOfMotors(0.0,0.0,0.0,0.0);
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

}
