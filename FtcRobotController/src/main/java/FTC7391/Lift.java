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
        MODE_MOVE_ANGLE,
        MODE_MOVE_BOTH,
        MODE_MOVE_HOOK,
        MODE_LIFT_STAGE1,
        MODE_STOP,
    }


    public static void setTestMode(TestModes mode, double power) {
        switch (mode) {
            case MODE_MOVE_HIGH:
                while(liftHigh.getCurrentPosition() > -5500)
                    liftHigh.setPower(1 * power);
                break;
            case MODE_MOVE_LOW:
                while(liftLow.getCurrentPosition() > -5500)
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
