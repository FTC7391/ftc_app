package org.firstinspires.ftc.team7391;

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
    public static DcMotor liftWrist;
    public static DcMotor liftShoulder;

//    liftHigh.setDirection(DcMotor.Direction.REVERSE);
//    liftShoulder.setDirection(DcMotor.Direction.REVERSE);
//    liftWrist.setDirection(DcMotor.Direction.REVERSE);



    public static int originalTicksHigh = 0; //to be added
    public static int originalTicksLow = 0; //to be added
    public static int originalTicksWrist = 0; //to be added
    public static int originalTicksShoulder = 0; //to be added

    public static int liftHighTargetPosition = 0;
    public static int liftLowTargetPosition = 0;
    public static int liftShoulderTargetPosition = 0;
    public static int liftWristTargetPosition = 0;



    private static final int TOTAL_TICKS_PER_ROTATION = 1680;
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


    private static final int LIFT_SHOULDER_MAX = 10000;
    private static final int LIFT_SHOULDER_MIN = 0;

    private static final int LIFT_ELBOW_MAX = 500; //(int)(Math.round((.5 * (double)TOTAL_TICKS_PER_ROTATION)));
    private static final int LIFT_ELBOW_MIN = 0;


    protected static boolean initialized = false;

    private static boolean isRunToPosition = false;
    private static boolean isHighRunToPosition = false;
    private static boolean isLowRunToPosition = false;
    private static boolean isShoulderRunToPosition = false;
    private static boolean isWristRunToPosition = false;

    private int currentTicks1 = liftHigh.getCurrentPosition();
    private int currentTicks2 = liftLow.getCurrentPosition();
    private int overallCurrent = currentTicks1 + currentTicks2;

    private static int nLiftLoop = 0;


    public static void init (HardwareMap hardwareMap) {
        if (initialized) return;
        initialized = true;

        liftHigh = hardwareMap.dcMotor.get("motor_high");
        liftLow = hardwareMap.dcMotor.get("motor_low");
        liftWrist = hardwareMap.dcMotor.get("motor_wrist");
        liftShoulder = hardwareMap.dcMotor.get("motor_shoulder");

        //runUsingEncoders();

        originalTicksHigh = liftHigh.getCurrentPosition();
        originalTicksLow = liftLow.getCurrentPosition();
        originalTicksWrist = liftWrist.getCurrentPosition();
        originalTicksShoulder = liftShoulder.getCurrentPosition();

        liftLow.setDirection(DcMotor.Direction.REVERSE);
        liftShoulder.setDirection(DcMotor.Direction.REVERSE);
       // liftWrist.setDirection(DcMotor.Direction.REVERSE);

        resetEncoders();
    }



    protected static void resetEncoders(){
        liftHigh.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftLow.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftShoulder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftWrist.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      }

    public static void runToPosition(){
       //resetEncoders();
        highRunToPosition();
        lowRunToPosition();
        shoulderRunToPosition();
        wristRunToPosition();
        liftHigh.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftLow.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftShoulder.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftWrist.setMode(DcMotor.RunMode.RUN_TO_POSITION);

//        liftHigh.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
//        liftLow.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
//        liftShoulder.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
//        liftWrist.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        isRunToPosition = true;
        isHighRunToPosition = true;
        isLowRunToPosition = true;
        isShoulderRunToPosition = true;
        isWristRunToPosition = true;


        setPowerOfMotors(1, 1, 1, 1);
    }

    public static void highRunToPosition(){
        if (isHighRunToPosition == false) {
            isHighRunToPosition = true;
            liftHigh.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            liftHighTargetPosition = liftHigh.getCurrentPosition();
            liftHigh.setTargetPosition(liftHighTargetPosition); //OUT
            liftHigh.setPower(1);
            Log.d("Auto", "highRunToPosition" + liftHigh.getCurrentPosition() );
        }
    }

    public static void lowRunToPosition(){
        if (isLowRunToPosition == false) {
            isLowRunToPosition = true;
            liftLow.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            liftLowTargetPosition = liftLow.getCurrentPosition();
            liftLow.setTargetPosition(liftLowTargetPosition);  //OUT
            liftLow.setPower(1);
            Log.d("Auto", "lowRunToPosition" + liftLow.getCurrentPosition());
        }
    }

    public static void shoulderRunToPosition(){
        if (isShoulderRunToPosition == false) {
            isShoulderRunToPosition = true;
            liftShoulder.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            liftShoulderTargetPosition = liftShoulder.getCurrentPosition();
            liftShoulder.setTargetPosition(liftShoulderTargetPosition); //OUT
            liftShoulder.setPower(1);
            Log.d("Auto", "shoulderRunToPosition" + liftShoulder.getCurrentPosition());
        }
    }

    public static void wristRunToPosition(){
        if (isWristRunToPosition == false) {
            isWristRunToPosition = true;
            liftWrist.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            liftWristTargetPosition = liftWrist.getCurrentPosition();
            liftWrist.setTargetPosition(liftWristTargetPosition); //OUT
            liftWrist.setPower(1);
            Log.d("Auto", "wristRunToPosition" + liftWrist.getCurrentPosition());
        }
    }

    public static void runUsingEncoders(){
        //resetEncoders();
        liftHigh.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftLow.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftShoulder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftWrist.setMode(DcMotor.RunMode.RUN_USING_ENCODER); //!!!!! Override, Hook should run to postion.
        isRunToPosition = false;
        isHighRunToPosition = false;
        isLowRunToPosition = false;
        isShoulderRunToPosition = false;
        isWristRunToPosition = false;

    }

    public static void highRunUsingEncoders(){
        //resetEncoders();
        liftHigh.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        isHighRunToPosition = false;
    }

    public static void lowRunUsingEncoders(){
        //resetEncoders();
        liftLow.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        isLowRunToPosition = false;
    }

    public static void angleRunUsingEncoders(){
        //resetEncoders();
        liftShoulder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        isShoulderRunToPosition = false;
    }

    public static void hookRunUsingEncoders(){
        //resetEncoders();
        liftWrist.setMode(DcMotor.RunMode.RUN_USING_ENCODER); //!!!!! Override, Hook should run to postion.
        isWristRunToPosition = false;
    }


    public static void runWithoutEncoders(){
        liftHigh.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftLow.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftShoulder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftWrist.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        isRunToPosition = false;
    }


    public static int getTicksLiftHigh(){return liftHigh.getCurrentPosition();}
    public static int getTicksLiftLow(){return liftLow.getCurrentPosition();}
    public static int getTicksliftShoulder(){return liftShoulder.getCurrentPosition();}
    public static int getTicksliftWrist(){return liftWrist.getCurrentPosition();}

