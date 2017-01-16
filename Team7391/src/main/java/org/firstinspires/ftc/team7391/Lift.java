package org.firstinspires.ftc.team7391;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Arjun Verma on 10/3/15.
 */
public class Lift {

    private static class Stage{
        private DcMotor motor;
        private int MIN_TICKS;
        private int MAX_TICKS;
        private double MAX_POWER = 1.0;

        private int originalTicks = 0;
        private int targetPosition = 0;
        private boolean isRunToPosition = false;

        private double currentPower;

        private String name = "";

        public Stage(String name, int min, int max){
            this(name, min, max, 1.0);
        }

        public Stage(String name, int min, int max, double power){
            this.name = name;
            MIN_TICKS = min;
            MAX_TICKS = max;
            MAX_POWER = power;
        }
    }

    private static Stage[] stages = {     //what should these numbers be?
            new Stage("stage0", 0, 12500),    //low
            new Stage("stage1", 0, 12500),    //mid
            new Stage("stage2", 0, 12500),   //high
            new Stage("stage3", 0, 6250),
            new Stage("wrist", 0, 1000, 1.0)    //wrist
    };

    private static final int NUM_STAGES = 5;

    private static boolean initialized = false;

    private static boolean bLimits = true;
    private static int nLiftLoop = 0;

    private static boolean isRunToPosition = false;


    public static int getTicksLiftLow(){return stages[0].motor.getCurrentPosition();}
    public static int getTicksLiftMid(){return stages[1].motor.getCurrentPosition();}
    public static int getTicksLiftHigh(){return stages[2].motor.getCurrentPosition();}
    public static int getTicksLiftTop(){return stages[3].motor.getCurrentPosition();}
    public static int getTicksLiftWrist(){return stages[4].motor.getCurrentPosition();}




    public static int getOriginalTicksLow(){return stages[0].originalTicks;}
    public static int getOriginalTicksMid(){return stages[1].originalTicks;}
    public static int getOriginalTicksHigh(){return stages[2].originalTicks;}
    public static int getOriginalTicksTop(){return stages[3].originalTicks;}
    public static int getOriginalTicksWrist(){return stages[4].originalTicks;}



//    public static int getgetOriginalTicksHigh(){return getOriginalTicksHigh;}
//    public static int getOriginalTicksLow(){return originalTicksLow;}
//    public static int getoriginalTicksMid(){return originalTicksMid;}

    //   public Lift(){}



