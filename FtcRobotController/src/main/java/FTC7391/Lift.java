package FTC7391;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by ArjunVerma on 10/3/15.
 */
public class Lift {


    private static DcMotor liftHigh;
    private static DcMotor liftLow;


    private static int driveModeTicks = 0; //to be added
    private static int originalTicks = 0; //to be added

    private static final int TOTAL_TICKS = 1120;
    private static final int TOTAL_DEGREES = 360;
    private static final double DIAMETER= 1.0;  //??????????
    private static final int OFFSET = (int) (TOTAL_TICKS / (Math.PI * DIAMETER));

    private static final double f = 1;
    private static final double h = 1;
    private static final double d = 1;
    private static double x = 1;

    protected static boolean initialized = false;

    private static double angle = 180 - (Math.atan(h/d) + (Math.acos(((f*f) - (x*x) + (h*h) + (d*d)) / (2*f*Math.sqrt((h*h)+(d*d))))));


    public static void init (HardwareMap hardwareMap) {
        if (initialized) return;
        initialized = true;

        liftHigh = hardwareMap.dcMotor.get("motor_high");
        liftLow = hardwareMap.dcMotor.get("motor_low");


    }

    private int currentTicks1 = liftHigh.getCurrentPosition();
    private int currentTicks2 = liftLow.getCurrentPosition();
    private int overallCurrent = currentTicks1 + currentTicks2;



    public Lift(){}


    public void setDriveMode(){

        if(currentTicks1 < driveModeTicks) {
            while (currentTicks1 <= driveModeTicks) {
                liftHigh.setPower(10);
                currentTicks1 = liftHigh.getCurrentPosition();
            }
        }
        else if(currentTicks1 > driveModeTicks) {
            while(currentTicks1 >= driveModeTicks){
                liftHigh.setPower(-10);
                currentTicks1 = liftHigh.getCurrentPosition();
            }
        }
        else{}

    }

    public void raise(int targetTicks){

        int differentTicks = targetTicks - currentTicks1;
        double target = 0.5*differentTicks + targetTicks;

        Lift.setMotorTargetPosition((int) target, (int) target);
        Lift.setPowerOfMotors(1, 1);
        if(Lift.isDone())
            Lift.setPowerOfMotors(0, 0);

    }

    public void lower(int targetTicks){

        int differentTicks = currentTicks1 - targetTicks;
        double target = targetTicks - 0.5*differentTicks;

        Lift.setMotorTargetPosition((int) target, (int) target);
        Lift.setPowerOfMotors(-1, -1);
        if(Lift.isDone())
            Lift.setPowerOfMotors(0,0);


    }

    public enum TestModes {
        MODE_MOVE_HIGH,
        MODE_MOVE_LOW,
        MODE_STOP,
    }


    public static void setTestMode(TestModes mode, double power) {
        switch (mode) {
            case MODE_MOVE_HIGH:
                liftHigh.setPower(1 * power);
                break;
            case MODE_MOVE_LOW:
                liftLow.setPower(1 * power);    //negative power = backwards
                break;
            case MODE_STOP:
                Lift.setPowerOfMotors(0,0);
                break;
        }
    }

    public static boolean isDone() {
        if(liftHigh.getPower() > 0 && liftHigh.getTargetPosition() <= liftHigh.getCurrentPosition() && liftLow.getTargetPosition() <= liftLow.getCurrentPosition())
            return true;
        else if(liftHigh.getPower() < 0 && liftHigh.getTargetPosition() >= liftHigh.getCurrentPosition() && liftLow.getTargetPosition() >= liftLow.getCurrentPosition())
            return true;
        else
            return false;

    }

    public static void setMotorTargetPosition(int liftHighDifference, int liftLowDifference) {
        if(liftHigh.getCurrentPosition() + liftHighDifference > 0)
            liftHigh.setTargetPosition(liftHigh.getCurrentPosition() + liftHighDifference);
        else
            liftHigh.setTargetPosition(liftHigh.getCurrentPosition());


        if(liftLow.getCurrentPosition() + liftHighDifference > 0)
            liftLow.setTargetPosition(liftHigh.getCurrentPosition() + liftLowDifference);
        else
            liftLow.setTargetPosition(liftLow.getCurrentPosition());


    }

    public static void setPowerOfMotors(double liftHighPower, double liftLowPower) {
        liftHigh.setPower(liftHighPower);
        liftLow.setPower(liftLowPower);

    }

}
