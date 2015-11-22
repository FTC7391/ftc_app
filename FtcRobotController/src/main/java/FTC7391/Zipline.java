package FTC7391;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by JoshuaHaines on 11/15/15.
 */
public class Zipline extends ServoAttachment {

//    private final double ARETRACTED_POS = 0;
//    private final double DRIVE_POS = 10;
//    private final double DEPLOYED_POS = 86;

    public Zipline(HardwareMap hardwareMap) {
        super(0.0, 0.10, 1.0);
        servo = hardwareMap.servo.get("zipline");
    }
}
