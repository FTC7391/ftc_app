package FTC7391;

import android.util.Log;

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

    public static int originalTicksHigh = 0; //to be added
    public static int originalTicksLow = 0; //to be added
    public static int originalTicksHook = 0; //to be added
    public static int originalTicksAngle = 0; //to be added

    private static final int TOTAL_TICKS_PER_ROTATION = 1120;
    private static final int TOTAL_DEGREES = 360;
    private static final double DIAMETER= 1.0;
    private static final int OFFSET = (int) (TOTAL_TICKS_PER_ROTATION / (Math.PI * DIAMETER));

//    private static final double f = 1;
//    private static final double h = 1;
//    private static final double d = 1;
//    private static double x = 1;
//    private static double angle = 180 - (Math.atan(h/d) + (Math.acos(((f*f) - (x*x) + (h*h) + (d*d)) / (2*f*Math.sqrt((h*h)+(d*d))))));
//

    private static final int LIFT_HIGH_MAX = 11937;
    private static final int LIFT_HIGH_MIN = 0;

    private static final int LIFT_LOW_MAX = 10382;
    private static final int LIFT_LOW_MIN = 0;


    private static final int LIFT_ANGLE_MAX = 10000;
    private static final int LIFT_ANGLE_MIN = 0;

    private static final int HOOK_MAX = 500; //(int)(Math.round((.5 * (double)TOTAL_TICKS_PER_ROTATION)));
    private static final int HOOK_MIN = 0;


    protected static boolean initialized = false;

    private static boolean isRunToPosition = false;
    private static boolean isHighRunToPosition = false;
    private static boolean isLowRunToPosition = false;
    private static boolean isAngleRunToPosition = false;
    private static boolean isHookRunToPosition = false;

    private int currentTicks1 = liftHigh.getCurrentPosition();
    private int currentTicks2 = liftLow.getCurrentPosition();
    private int overallCurrent = currentTicks1 + currentTicks2;

    private static int nLiftLoop = 0;


    public static void init (HardwareMap hardwareMap) {
        if (initialized) return;
        initialized = true;

        liftHigh = hardwareMap.dcMotor.get("motor_high");
        liftLow = hardwareMap.dcMotor.get("motor_low");
        liftHook = hardwareMap.dcMotor.get("motor_hook");
        liftAngle = hardwareMap.dcMotor.get("motor_angle");

        //runUsingEncoders();

        originalTicksHigh = liftHigh.getCurrentPosition();
        originalTicksLow = liftLow.getCurrentPosition();
        originalTicksHook = liftHook.getCurrentPosition();
        originalTicksAngle = liftAngle.getCurrentPosition();

        liftLow.setDirection(DcMotor.Direction.REVERSE);
        liftAngle.setDirection(DcMotor.Direction.REVERSE);
       // liftHook.setDirection(DcMotor.Direction.REVERSE);

        resetEncoders();
    }



    protected static void resetEncoders(){
        liftHigh.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        liftLow.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        liftAngle.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        liftHook.setMode(DcMotorController.RunMode.RESET_ENCODERS);
      }

    public static void runToPosition(){
       //resetEncoders();
        highRunToPosition();
        lowRunToPosition();
        angleRunToPosition();
        hookRunToPosition();

//        liftHigh.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
//        liftLow.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
//        liftAngle.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
//        liftHook.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        isRunToPosition = true;
//        setPowerOfMotors(1, 1, 1, 1);
    }

    public static void highRunToPosition(){
        if (isHighRunToPosition == false) {
            isHighRunToPosition = true;
            liftHigh.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
            liftHigh.setPower(1);
            liftHigh.setTargetPosition(liftHigh.getCurrentPosition());
        }
    }

    public static void lowRunToPosition(){
        if (isLowRunToPosition == false) {
            isLowRunToPosition = true;
            liftLow.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
            liftLow.setPower(1);
            liftLow.setTargetPosition(liftLow.getCurrentPosition());
        }
    }

    public static void angleRunToPosition(){
        if (isAngleRunToPosition == false) {
            isAngleRunToPosition = true;
            liftAngle.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
            liftAngle.setPower(1);
            liftAngle.setTargetPosition(liftAngle.getCurrentPosition());
        }
    }

    public static void hookRunToPosition(){
        if (isHookRunToPosition == false) {
            isHookRunToPosition = true;
            liftHook.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
            liftHook.setPower(1);
            liftHook.setTargetPosition(liftHook.getCurrentPosition());
        }
    }

    public static void runUsingEncoders(){
        //resetEncoders();
        liftHigh.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        liftLow.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        liftAngle.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        liftHook.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS); //!!!!! Override, Hook should run to postion.
        isRunToPosition = false;
        isHighRunToPosition = false;
        isLowRunToPosition = false;
        isAngleRunToPosition = false;
        isHookRunToPosition = false;

    }

    public static void highRunUsingEncoders(){
        //resetEncoders();
        liftHigh.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        isHighRunToPosition = false;
    }

    public static void lowRunUsingEncoders(){
        //resetEncoders();
        liftLow.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        isLowRunToPosition = false;
    }

    public static void angleRunUsingEncoders(){
        //resetEncoders();
        liftAngle.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        isAngleRunToPosition = false;
    }

    public static void hookRunUsingEncoders(){
        //resetEncoders();
        liftHook.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS); //!!!!! Override, Hook should run to postion.
        isHookRunToPosition = false;
    }


    public static void runWithoutEncoders(){
        liftHigh.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        liftLow.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        liftAngle.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        liftHook.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        isRunToPosition = false;
    }


    public static int getTicksLiftHigh(){return liftHigh.getCurrentPosition();}
    public static int getTicksLiftLow(){return liftLow.getCurrentPosition();}
    public static int getTicksLiftAngle(){return liftAngle.getCurrentPosition();}
    public static int getTicksLiftHook(){return liftHook.getCurrentPosition();}