    public static void init (HardwareMap hardwareMap) {
        Log.i("FTC7319", "Lift: " + "init " + initialized);

        if (initialized) {
            //Bug in FTC Code.  Reinits for FORWARD when Init forthe same Opmode or different Opmode is run
            //what should these directions be set to?

            setMotorDirection();
            return;
        }

        initialized = true;

        for(int i=0; i<NUM_STAGES; i++){
            //stages[i] = new Stage();
        }

        stages[0].motor = hardwareMap.dcMotor.get("stage0");
        stages[1].motor = hardwareMap.dcMotor.get("stage1");
        stages[2].motor = hardwareMap.dcMotor.get("stage2");
        stages[3].motor = hardwareMap.dcMotor.get("stage3");
        stages[4].motor = hardwareMap.dcMotor.get("stage4");

        //Log.i ("FTC7391", "Lift: " + "REVERSE/FORWARD" + " H:" + liftHigh.get())
        setMotorDirection();


        resetEncoders();
        runUsingEncoders();
        //stop();

        for( int i=0;i<NUM_STAGES;i++)
            stages[i].originalTicks = stages[i].motor.getCurrentPosition();

        //runToPosition();
       Log.i("FTC7391", "Lift: " + "INIT Complete  originalTicksWrist" + stages[4].originalTicks);
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

    // --------------- SET MOTOR DIRECTION ---------------

    public static void setMotorDirection(){
        stages[0].motor.setDirection(DcMotor.Direction.FORWARD);
        stages[1].motor.setDirection(DcMotor.Direction.REVERSE);
        stages[2].motor.setDirection(DcMotor.Direction.FORWARD);
        stages[3].motor.setDirection(DcMotor.Direction.FORWARD);
        stages[4].motor.setDirection(DcMotor.Direction.REVERSE);

    }



    // --------------- RESET ENCODERS ---------------
    protected static void resetEncoders() {
        for(int i=0; i<NUM_STAGES; i++)
            stages[i].motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        setPowerOfMotors(0, 0, 0, 0, 0);

        Log.i("FTC7391", "Lift: " + "RESET ENCODERS   All power= 0");

        isRunToPosition = false;
        for(int i=0; i<NUM_STAGES; i++)
            stages[i].isRunToPosition = false;

    }

    // --------------- RUN USING ENCODERS ---------------
    public static void runUsingEncoders(){
        Log.i("FTC7391", "Lift: " + "RUN USING ENCODERS ");
        //resetEncoders();

        for(int i=0; i<NUM_STAGES; i++)
            runUsingEncoders(i);

        isRunToPosition = false;
        for(int i=0; i<NUM_STAGES; i++)
            stages[i].motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        setPowerOfMotors(0, 0, 0, 0, 0);

    }

    public static void runUsingEncoders(int stageNum){
        //resetEncoders();
        stages[stageNum].motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        stages[stageNum].isRunToPosition = false;
        Log.i("FTC7391", "Lift: " + stages[stageNum].name + "RunUsingEncoders  flag FALSE ");
    }

    // --------------- RUN TO POSITION ---------------
    public static void runToPosition(){
        Log.i("FTC7391", "Lift: " + "RUN TO POSITION  Target = Current All power= MAX");
       //resetEncoders();

        for(int i=0; i<NUM_STAGES; i++)
            runToPosition(i);


        isRunToPosition = true;

    }

    public static void runToPosition(int stageNum){
        if (stages[stageNum].isRunToPosition == false) {
            stages[stageNum].isRunToPosition = true;
            stages[stageNum].motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            liftHighTargetPosition = liftHigh.getCurrentPosition();
//            liftHigh.setTargetPosition(liftHighTargetPosition); //OUT
            stages[stageNum].motor.setPower(1);
            Log.i("FTC7391", "Lift: " + stages[stageNum].name + "RunToPosition"  );
        }
    }

    // --------------- SET POWER ---------------
    public static void setPowerOfMotors(double liftTopPower, double liftHighPower, double liftLowPower, double liftMidPower, double liftWristPower) {
        Log.i("FTC7391", "Lift: " + "SET POWER " + " L:" + liftLowPower + " M:" + liftMidPower + " H:" + liftHighPower +
                                     " M:" + liftMidPower + " W:" + stages[4].MAX_POWER*liftWristPower);
        stages[0].motor.setPower(liftLowPower);
        stages[1].motor.setPower(liftMidPower);
        stages[2].motor.setPower(liftHighPower);
        stages[3].motor.setPower(liftTopPower);
        stages[4].motor.setPower(stages[4].MAX_POWER * liftWristPower);

    }

    // --------------- SET MOTOR TARGET POSITION ---------------
    public static void setMotorTargetPosition(int liftLowDifference, int liftMidDifference, int liftHighDifference, int liftTopDifference, int liftWristDifference) {
        Log.i("FTC7391", "Lift: " +"SetMotorTargetPostion");

        runToPosition();

        Log.i("FTC7391", "Lift: " + "SET TARGET POSITION" + " H:" +liftHighDifference + " L:" + liftLowDifference +
                                              " S:" + liftMidDifference + " W:" + liftWristDifference + " ");
        stages[0].motor.setTargetPosition(liftLowDifference);
        stages[1].motor.setTargetPosition(liftMidDifference);
        stages[2].motor.setTargetPosition(liftHighDifference);
        stages[3].motor.setTargetPosition(liftTopDifference);
        stages[4].motor.setTargetPosition(liftWristDifference);

        stages[0].targetPosition    = liftLowDifference;
        stages[1].targetPosition  = liftMidDifference;
        stages[2].targetPosition   = liftHighDifference;
        stages[3].targetPosition   = liftTopDifference;
        stages[4].targetPosition   = liftWristDifference;

    }


    public static boolean isDone(){
        return isAtPosition();
    }

    public static boolean isAtPosition(){

        boolean highDone = false;
        boolean lowDone = false;
        boolean midDone = false;
        boolean wristDone = false;
        boolean topDone = false;

        Log.v("FTC7391", "Lift: " + "High " +  stages[2].motor.isBusy()   + " " + stages[2].motor.getCurrentPosition() + " " + stages[2].motor.getTargetPosition()  + " " + stages[2].targetPosition);

        //if (!liftLow.isBusy() && Math.abs(liftLow.getCurrentPosition() - liftLowTargetPosition) < 50){
        if (Math.abs(stages[0].motor.getCurrentPosition() - stages[0].targetPosition) < 50){
            //stages[0].motor.setPower(0);
            lowDone = true;
            //Log.i("FTC7391", "Lift: " + "Low Done True");
        }

        //if (!liftMid.isBusy() && Math.abs(liftMid.getCurrentPosition() - liftMidTargetPosition) < 50){
        if ( Math.abs(stages[1].motor.getCurrentPosition() - stages[1].targetPosition) < 50){
            //liftHigh.setPower(0);
            midDone = true;
            //Log.i("FTC7391", "Lift: " + "Mid Done True");
        }

        if (stages[2].targetPosition != stages[2].motor.getTargetPosition()) {
            stages[2].motor.setTargetPosition(stages[2].targetPosition);
        }
        //else if (!liftHigh.isBusy() && Math.abs(liftHigh.getCurrentPosition() - liftHighTargetPosition) < 50 ){
        else if (Math.abs(stages[2].motor.getCurrentPosition() - stages[2].targetPosition) < 50 ){
            //liftHigh.setPower(0);
            Log.v("FTC7391", "Lift " + "highDone = true");
            highDone = true;
        }

        //if (!liftWrist.isBusy() && Math.abs(liftWrist.getCurrentPosition() - liftWristTargetPosition) < 50){
        if (Math.abs(stages[3].motor.getCurrentPosition() - stages[3].targetPosition) < 50){
            //stages[3].motor.setPower(0);
            topDone = true;
            //Log.i("FTC7391", "Lift: " + "Wrist Done True");
        }

        if (Math.abs(stages[4].motor.getCurrentPosition() - stages[4].targetPosition) < 50){
            //stages[0].motor.setPower(0);
            wristDone = true;
            //Log.i("FTC7391", "Lift: " + "Low Done True");
        }

       //if (nLiftLoop == 0) {
            Log.v("FTC7391", "Lift: " + "High: Busy:" + stages[2].motor.isBusy() + " Curr:" + stages[2].motor.getCurrentPosition() + " Target:" + stages[2].motor.getTargetPosition() + " " + stages[2].targetPosition);
            Log.v("FTC7391", "Lift: " + "Low: Busy:" + stages[0].motor.isBusy() + " Curr:" + stages[0].motor.getCurrentPosition() + " Target:" + stages[0].motor.getTargetPosition()+ " " + stages[0].targetPosition);
            Log.v("FTC7391", "Lift: " + "Angle: Busy:" + stages[1].motor.isBusy() + " Curr:" + stages[1].motor.getCurrentPosition() + " Target:" + stages[1].motor.getTargetPosition()+ " " + stages[1].targetPosition);
            Log.v("FTC7391", "Lift: " + "Hook: Busy:" + stages[3].motor.isBusy() + " Curr:" + stages[3].motor.getCurrentPosition() + " Target:" + stages[3].motor.getTargetPosition()+ " " + stages[3].targetPosition);
            //if (++nLiftLoop == 10)
             //   nLiftLoop = 0;

            Log.v("FTC7391", "Lift: " + highDone + " "  + lowDone + " " + midDone + " " + topDone + " " + wristDone);
        //}

        if (highDone && lowDone && midDone && wristDone) return true;

        else return false;

    }


    // --------------- SET CURRENT MODE or POSITION ---------------

    public enum TestModes {
        MODE_MOVE_TOP,
        MODE_MOVE_HIGH,
        MODE_MOVE_MID,
        MODE_MOVE_LOW,
        MODE_MOVE_BOTH,
        MODE_MOVE_WRIST,


        MODE_RUN_TO_POSITION,
        MODE_RUN_USING_ENCODERS,

        MODE_GOTO_INIT,
        MODE_GOTO_TEST_POSITION,
        MODE_GOTO_DRIVE_POSITION1,
        MODE_GOTO_GRABPOSITION,
        MODE_GOTO_HOLDBALL,

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
            Log.d("FTC7391", "Lift: " + "Power:" + power + "Current High" + stages[2].motor.getCurrentPosition() + "Current Low" + stages[0].motor.getCurrentPosition());
        }
        switch (mode) {
            case MODE_MOVE_TOP:

                if (bLimits && (power > 0 && stages[3].motor.getCurrentPosition() > stages[3].MAX_TICKS ||
                        power < 0 && stages[3].motor.getCurrentPosition() < stages[3].MIN_TICKS )){
                    stages[3].motor.setPower(0);
                    Log.d("FTC7391", "Lift: " + "Mid power = 0" );
                    //MidRunToPosition();
                }
                else{
                    //MidRunUsingEncoders();
                    stages[3].motor.setPower(1 * power);    //negative power = backwards
                }
                break;

            case MODE_MOVE_HIGH:

               if (bLimits && (power > 0 && stages[2].motor.getCurrentPosition() > stages[2].MAX_TICKS ||
                    power < 0 && stages[2].motor.getCurrentPosition() <= stages[2].MIN_TICKS )){
                   stages[2].motor.setPower(0);  //
                   Log.d("FTC7391", "Lift: " + "high power = 0" );
                   //highRunToPosition();
               }
               else{
                   //highRunUsingEncoders();
                   stages[2].motor.setPower(1 * power);
              }
                break;

            case MODE_MOVE_MID:

                if (bLimits && (power > 0 && stages[1].motor.getCurrentPosition() > stages[1].MAX_TICKS ||
                        power < 0 && stages[1].motor.getCurrentPosition() < stages[1].MIN_TICKS )){
                    stages[1].motor.setPower(0);
                    Log.d("FTC7391", "Lift: " + "Mid power = 0" );
                    //MidRunToPosition();
                }
                else{
                    //MidRunUsingEncoders();
                    stages[1].motor.setPower(1 * power);    //negative power = backwards
                }
                break;

            case MODE_MOVE_LOW:

               if (bLimits && (power > 0 && stages[0].motor.getCurrentPosition() > stages[0].MAX_TICKS ||
                    power < 0 && stages[0].motor.getCurrentPosition() < stages[0].MIN_TICKS )){
                    stages[0].motor.setPower(0);
                    Log.d("FTC7391", "Lift: " + "low power = 0" );
                   //lowRunToPosition();
               }
               else{
                   //lowRunUsingEncoders();
                   stages[0].motor.setPower(1 * power);    //negative power = backwards
               }
                break;

            case MODE_MOVE_BOTH:
                if (isRunToPosition == true)
                    runUsingEncoders();
//                if (power > 0 && liftHigh.getCurrentPosition() < LIFT_HIGH_MAX ||
//                    power < 0 && liftHigh.getCurrentPosition() > LIFT_HIGH_MIN )
                stages[2].motor.setPower(1 * power);
//                if (power > 0 && liftHigh.getCurrentPosition() < LIFT_LOW_MAX ||
//                    power < 0 && liftHigh.getCurrentPosition() > LIFT_LOW_MIN )
                stages[0].motor.setPower(1 * power);    //negative power = backwards
                break;


            case MODE_MOVE_WRIST:
                if (bLimits && (power > 0 && stages[4].motor.getCurrentPosition() > stages[4].MAX_TICKS ||
                        power < 0 && stages[4].motor.getCurrentPosition() < stages[4].MIN_TICKS )){
                    stages[4].motor.setPower(0);
                    Log.d("FTC7391", "Lift: " + "wrist power = 0" );
                    //wristRunToPosition();  // Shouldn't need to do this
                }
                else{
                    //wristRunUsingEncoders();
                    stages[4].motor.setPower(stages[4].MAX_POWER * power);    //negative power = backwards
                    //Log.v("FTC7391", "Lift: " + "WRIST_MAX_POWER" );
                }
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
                 grabPosition();
                break;
            case MODE_GOTO_HOLDBALL:
                holdBall();
            case MODE_STOP:
                Log.i("FTC7391", "Lift: " + "stop ");
                //runToPosition();
                stop();
                break;

        }
    }


