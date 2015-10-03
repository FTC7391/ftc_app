package FTC7391;

import com.qualcomm.robotcore.hardware.DcMotor;
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

    protected static final double AXLE_LENGTH = 14.5;
    protected static final double WHEEL_DIAMETER = 4.0;
    protected static final int TICKS_PER_REVOLUTION = 1440;
    protected static final int DEGREES_PER_REVOLUTION = 360;
    protected static final int TICKS_PER_INCH = (int) (TICKS_PER_REVOLUTION / (Math.PI * WHEEL_DIAMETER));

    //init
    public static void init (HardwareMap hardwareMap) {
        if (initialized) return;
        initialized = true;

        motorFrontRight = hardwareMap.dcMotor.get("motor_2");
        motorFrontLeft = hardwareMap.dcMotor.get("motor_1");
        motorBackRight = hardwareMap.dcMotor.get("motor_3");
        motorBackLeft = hardwareMap.dcMotor.get("motor_4");

        //run_using_encoders();

    }

    protected static void setPowerOfMotors(double frontRightPower, double frontLeftPower, double backRightPower, double backLeftPower) {
        motorFrontRight.setPower(frontRightPower);
        motorFrontLeft.setPower(frontLeftPower);
        motorBackRight.setPower(backRightPower);
        motorBackLeft.setPower(backLeftPower);
    }

}


// Test Forward/Backward Movement

// Test Lateral Right/Left Movement

// Test Rotate Degrees (Left and Right)