//    public static int getOriginalTicksHigh(){return originalTicksHigh;}
//    public static int getOriginalTicksLow(){return originalTicksLow;}
//    public static int getoriginalTicksShoulder(){return originalTicksShoulder;}




    public Lift(){}




    public static boolean isDone(){
        return isAtPosition();
    }

    public static boolean isAtPosition(){

        boolean highDone = false;
        boolean lowDone = false;
        boolean angleDone = false;
        boolean hookDone = false;

        Log.v("Auto", "High " +  liftHigh.isBusy()   + " " + liftHigh.getCurrentPosition() + " " + liftHigh.getTargetPosition()  + " " + liftHighTargetPosition);

        if (liftHighTargetPosition != liftHigh.getTargetPosition()) {
            liftHigh.setTargetPosition(liftHighTargetPosition);
        }
        else if (!liftHigh.isBusy() && Math.abs(liftHigh.getCurrentPosition() - liftHighTargetPosition) < 50 ){
                //liftHigh.setPower(0);
                highDone = true;
        }

        if (!liftLow.isBusy() && Math.abs(liftLow.getCurrentPosition() - liftLowTargetPosition) < 50){
                //liftLow.setPower(0);
                lowDone = true;
                //Log.d("Auto", "Low Done True");
        }

        if (!liftShoulder.isBusy() && Math.abs(liftShoulder.getCurrentPosition() - liftShoulderTargetPosition) < 50){
                //liftHigh.setPower(0);
                angleDone = true;
                //Log.d("Auto", "Angle Done True");
        }



        if (!liftWrist.isBusy() && Math.abs(liftWrist.getCurrentPosition() - liftWristTargetPosition) < 50){
                //liftWrist.setPower(0);
                hookDone = true;
                //Log.d("Auto", "Hook Done True");
        }
        if (nLiftLoop == 0) {
            Log.d("Auto", "High: Busy:" + liftHigh.isBusy() + " Curr:" + liftHigh.getCurrentPosition() + " Target:" + liftHigh.getTargetPosition() + " " + liftHighTargetPosition);
            Log.d("Auto", "Low: Busy:" + liftLow.isBusy() + " Curr:" + liftLow.getCurrentPosition() + " Target:" + liftLow.getTargetPosition()+ " " + liftLowTargetPosition);
            Log.d("Auto", "Angle: Busy:" + liftShoulder.isBusy() + " Curr:" + liftShoulder.getCurrentPosition() + " Target:" + liftShoulder.getTargetPosition()+ " " + liftShoulderTargetPosition);
            Log.d("Auto", "Hook: Busy:" + liftWrist.isBusy() + " Curr:" + liftWrist.getCurrentPosition() + " Target:" + liftWrist.getTargetPosition()+ " " + liftWristTargetPosition);
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



        if(liftShoulder.getPower() > 0) {
            if(liftShoulder.getTargetPosition() >= liftShoulder.getCurrentPosition()) {
                liftShoulder.setPower(0);
                angleDone = true;
            }

        }
        else if(liftShoulder.getPower() < 0) {
            if(liftShoulder.getTargetPosition() <= liftShoulder.getCurrentPosition()) {
                liftShoulder.setPower(0);
                angleDone = true;
            }
        }


        if(angleDone && lowDone && highDone)
            return true;

        return false;

    }


    public static boolean isHookAtPosition(){

         boolean hookDone = false;

        if (!liftWrist.isBusy() && Math.abs(liftWrist.getCurrentPosition() - liftWrist.getTargetPosition()) < 50){
            //liftWrist.setPower(0);
            hookDone = true;
            Log.d("Auto", "Hook Done True");
        }
        Log.d("Auto", "Hook: Busy:" + liftWrist.isBusy() + " Curr:" + liftWrist.getCurrentPosition() + " Target:" + liftWrist.getTargetPosition());

        if (hookDone) return true;

        else return false;

    }



    public static void setMotorTargetPosition(int liftHighDifference, int liftLowDifference, int liftShoulderDifference, int liftWristDifference) {
        Log.d("Auto","SetMotorTargetPostion");
        //if (isRunToPosition == false)
            runToPosition();  //OUT

        setPowerOfMotors(1,1,1,1);


        Log.i("Auto", "setMotorTargetPostion" + liftHighDifference + " " + liftLowDifference +" " +  liftShoulderDifference  +" " + liftWristDifference);

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
//        if(liftShoulder.getCurrentPosition() + liftShoulderDifference < 0)
//            liftShoulder.setTargetPosition(liftShoulder.getCurrentPosition() + liftShoulderDifference);
//        else
//            liftShoulder.setTargetPosition(liftShoulder.getCurrentPosition());

        Log.d("Auto", "SetMotorTargetPostion" + liftHighDifference + " " + liftLowDifference + " " + liftShoulderDifference + " " + liftWristDifference + " ");
        liftHigh.setTargetPosition(liftHighDifference);
        liftLow.setTargetPosition(liftLowDifference);
        liftShoulder.setTargetPosition(liftShoulderDifference);
        liftWrist.setTargetPosition(liftWristDifference);

        liftHighTargetPosition   = liftHighDifference;
        liftLowTargetPosition    = liftLowDifference;
        liftShoulderTargetPosition  = liftShoulderDifference;
        liftWristTargetPosition   = liftWristDifference;

    }

    public static void setPowerOfMotors(double liftHighPower, double liftLowPower, double liftShoulderPower, double liftWristPower) {
        liftHigh.setPower(liftHighPower);
        liftLow.setPower(liftLowPower);
        liftShoulder.setPower(liftShoulderPower);
        liftWrist.setPower(liftWristPower);

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
//               if (power > 0 && liftHigh.getCurrentPosition() > LIFT_SHOULDER_MAX ||
//                       power < 0 && liftHigh.getCurrentPosition() < LIFT_SHOULDER_MIN ){
//
//                     shoulderRunToPosition();
//               }
//               else {
                   angleRunUsingEncoders();
                   liftShoulder.setPower(1 * power);
//               }
                break;
            case MODE_MOVE_HOOK:

              //  if (power > 0 && liftWrist.getCurrentPosition() > LIFT_ELBOW_MAX)
               //     wristRunToPosition();
              //  else {
                    hookRunUsingEncoders();
                    liftWrist.setPower(1 * power);
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
                //wristRunToPosition();
                straightHook();

            case MODE_STOP:
                //if (isRunToPosition == false)
                    runToPosition();
                setMotorTargetPosition(liftHigh.getCurrentPosition(),
                        liftLow.getCurrentPosition(),
                        liftWrist.getCurrentPosition(),
                        liftShoulder.getCurrentPosition());
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
        liftWrist.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftWrist.setTargetPosition(921);
        liftWrist.setPower(1);
    }

    public static void drivePosition2(){
        Log.i("Auto", "drivePostion2 ");
        setMotorTargetPosition(1350, 1350, -550, -120);
        setPowerOfMotors(1, 1, 1, 1);
    }

    public static void stickLift(){
        Log.i("Auto", "stickLift");
        setMotorTargetPosition(1700, 1700, -550, -120);
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
