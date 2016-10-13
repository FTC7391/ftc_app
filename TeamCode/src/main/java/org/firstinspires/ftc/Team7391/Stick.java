package FTC7391;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by ArjunVerma on 11/14/15.
 */
public class Stick extends ServoAttachment {

    public Stick(HardwareMap hardwareMap){
        super(1, .8, 0);
        servo = hardwareMap.servo.get("stick");
    }

}
