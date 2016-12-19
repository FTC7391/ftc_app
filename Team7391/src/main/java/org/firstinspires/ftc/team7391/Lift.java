package org.firstinspires.ftc.team7391;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Arjun Verma on 10/3/15.
 */
public class Lift {

    class Stage{
        private DcMotor motor;
        private final int MAX_TICKS;
        private int MIN_TICKS;
        private double MAX_POWER = 1.0;

    }

    private static Stage[] stages;

    public static DcMotor liftHigh;
    public static DcMotor liftLow;
    public static DcMotor liftWrist;
    public static DcMotor liftShoulder;

    private static final int LIFT_HIGH_MAX = 12250;
    private static final int LIFT_HIGH_MIN = 0;

    private static final int LIFT_LOW_MAX = 12250;
    private static final int LIFT_LOW_MIN = 0;

    private static final int LIFT_SHOULDER_MAX = 3100;
    private static final int LIFT_SHOULDER_MIN = 0;

    private static final int LIFT_WRIST_MAX = 0;      // Wrist is engaged from initially up (at 0) to down position (negative)
    private static final int LIFT_WRIST_MIN = -1000;  //

    private static final double WRIST_MAX_POWER = 1.0;

    private static boolean initialized = false;

    private static boolean bLimits = true;
    private static int nLiftLoop = 0;

    public static int originalTicksHigh = 0; //to be added
    public static int originalTicksLow = 0; //to be added
    public static int originalTicksWrist = 0; //to be added
    public static int originalTicksShoulder = 0; //to be added

    public static int liftHighTargetPosition = 0;
    public static int liftLowTargetPosition = 0;
    public static int liftShoulderTargetPosition = 0;
    public static int liftWristTargetPosition = 0;

    private static boolean isRunToPosition = false;
    private static boolean isHighRunToPosition = false;
    private static boolean isLowRunToPosition = false;
    private static boolean isShoulderRunToPosition = false;
    private static boolean isWristRunToPosition = false;

    public static int getTicksLiftHigh(){return liftHigh.getCurrentPosition();}
    public static int getTicksLiftLow(){return liftLow.getCurrentPosition();}
    public static int getTicksliftShoulder(){return liftShoulder.getCurrentPosition();}
    public static int getTicksliftWrist(){return liftWrist.getCurrentPosition();}

//    public static int getOriginalTicksHigh(){return originalTicksHigh;}
//    public static int getOriginalTicksLow(){return originalTicksLow;}
//    public static int getoriginalTicksShoulder(){return originalTicksShoulder;}

    //   public Lift(){}



    public static void init (HardwareMap hardwareMap) {
        Log.i("FTC7319", "Lift: " + "init " + initialized);

        if (initialized) {
            //Bug in FTC Code.  Reinits for FORWARD when Init forthe same Opmode or different Opmode is run
            liftHigh.setDirection(DcMotor.Direction.REVERSE);
            liftLow.setDirection(DcMotor.Direction.FORWARD);
            liftShoulder.setDirection(DcMotor.Direction.FORWARD);
            liftWrist.setDirection(DcMotor.Direction.REVERSE);
            return;
        }

        initialized = true;

        for(int i=0; i<NUM_STAGES; i++){
            
        }

        liftHigh = hardwareMap.dcMotor.get("motor_high");
        liftLow = hardwareMap.dcMotor.get("motor_low");
        liftWrist = hardwareMap.dcMotor.get("motor_wrist");
        liftShoulder = hardwareMap.dcMotor.get("motor_shoulder");

        //Log.i ("FTC7391", "Lift: " + "REVERSE/FORWARD" + " H:" + liftHigh.get())
        liftHigh.setDirection(DcMotor.Direction.REVERSE);
        liftLow.setDirection(DcMotor.Direction.FORWARD);
        liftShoulder.setDirection(DcMotor.Direction.FORWARD);
        liftWrist.setDirection(DcMotor.Direction.REVERSE);

        resetEncoders();
        runUsingEncoders();
        //stop();

        originalTicksHigh = liftHigh.getCurrentPosition();
        originalTicksLow = liftLow.getCurrentPosition();
        originalTicksShoulder = liftShoulder.getCurrentPosition();
        originalTicksWrist = liftWrist.getCurrentPosition();

        //runToPosition();
       Log.i("FTC7391", "Lift: " + "INIT Complete  originalTicksWrist" + originalTicksWrist);
    }

