package FTC7391;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Arjun Verma on 10/3/15.
 */
public class Lift {


    public static DcMotor liftHigh;
    public static DcMotor liftLow;
    public static DcMotor liftHook;
    public static DcMotor liftAngle;


    private static int driveModeTicksHigh = 1575; //to be added
    private static int driveModeTicksLow = -657;


    public static int originalTicksHigh = 0; //to be added
    public static int originalTicksLow = 0; //to be added
    public static int originalTicksHook = 0; //to be added
    public static int originalTicksAngle = 0; //to be added

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

    private int currentTicks1 = liftHigh.getCurrentPosition();
    private int currentTicks2 = liftLow.getCurrentPosition();
    private int overallCurrent = currentTicks1 + currentTicks2;

    private static boolean highDone = true;
    private static boolean lowDone = true;
    private static boolean angleDone = true;


    public static void init (HardwareMap hardwareMap) {
        if (initialized) return;
        initialized = true;

        liftHigh = hardwareMap.dcMotor.get("motor_high");
        liftLow = hardwareMap.dcMotor.get("motor_low");
        liftHook = hardwareMap.dcMotor.get("motor_hook");
        liftAngle = hardwareMap.dcMotor.get("motor_angle");

        runUsingEncoders();

        originalTicksHigh = liftHigh.getCurrentPosition();
        originalTicksLow = liftLow.getCurrentPosition();
        originalTicksHook = liftHook.getCurrentPosition();
        originalTicksAngle = liftAngle.getCurrentPosition();
    }

    protected static void resetEncoders(){
        liftHigh.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        liftLow.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        liftAngle.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        liftHook.setMode(DcMotorController.RunMode.RESET_ENCODERS);

    }

    protected static void runUsingEncoders(){
        resetEncoders();
        liftHigh.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        liftLow.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        liftAngle.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        liftHook.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

    }


    public static int getTicksLiftHigh(){return liftHigh.getCurrentPosition();}
    public static int getTicksLiftLow(){return liftLow.getCurrentPosition();}
    public static int getTicksLiftAngle(){return liftAngle.getCurrentPosition();}
    public static int getTicksLiftHook(){return liftHook.getCurrentPosition();}

//    public static int getOriginalTicksHigh(){return originalTicksHigh;}
//    public static int getOriginalTicksLow(){return originalTicksLow;}
//    public static int getOriginalTicksAngle(){return originalTicksAngle;}




    public Lift(){}


//    public void setDriveMode(){
//
//        if(liftHigh.getCurrentPosition() < driveModeTicksHigh){
//            while(!isDone()) {
//                liftHigh.setPower(0.5);
//            }
//        }
//        else{
//            while(!isDone()) {
//                liftHigh.setPower(-0.5);
//            }
//        }
//
//        if(liftLow.getCurrentPosition() < driveModeTicksHigh){
//            while(!isDone()) {
//                liftLow.setPower
//            }
//        }
//
//
//    }



    public enum TestModes {
        MODE_MOVE_HIGH,
        MODE_MOVE_LOW,
        MODE_MOVE_ANGLE,
        MODE_MOVE_BOTH,
        MODE_MOVE_HOOK,
        MODE_LIFT_STAGE1,
        MODE_STOP,
    }


    public static void setTestMode(TestModes mode, double power) {
        switch (mode) {
            case MODE_MOVE_HIGH:
                //while(liftHigh.getCurrentPosition() > -5500)
                    liftHigh.setPower(1 * power);
                break;
            case MODE_MOVE_LOW:
                //while(liftLow.getCurrentPosition() > -5500)
                    liftLow.setPower(1 * power);    //negative power = backwards
                break;
            case MODE_MOVE_ANGLE:
                liftAngle.setPower(1 * power);
                break;
            case MODE_MOVE_BOTH:
                liftHigh.setPower(1 * power);
                liftLow.setPower(1 * power);    //negative power = backwards
                break;
            case MODE_MOVE_HOOK:
                liftHook.setPower(1*power);
                break;
            case MODE_STOP:
                Lift.setPowerOfMotors(0,0,0);
                break;
        }
    }

    public static boolean isDone() {


        if(liftHigh.getPower() > 0) {
            if (liftHigh.getTargetPosition() <= liftHigh.getCurrentPosition()) {
                liftHigh.setPower(0);
                highDone = true;
            }
        }

        else if(liftHigh.getPower() < 0) {
            if (liftHigh.getTargetPosition() >= liftHigh.getCurrentPosition()) {
                liftHigh.setPower(0);
                highDone = true;
            }
        }

        if(liftLow.getPower() > 0) {
            if(liftLow.getTargetPosition() >= liftLow.getCurrentPosition()) {
                liftLow.setPower(0);
                lowDone = true;
            }

        }

        else if(liftLow.getPower() < 0) {
            if(liftLow.getTargetPosition() <= liftLow.getCurrentPosition()) {
                liftLow.setPower(0);
                lowDone = true;
            }
        }

        if(liftAngle.getPower() > 0) {
            if(liftAngle.getTargetPosition() >= liftAngle.getCurrentPosition()) {
                liftAngle.setPower(0);
                angleDone = true;
            }

        }

        else if(liftAngle.getPower() < 0) {
            if(liftAngle.getTargetPosition() <= liftAngle.getCurrentPosition()) {
                liftAngle.setPower(0);
                angleDone = true;
            }
        }

        if(angleDone && lowDone && highDone)
            return true;

        return false;

    }

    public static void setMotorTargetPosition(int liftHighDifference, int liftLowDifference, int liftAngleDifference) {
//        if(liftHigh.getCurrentPosition() + liftHighDifference > 0)
//            liftHigh.setTargetPosition(liftHigh.getCurrentPosition() + liftHighDifference);
//        else
//            liftHigh.setTargetPosition(liftHigh.getCurrentPosition());
//
//
//        if(liftLow.getCurrentPosition() + liftLowDifference < 0)
//            liftLow.setTargetPosition(liftLow.getCurrentPosition() + liftLowDifference);
//        else
//            liftLow.setTargetPosition(liftLow.getCurrentPosition());
//
//
//        if(liftAngle.getCurrentPosition() + liftAngleDifference < 0)
//            liftAngle.setTargetPosition(liftAngle.getCurrentPosition() + liftAngleDifference);
//        else
//            liftAngle.setTargetPosition(liftAngle.getCurrentPosition());

        liftHigh.setTargetPosition(liftHighDifference);
        liftLow.setTargetPosition(liftLowDifference);
        liftAngle.setTargetPosition(liftAngleDifference);

    }

    public static void setPowerOfMotors(double liftHighPower, double liftLowPower, double liftAnglePower) {
        liftHigh.setPower(liftHighPower);
        liftLow.setPower(liftLowPower);
        liftAngle.setPower(liftAnglePower);
        angleDone = false;
        lowDone = false;
        highDone = false;

    }

    public static void climbPosition(){
        setMotorTargetPosition(721, -341, -7778);
        setPowerOfMotors(0.25, 0.25, 0.25);

    }

    public static void readyToHangPosition(){
        setMotorTargetPosition(11425, -11292, -8783);
        setPowerOfMotors(0.25, 0.25, 0.25);
    }


}
