package FTC7391;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Dylan on 7/19/2015.
 */
public class DriveTrain {
    int currentTick = 0;
    int targetTick = 0;
    DcMotor motorFrontRight;
    DcMotor motorFrontLeft;
    DcMotor motorBackRight;
    DcMotor motorBackLeft;

    double axleLength = 0.0;
    double diameter = 0.0;
    //Constructor
    public DriveTrain(HardwareMap hardwareMap, double diameterOfWheel, double lengthOfAxle)
    {
        motorFrontRight = hardwareMap.dcMotor.get("motor_2");
        motorFrontLeft = hardwareMap.dcMotor.get("motor_1");
        motorBackRight = hardwareMap.dcMotor.get("motor_3");
        motorBackLeft = hardwareMap.dcMotor.get("motor_4");

        diameter = diameterOfWheel;
        axleLength = lengthOfAxle;

    }
    public void moveInches(double distance, int power) {
        motorFrontRight.setPower(power);
        motorFrontLeft.setPower(power);
        motorBackRight.setPower(power);
        motorBackLeft.setPower(power);
        targetTick = (int)(distance*(1440/(Math.PI*diameter)));

        motorFrontRight.setTargetPosition(targetTick);
        motorFrontLeft.setTargetPosition(targetTick);
        motorBackRight.setTargetPosition(targetTick);
        motorBackLeft.setTargetPosition(targetTick);
    }
    public void lateraMoveInches(boolean right, double distance, int power) {
        if (right) {
            motorFrontRight.setPower(power);
            motorFrontLeft.setPower(-power);
            motorBackRight.setPower(-power);
            motorBackLeft.setPower(power);
        }
        else if (!right) {
            motorFrontRight.setPower(-power);
            motorFrontLeft.setPower(power);
            motorBackRight.setPower(power);
            motorBackLeft.setPower(-power);
        }

        targetTick = (int)(distance*(1440/(Math.PI*diameter)));
        motorFrontRight.setTargetPosition(targetTick);
        motorFrontLeft.setTargetPosition(targetTick);
        motorBackRight.setTargetPosition(targetTick);
        motorBackLeft.setTargetPosition(targetTick);
    }
    public boolean isDone()
    {
//        if(getCurrentPosition() >= targetMotorPosition)
        return true;
    }
    public void rotateDegrees(double degrees, double power) {
        if (degrees > 0){
            motorFrontRight.setPower(power);
            motorBackRight.setPower(power);
            motorBackRight.setPower(-power);
            motorFrontLeft.setPower(-power);
        }
        else if (degrees < 0){
            motorFrontRight.setPower(-power);
            motorBackRight.setPower(-power);
            motorBackRight.setPower(power);
            motorFrontLeft.setPower(power);
        }
        targetTick = (int)(degrees*(1440/(360)));
        motorFrontRight.setTargetPosition(targetTick);
        motorFrontLeft.setTargetPosition(targetTick);
        motorBackRight.setTargetPosition(targetTick);
        motorBackLeft.setTargetPosition(targetTick);
    }
}