    public static boolean isModeRunToPosition() {
        return (isRunToPosition);
    }
    public static String getStrMode() {
        if (isRunToPosition)
            return "RUN TO POSITION";
        else
            return "RUN USING ENCODERS";
    }





    // --------------- RESET ENCODERS ---------------
    protected static void resetEncoders() {
        liftHigh.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftLow.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftShoulder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftWrist.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setPowerOfMotors(0, 0, 0, 0);

        Log.i("FTC7391", "Lift: " + "RESET ENCODERS   All power= 0");

        isRunToPosition = false;
        isHighRunToPosition = false;
        isLowRunToPosition = false;
        isShoulderRunToPosition = false;
        isWristRunToPosition = false;
    }

    // --------------- RUN USING ENCODERS ---------------
    public static void runUsingEncoders(){
        Log.i("FTC7391", "Lift: " + "RUN USING ENCODERS ");
        //resetEncoders();
        highRunUsingEncoders();
        lowRunUsingEncoders();
        shoulderRunUsingEncoders();
        wristRunUsingEncoders();

        isRunToPosition = false;

        liftHigh.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftLow.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftShoulder.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftWrist.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        setPowerOfMotors(0, 0, 0, 0);

    }

    public static void highRunUsingEncoders(){
        //resetEncoders();
        liftHigh.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        isHighRunToPosition = false;
        Log.i("FTC7391", "Lift: " + "highRunUsingEncoders  flag FALSE ");
    }

    public static void lowRunUsingEncoders(){
        //resetEncoders();
        liftLow.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        isLowRunToPosition = false;
        Log.i("FTC7391", "Lift: " + "lowRunUsingEncoders  flag FALSE ");
    }

    public static void shoulderRunUsingEncoders(){
        //resetEncoders();
        liftShoulder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        isShoulderRunToPosition = false;
        Log.i("FTC7391", "Lift: " + "shoulderRunUsingEncoders  flag FALSE ");
    }

    public static void wristRunUsingEncoders(){
        //resetEncoders();
        liftWrist.setMode(DcMotor.RunMode.RUN_USING_ENCODER); //!!!!! Override, Hook should run to postion.
        isWristRunToPosition = false;
        Log.i("FTC7391", "Lift: " + "wristRunUsingEncoders  flag FALSE ");
    }


    // --------------- RUN TO POSITION ---------------
    public static void runToPosition(){
        Log.i("FTC7391", "Lift: " + "RUN TO POSITION  Target = Current All power= MAX");
       //resetEncoders();

        highRunToPosition();
        lowRunToPosition();
        shoulderRunToPosition();
        wristRunToPosition();

        isRunToPosition = true;

    }

    public static void highRunToPosition(){
        if (isHighRunToPosition == false) {
            isHighRunToPosition = true;
            liftHigh.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            liftHighTargetPosition = liftHigh.getCurrentPosition();
//            liftHigh.setTargetPosition(liftHighTargetPosition); //OUT
            liftHigh.setPower(1);
            Log.i("FTC7391", "Lift: " + "highRunToPosition"  );
        }
    }

    public static void lowRunToPosition(){
        if (isLowRunToPosition == false) {
            isLowRunToPosition = true;
            liftLow.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            liftLowTargetPosition = liftLow.getCurrentPosition();
//            liftLow.setTargetPosition(liftLowTargetPosition);  //OUT
            liftLow.setPower(1);
            Log.i("FTC7391", "Lift: " + "lowRunToPosition" );
        }
    }

    public static void shoulderRunToPosition(){
        if (isShoulderRunToPosition == false) {
            isShoulderRunToPosition = true;
            liftShoulder.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            liftShoulderTargetPosition = liftShoulder.getCurrentPosition();
//            liftShoulder.setTargetPosition(liftShoulderTargetPosition); //OUT
            liftShoulder.setPower(1);
            Log.i("FTC7391", "Lift: " + "shoulderRunToPosition" );
        }
    }