    public static void stop() {
        setMotorTargetPosition(
                stages[0].motor.getCurrentPosition(),
            stages[1].motor.getCurrentPosition(),
                stages[2].motor.getCurrentPosition(),
            stages[3].motor.getCurrentPosition(),
            stages[4].motor.getCurrentPosition()
        );
    }


    public static void initPosition(){
        Log.i("FTC7391", "Lift: " + "initPosition ");
        //runToPosition();  //Done in setMotorTargetPosition();
        setMotorTargetPosition(0, 0, 0, 0, 0);
     }

    public static void testPosition(){
        //runToPosition();
        setMotorTargetPosition(3000, 2000, 2000, 3000, 500);
     }

    public static void drivePosition1(){
        Log.i("FTC7391", "Lift: " + "drivePostion1 ");
        //runToPosition();
        setMotorTargetPosition(1600, 0, 0, 0, 0);
     }

     public static void grabPosition(){
        Log.i("FTC7391", "Lift: " + "grabPosition ");
        setMotorTargetPosition(850, 850, 550, 0, 120);
     }

    public static void holdBall(){
        Log.i("FTC7391", "Lift: " + "holdBallPosition ");
        setMotorTargetPosition(850, 850, 550, 0, 120);
    }

    public static void raiseBallPosition1(){
        setMotorTargetPosition(stages[0].MAX_TICKS, stages[1].MAX_TICKS, 0, 0, 500);
    }

    public static void raiseBallPosition2(){
        setMotorTargetPosition(stages[0].MAX_TICKS, stages[1].MAX_TICKS, stages[2].MAX_TICKS, stages[3].MAX_TICKS, 500);
    }

    public static void dropBall1(){
        setMotorTargetPosition(stages[0].MAX_TICKS, stages[1].MAX_TICKS, stages[2].MAX_TICKS, stages[3].MAX_TICKS, stages[4].MAX_TICKS);
    }

    public static void dropBall2(){
        setMotorTargetPosition(0, 0, 0, 0, 0);
    }

    /*
    public static void straightHook(){
        Log.i("FTC7391", "Lift: " + "straightHook ");
        stages[3].motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        stages[3].motor.setTargetPosition(921);
        stages[3].motor.setPower(1);
    }

    public static void stickLift(){
        Log.i("FTC7391", "Lift: " + "stickLift");
        setMotorTargetPosition(1700, 1700, -550, 0, 120);
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
*/

}
