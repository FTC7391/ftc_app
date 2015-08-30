package FTC7391;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Dylan on 7/19/2015.
 */
public class DriveTrain
{
    DcMotor motorRight;
    DcMotor motorLeft;

    final double diameter = 0.0;
    //Constructor
    public DriveTrain(HardwareMap hardwareMap)
    {
        motorRight = hardwareMap.dcMotor.get("motor_2");
        motorLeft = hardwareMap.dcMotor.get("motor_1");

    }
    public void moveInches(double distance, int power)
    {
        motorRight.setPower(power);
        motorLeft.setPower(power);
    }
    public boolean isDone()
    {
//        if(getCurrentPosition() >= targetMotorPosition)
        return true;
    }
    public void rotateDegrees(double degrees)
    {

    }
    public void arcDegrees(double degrees)
    {

    }

}