    public static void wristRunToPosition(){
        if (isWristRunToPosition == false) {
            isWristRunToPosition = true;
            liftWrist.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            liftWristTargetPosition = liftWrist.getCurrentPosition();
//            liftWrist.setTargetPosition(liftWristTargetPosition); //OUT

 //           liftWrist.setPower(WRIST_MAX_POWER);
            liftWrist.setPower(1);

            Log.i("FTC7391", "Lift: " + "wristRunToPosition" );
        }
    }


    // --------------- SET POWER ---------------
    public static void setPowerOfMotors(double liftHighPower, double liftLowPower, double liftShoulderPower, double liftWristPower) {
        Log.i("FTC7391", "Lift: " + "SET POWER " + " H:" + liftHighPower + " L:" + liftLowPower +
                                     " S:" + liftShoulderPower + " W:" + WRIST_MAX_POWER*liftWristPower);
        liftHigh.setPower(liftHighPower);
        liftLow.setPower(liftLowPower);
        liftShoulder.setPower(liftShoulderPower);
        liftWrist.setPower(WRIST_MAX_POWER * liftWristPower);

    }

    // --------------- SET MOTOR TARGET POSITION ---------------
    public static void setMotorTargetPosition(int liftHighDifference, int liftLowDifference, int liftShoulderDifference, int liftWristDifference) {
        Log.i("FTC7391", "Lift: " +"SetMotorTargetPostion");

        runToPosition();

        Log.i("FTC7391", "Lift: " + "SET TARGET POSITION" + " H:" +liftHighDifference + " L:" + liftLowDifference +
                                              " S:" + liftShoulderDifference + " W:" + liftWristDifference + " ");
        liftHigh.setTargetPosition(liftHighDifference);
        liftLow.setTargetPosition(liftLowDifference);
        liftShoulder.setTargetPosition(liftShoulderDifference);
        liftWrist.setTargetPosition(liftWristDifference);

        liftHighTargetPosition   = liftHighDifference;
        liftLowTargetPosition    = liftLowDifference;
        liftShoulderTargetPosition  = liftShoulderDifference;
        liftWristTargetPosition   = liftWristDifference;

    }


    public static boolean isDone(){
        return isAtPosition();
    }