//    public static int getOriginalTicksHigh(){return originalTicksHigh;}
//    public static int getOriginalTicksLow(){return originalTicksLow;}
//    public static int getOriginalTicksAngle(){return originalTicksAngle;}




    public Lift(){}




    public static boolean isDone(){
        return isAtPosition();
    }

    public static boolean isAtPosition(){

        boolean highDone = false;
        boolean lowDone = false;
        boolean angleDone = false;
        boolean hookDone = false;

        Log.i("Auto", "High " +  liftHigh.isBusy()   + " " + liftHigh.getCurrentPosition() + " " + liftHigh.getTargetPosition());

        if (!liftHigh.isBusy() && Math.abs(liftHigh.getCurrentPosition() - liftHigh.getTargetPosition()) < 50 ){
                //liftHigh.setPower(0);
                highDone = true;
        }

        if (!liftLow.isBusy() && Math.abs(liftLow.getCurrentPosition() - liftLow.getTargetPosition()) < 50){
                //liftLow.setPower(0);
                lowDone = true;
                //Log.d("Auto", "Low Done True");
        }

        if (!liftAngle.isBusy() && Math.abs(liftAngle.getCurrentPosition() - liftAngle.getTargetPosition()) < 50){
                //liftHigh.setPower(0);
                angleDone = true;
                //Log.d("Auto", "Angle Done True");
        }



        if (!liftHook.isBusy() && Math.abs(liftHook.getCurrentPosition() - liftHook.getTargetPosition()) < 50){
                //liftHook.setPower(0);
                hookDone = true;
                //Log.d("Auto", "Hook Done True");
        }
        if (nLiftLoop == 0) {
            Log.d("Auto", "High: Busy:" + liftHigh.isBusy() + " Curr:" + liftHigh.getCurrentPosition() + " Target:" + liftHigh.getTargetPosition());
            Log.d("Auto", "Low: Busy:" + liftLow.isBusy() + " Curr:" + liftLow.getCurrentPosition() + " Target:" + liftLow.getTargetPosition());
            Log.d("Auto", "Angle: Busy:" + liftAngle.isBusy() + " Curr:" + liftAngle.getCurrentPosition() + " Target:" + liftAngle.getTargetPosition());
            Log.d("Auto", "Hook: Busy:" + liftHook.isBusy() + " Curr:" + liftHook.getCurrentPosition() + " Target:" + liftHook.getTargetPosition());
            if (++nLiftLoop == 10)
                nLiftLoop = 0;
        }

        if (highDone && lowDone && angleDone && hookDone) return true;                 

        else return false;

    }

    public static boolean isEncoderDone() {
        boolean highDone = false;
        boolean lowDone = false;
        boolean angleDone = false;
        boolean hookDone = false;

        if(liftHigh.getPower() > 0) {
            if (liftHigh.getTargetPosition() <= liftHigh.getCurrentPosition()) {

                   liftHigh.setPower(0); highDone = true;
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


    public static boolean isHookAtPosition(){

         boolean hookDone = false;

        if (!liftHook.isBusy() && Math.abs(liftHook.getCurrentPosition() - liftHook.getTargetPosition()) < 50){
            //liftHook.setPower(0);
            hookDone = true;
            Log.d("Auto", "Hook Done True");
        }
        Log.d("Auto", "Hook: Busy:" + liftHook.isBusy() + " Curr:" + liftHook.getCurrentPosition() + " Target:" + liftHook.getTargetPosition());

        if (hookDone) return true;

        else return false;

    }



    public static void setMotorTargetPosition(int liftHighDifference, int liftLowDifference, int liftAngleDifference, int liftHookDifference) {
        if (isRunToPosition == false)
            runToPosition();

        setPowerOfMotors(1,1,1,1);


        Log.i("Auto", "setMotorTargetPostion" + liftHighDifference + " " + liftLowDifference +" " +  liftAngleDifference  +" " + liftHookDifference);

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
        liftHook.setTargetPosition(liftHookDifference);




    }

    public static void setPowerOfMotors(double liftHighPower, double liftLowPower, double liftAnglePower, double liftHookPower) {
        liftHigh.setPower(liftHighPower);
        liftLow.setPower(liftLowPower);
        liftAngle.setPower(liftAnglePower);
        liftHook.setPower(liftHookPower);

    }




    public enum TestModes {
        MODE_RUN_TO_POSITION,
        MODE_RUN_USING_ENCODERS,

        MODE_GOTO_DRIVE_POSITION1,
        MODE_GOTO_DRIVE_POSITION2,
        MODE_GOTO_STRAIGHT_HOOK,

        MODE_STOP,

        MODE_MOVE_HIGH,
        MODE_MOVE_LOW,
        MODE_MOVE_ANGLE,
        MODE_MOVE_BOTH,
        MODE_MOVE_HOOK,

    }

    /* Called during TeleOp */
    public static void setTestMode(TestModes mode, double power) {
        switch (mode) {
            case MODE_MOVE_HIGH:

//               if (power > 0 && liftHigh.getCurrentPosition() > LIFT_HIGH_MAX ||
//                    power < 0 && liftHigh.getCurrentPosition() < LIFT_HIGH_MIN ){
//
//                   highRunToPosition();
//               }
//               else{
                   highRunUsingEncoders();
                   liftHigh.setPower(1 * power);
//               }

                break;
            case MODE_MOVE_LOW:
//
//               if (power > 0 && liftHigh.getCurrentPosition() > LIFT_LOW_MAX ||
//                    power < 0 && liftHigh.getCurrentPosition() < LIFT_LOW_MIN ){
//
//                   lowRunToPosition();
//               }
//               else{
                   lowRunUsingEncoders();
                   liftLow.setPower(1 * power);    //negative power = backwards
//               }
                break;
            case MODE_MOVE_ANGLE:
//
//               if (power > 0 && liftHigh.getCurrentPosition() > LIFT_ANGLE_MAX ||
//                       power < 0 && liftHigh.getCurrentPosition() < LIFT_ANGLE_MIN ){
//
//                     angleRunToPosition();
//               }
//               else {
                   angleRunUsingEncoders();
                   liftAngle.setPower(1 * power);
//               }
                break;
            case MODE_MOVE_HOOK:

              //  if (power > 0 && liftHook.getCurrentPosition() > HOOK_MAX)
               //     hookRunToPosition();
              //  else {
                    hookRunUsingEncoders();
                    liftHook.setPower(1 * power);
                //}

                break;

            case MODE_MOVE_BOTH:
                if (isRunToPosition == true)
                    runUsingEncoders();
//                if (power > 0 && liftHigh.getCurrentPosition() < LIFT_HIGH_MAX ||
//                    power < 0 && liftHigh.getCurrentPosition() > LIFT_HIGH_MIN )
                liftHigh.setPower(1 * power);
//                if (power > 0 && liftHigh.getCurrentPosition() < LIFT_LOW_MAX ||
//                    power < 0 && liftHigh.getCurrentPosition() > LIFT_LOW_MIN )
                liftLow.setPower(1 * power);    //negative power = backwards
                break;

            case MODE_RUN_TO_POSITION:
                runToPosition();
                break;
            case MODE_RUN_USING_ENCODERS:
                //if (isRunToPosition == true)
                    runUsingEncoders();
                break;
            case MODE_GOTO_DRIVE_POSITION1:
                //if (isRunToPosition == false)
                    runToPosition();
                drivePosition1();
                break;
            case MODE_GOTO_DRIVE_POSITION2:
                    runToPosition();
                drivePosition2();
                break;
            case MODE_GOTO_STRAIGHT_HOOK:
                //hookRunToPosition();
                straightHook();

            case MODE_STOP:
                //if (isRunToPosition == false)
                    runToPosition();
                setMotorTargetPosition(liftHigh.getCurrentPosition(),
                        liftLow.getCurrentPosition(),
                        liftHook.getCurrentPosition(),
                        liftAngle.getCurrentPosition());
                break;

        }
    }


    public static void testPosition(){
        setMotorTargetPosition(100, 100, 100, 100);
        setPowerOfMotors(1, 1, 1, 1);
    }

    public static void drivePosition1(){
        Log.i("Auto", "drivePostion1 ");
        setMotorTargetPosition(1350, 1350, 0, 0);
        setPowerOfMotors(1, 1, 1, 1);
    }

    public static void straightHook(){
        Log.i("Auto", "straightHook ");
        liftHook.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        liftHook.setTargetPosition(921);
        liftHook.setPower(1);
    }

    public static void drivePosition2(){
        Log.i("Auto", "drivePostion2 ");
        setMotorTargetPosition(1350, 1350, -550, -120);
        setPowerOfMotors(1, 1, 1, 1);
    }

    public static void climbPosition(){
        Log.i("Auto", "climbPosition ");
        //setMotorTargetPosition(2215, 2228, 7778);
        setPowerOfMotors(1, 1, 1, 1);

    }

    public static void readyToHangPosition(){
        Log.i("Auto", "climbPosition 1, 1, 1,1");
        //setMotorTargetPosition(11425, 11292, 8783);
        setPowerOfMotors(1, 1, 1, 1);
    }

    public static void menBasketPosition(){
        Log.i("Auto", "menBasketPosition 8035, 6164, 4444, 1");
        setMotorTargetPosition(8035, 6164, 4444, 1);
        setPowerOfMotors(1, 1, 1, 1);
    }


//    private static int driveModeTicksHigh = 1575; //to be added
//    private static int driveModeTicksLow = -657;


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


}
