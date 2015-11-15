package FTC7391;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by ArjunVerma on 10/3/15.
 */
public class Stick {


    private static Servo rescueStick;

    private static double position = 0.0; //to be added
    private static int originalTicks = 0; //to be added

    private static final double DROP_POSITION = 0.0; //for now

    private static final int TOTAL_TICKS = 1120;
    private static final int TOTAL_DEGREES = 360;
    private static final double DIAMETER= 1.0;  //??????????
    private static final int OFFSET = (int) (TOTAL_TICKS / (Math.PI * DIAMETER));


    protected static boolean initialized = false;



    public static void init (HardwareMap hardwareMap) {
        if (initialized) return;
        initialized = true;

        rescueStick = hardwareMap.servo.get("motor_high");


    }

//    private int currentTicks1 = liftHigh.getCurrentPosition();
//    private int currentTicks2 = liftLow.getCurrentPosition();
//    private int overallCurrent = currentTicks1 + currentTicks2;



    public Stick(){}


    public void setDrivePosition(){

        rescueStick.setPosition(Servo.MIN_POSITION);

    }

    public void setDropPosition(){

    }


    public enum TestModes {
        MOVE_FORWARD,
        MOVE_BACKWARD,

    }


    public static void setTestMode(TestModes mode) {
        switch (mode) {
            case MOVE_FORWARD:
                position += 5;
                rescueStick.setPosition(position);
                break;
            case MOVE_BACKWARD:
                position -= 5;
                rescueStick.setPosition(position);    //negative power = backwards
                break;
        }
    }


}