    public static boolean isAtPosition(){

        boolean highDone = false;
        boolean lowDone = false;
        boolean angleDone = false;
        boolean hookDone = false;

        Log.v("FTC7391", "Lift: " + "High " +  liftHigh.isBusy()   + " " + liftHigh.getCurrentPosition() + " " + liftHigh.getTargetPosition()  + " " + liftHighTargetPosition);

        if (liftHighTargetPosition != liftHigh.getTargetPosition()) {
            liftHigh.setTargetPosition(liftHighTargetPosition);
        }
        //else if (!liftHigh.isBusy() && Math.abs(liftHigh.getCurrentPosition() - liftHighTargetPosition) < 50 ){
        else if (Math.abs(liftHigh.getCurrentPosition() - liftHighTargetPosition) < 50 ){
            //liftHigh.setPower(0);
            Log.v("FTC7391", "Lift " + "highDone = true");
            highDone = true;
        }

        //if (!liftLow.isBusy() && Math.abs(liftLow.getCurrentPosition() - liftLowTargetPosition) < 50){
        if (Math.abs(liftLow.getCurrentPosition() - liftLowTargetPosition) < 50){
                //liftLow.setPower(0);
                lowDone = true;
                //Log.i("FTC7391", "Lift: " + "Low Done True");
        }

        //if (!liftShoulder.isBusy() && Math.abs(liftShoulder.getCurrentPosition() - liftShoulderTargetPosition) < 50){
        if ( Math.abs(liftShoulder.getCurrentPosition() - liftShoulderTargetPosition) < 50){
                //liftHigh.setPower(0);
                angleDone = true;
                //Log.i("FTC7391", "Lift: " + "Angle Done True");
        }



        //if (!liftWrist.isBusy() && Math.abs(liftWrist.getCurrentPosition() - liftWristTargetPosition) < 50){
        if (Math.abs(liftWrist.getCurrentPosition() - liftWristTargetPosition) < 50){
                //liftWrist.setPower(0);
                hookDone = true;
                //Log.i("FTC7391", "Lift: " + "Hook Done True");
        }
       //if (nLiftLoop == 0) {
            Log.v("FTC7391", "Lift: " + "High: Busy:" + liftHigh.isBusy() + " Curr:" + liftHigh.getCurrentPosition() + " Target:" + liftHigh.getTargetPosition() + " " + liftHighTargetPosition);
            Log.v("FTC7391", "Lift: " + "Low: Busy:" + liftLow.isBusy() + " Curr:" + liftLow.getCurrentPosition() + " Target:" + liftLow.getTargetPosition()+ " " + liftLowTargetPosition);
            Log.v("FTC7391", "Lift: " + "Angle: Busy:" + liftShoulder.isBusy() + " Curr:" + liftShoulder.getCurrentPosition() + " Target:" + liftShoulder.getTargetPosition()+ " " + liftShoulderTargetPosition);
            Log.v("FTC7391", "Lift: " + "Hook: Busy:" + liftWrist.isBusy() + " Curr:" + liftWrist.getCurrentPosition() + " Target:" + liftWrist.getTargetPosition()+ " " + liftWristTargetPosition);
            //if (++nLiftLoop == 10)
             //   nLiftLoop = 0;

            Log.v("FTC7391", "Lift: " + highDone + " "  + lowDone + " " + angleDone + " " + hookDone);
        //}

        if (highDone && lowDone && angleDone && hookDone) return true;

        else return false;

    }


    // --------------- SET CURRENT MODE or POSITION ---------------

    public enum TestModes {
        MODE_MOVE_HIGH,
        MODE_MOVE_LOW,
        MODE_MOVE_SHOULDER,
        MODE_MOVE_BOTH,
        MODE_MOVE_WRIST,

        MODE_RUN_TO_POSITION,
        MODE_RUN_USING_ENCODERS,

        MODE_GOTO_INIT,
        MODE_GOTO_TEST_POSITION,
        MODE_GOTO_DRIVE_POSITION1,
        MODE_GOTO_GRABPOSITION,
        MODE_GOTO_STRAIGHT_HOOK,

         MODE_STOP
    }

    public static void runWithoutLimits(){
        bLimits = false;
    }

    /* Called during TeleOp */
    public static void setTestMode(TestModes mode, double power) {
        power = -power; //when pushing up on joystick negative power is given


        // clip the position values so that they never exceed their allowed range.
        // armPosition = Range.clip(armPosition, ARM_MIN_RANGE, ARM_MAX_RANGE);
        //clawPosition = Range.clip(clawPosition, CLAW_MIN_RANGE, CLAW_MAX_RANGE);


        if (power !=0) {
            Log.d("FTC7391", "Lift: " + "Power:" + power + "Current High" + liftHigh.getCurrentPosition() + "Current Low" + liftLow.getCurrentPosition());
        }
        switch (mode) {
            case MODE_MOVE_HIGH:

               if (bLimits && (power > 0 && liftHigh.getCurrentPosition() > LIFT_HIGH_MAX ||
                    power < 0 && liftHigh.getCurrentPosition() <= LIFT_HIGH_MIN )){
                   liftHigh.setPower(0);  //
                   Log.d("FTC7391", "Lift: " + "high power = 0" );
                   //highRunToPosition();
               }
               else{
                   //highRunUsingEncoders();
                   liftHigh.setPower(1 * power);
              }


                break;
            case MODE_MOVE_LOW:

               if (bLimits && (power > 0 && liftLow.getCurrentPosition() > LIFT_LOW_MAX ||
                    power < 0 && liftLow.getCurrentPosition() < LIFT_LOW_MIN )){
                    liftLow.setPower(0);
                    Log.d("FTC7391", "Lift: " + "low power = 0" );
                   //lowRunToPosition();
               }
               else{
                   //lowRunUsingEncoders();
                   liftLow.setPower(1 * power);    //negative power = backwards
               }
                break;
            case MODE_MOVE_SHOULDER:

                if (bLimits && (power > 0 && liftShoulder.getCurrentPosition() > LIFT_SHOULDER_MAX ||
                        power < 0 && liftShoulder.getCurrentPosition() < LIFT_SHOULDER_MIN )){
                    liftShoulder.setPower(0);
                    Log.d("FTC7391", "Lift: " + "shoulder power = 0" );
                    //shoulderRunToPosition();
                }
                else{
                    //shoulderRunUsingEncoders();
                    liftShoulder.setPower(1 * power);    //negative power = backwards
                }
                break;
            case MODE_MOVE_WRIST:
                if (bLimits && (power > 0 && liftWrist.getCurrentPosition() > LIFT_WRIST_MAX ||
                        power < 0 && liftWrist.getCurrentPosition() < LIFT_WRIST_MIN )){
                    liftWrist.setPower(0);
                    Log.d("FTC7391", "Lift: " + "wrist power = 0" );
                    //wristRunToPosition();  // Shouldn't need to do this
                }
                else{
                    //wristRunUsingEncoders();
                    liftWrist.setPower(WRIST_MAX_POWER * power);    //negative power = backwards
                    //Log.v("FTC7391", "Lift: " + "WRIST_MAX_POWER" );
                }
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

            case MODE_GOTO_INIT:
                 initPosition();
                break;
            case MODE_GOTO_TEST_POSITION:
                testPosition();
                break;
            case MODE_GOTO_DRIVE_POSITION1:
                drivePosition1();
                break;
            case MODE_GOTO_GRABPOSITION:
                 drivePosition2();
                break;
            case MODE_GOTO_STRAIGHT_HOOK:
                //wristRunToPosition();
                straightHook();

            case MODE_STOP:
                Log.i("FTC7391", "Lift: " + "stop ");
                //runToPosition();
                stop();
                break;

        }
    }


    public static void stop() {
        setMotorTargetPosition(
            liftHigh.getCurrentPosition(),
            liftLow.getCurrentPosition(),
            liftShoulder.getCurrentPosition(),
            liftWrist.getCurrentPosition()
        );
    }


    public static void initPosition(){
        Log.i("FTC7391", "Lift: " + "initPosition ");
        //runToPosition();  //Done in setMotorTargetPosition();
        setMotorTargetPosition(0, 0, 0, 0);
     }

    public static void testPosition(){
        //runToPosition();
        setMotorTargetPosition(3000, 3000, 500, -500);
     }

    public static void drivePosition1(){
        Log.i("FTC7391", "Lift: " + "drivePostion1 ");
        //runToPosition();
        setMotorTargetPosition(0, 1600, 0, 0);
     }

     public static void drivePosition2(){
        Log.i("FTC7391", "Lift: " + "drivePostion2 ");
        setMotorTargetPosition(850, 850, 550, -120);
     }

    public static void straightHook(){
        Log.i("FTC7391", "Lift: " + "straightHook ");
        liftWrist.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftWrist.setTargetPosition(921);
        liftWrist.setPower(1);
    }

    public static void stickLift(){
        Log.i("FTC7391", "Lift: " + "stickLift");
        setMotorTargetPosition(1700, 1700, -550, -120);
    }

    public static void climbPosition(){
        Log.i("FTC7391", "Lift: " + "climbPosition ");
        //setMotorTargetPosition(2215, 2228, 7778);

    }

    public static void readyToHangPosition(){
        Log.i("FTC7391", "Lift: " + "climbPosition 1, 1, 1,1");
        //setMotorTargetPosition(11425, 11292, 8783);
    }

    public static void menBasketPosition(){
        Log.i("FTC7391", "Lift: " + "menBasketPosition 8035, 6164, 4444, 1");
        setMotorTargetPosition(8035, 6164, 4444, 1);
    }

}
