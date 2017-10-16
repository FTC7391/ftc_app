package org.firstinspires.ftc.team7391;

import android.util.Log;

import java.util.ArrayList;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddressableDevice;
import com.qualcomm.robotcore.hardware.I2cAddr;


/**
 * Created by Allana Evans on 9/26/15.
 */
public class AutoOpBase extends OpMode {

    private static final String TAG = AutoOpBase.class.getSimpleName();
    protected State currentState;
    protected int step;
    protected ArrayList<State> stepsList = new ArrayList<State> (40);
    protected FTC7391PrintWriter dbgWriter = new FTC7391PrintWriter("Autonomous", "telemetryWait");
    private String stateStr = "";

    protected static Zipline pusher_right;
    protected static Zipline pusher_left;
    protected static final double DEFAULT_MOVE_POWER = 0.65;
    protected static final double DEFAULT_TURN_POWER = 0.6;

    private ColorSensor colorSensor;
    private ColorSensor colorRight;
    private ColorSensor colorLeft;
    private ColorSensor colorRightBottom;
    private ColorSensor colorLeftBottom;

    private int isLastColorRed; //RED = 1, BLUE = -1; need to change to enum
    private boolean isWhite;
    protected static double distMovedToColor;

    private static int nAutoLoop = 0;

//    public ArrayList<State>  getStepsList() {
//        return stepsList;
//    }
//

    //If LinearOpMode need this
//    public void runOpMode() {
//        team7391_init();
//        waitForStart();
//
//        while(opModeIsActive()) {
//            loop();
//            idle();
//        }
//        stop();
//    }
//
    //Else need this for OpMode (not LinearOpMode)
//    public void init() {
//        team7391_init();
//    }
//
    //public void team7391_init(){
    public void init(){
        //telemetry.addData(TAG, "AutoOp Init");
        currentState = null;
        step = -1;

        Log.i("FTC7391", "Auto: " + "AutoOpBase init:"+ "  step " + step);

        DriveTrainAuto.init(hardwareMap);
        Lift.init(hardwareMap);
        //Claw.init(hardwareMap);
        Lift.resetEncoders();

        //public Zipline(HardwareMap hardwareMap, double retracted, double drive, double deploy, String name)
        pusher_right = new Zipline(hardwareMap, 1,1, .35, "pusher_right"); //
        pusher_left = new Zipline(hardwareMap, 0,0, .65, "pusher_left"); //
        pusher_right.setRetractedPosition();
        pusher_left.setRetractedPosition();

        colorRight = hardwareMap.colorSensor.get("color_right"); // 0X38 Default
        colorRight.setI2cAddress(I2cAddr.create8bit(0X5C));
        colorRight.enableLed(false);
        colorLeft = hardwareMap.colorSensor.get("color_left");
        colorLeft.setI2cAddress(I2cAddr.create8bit(0X3C));
        colorLeft.enableLed(false);
        colorRightBottom = hardwareMap.colorSensor.get("color_right_bottom");
        colorRightBottom.setI2cAddress(I2cAddr.create8bit(0X6C));
        colorRight.enableLed(false);
        colorLeftBottom = hardwareMap.colorSensor.get("color_left_bottom");
        colorLeftBottom.setI2cAddress(I2cAddr.create8bit(0X4C));
        colorLeft.enableLed(false);

        Log.i("FTC7391", "Auto: " + " Color Left Address: " + colorLeft.getI2cAddress().get8Bit());
        Log.i("FTC7391", "Auto: " + " Color Right Address: " + colorRight.getI2cAddress().get8Bit());


        stateStr = "INIT";
        showTelemetry();

        //add steps to stepsList

    }


    public void loop(){
        nAutoLoop++;
        if (nAutoLoop == 10)  {

            nAutoLoop = 0;

            //Log.v("FTC7391", "update");
        }

        if (currentState != null && !currentState.update())
            return;
        step++;
        Log.d("FTC7391", "Auto: " + "AutoOpBase loop:"+ "  step " + step);
        currentState = stepsList.get(step);
        currentState.init();
    }

    public void stop(){
        //telemetry.addData(TAG, "Test Stopped")
        stateStr = "STOP";
        currentState = new StopState();
    }

    protected abstract class State{
        protected int cnt = 0;

        public void init(){
//            Lift.runToPosition();
            //perform state action
        }

        public boolean update(){
            cnt++;
            if (cnt%300 == 0) {
                showTelemetryStateInfo();
            }
            return (updateState());
        }

        public abstract boolean updateState();

    }





    /* ----- STATES --------------*/
    protected class WaitState extends State {

        private int counter = 0;
        private int waitTime;

        public WaitState(int seconds)
        {
            waitTime = (int)(seconds * 160);
            Log.i("FTC7391", "Auto: " + "WaitTime constructor " + "waitTime:" + waitTime);
        }

        public void init(){
            super.init();
            DriveTrainAuto.moveInches(0, 0);
            stateStr = "WAIT";
            showTelemetryStateInfo();
        }

        public boolean updateState(){
            counter++;
            //if(counter%2 == 1)
            //if (counter%100 == 0)
            // Log.d("FTC7391", "Auto: " + "WaitSate counter:" + counter  + " waitTime:" + waitTime + " gamepad1.a:" + gamepad1.a);

            return (counter == waitTime || gamepad1.a);
            //else
            //return (counter == waitTime || gamepad1.b);

        }

    }

    protected class TestPositionState extends State {

        public void init(){
            super.init();
            Lift.testPosition();
            stateStr = "TEST POSITION";
        }

        public boolean updateState() { return Lift.isDone(); }
    }

    protected class DrivePosition1State extends State {

        public void init(){
            super.init();
            Lift.drivePosition1();
            stateStr = "DRIVE POSITION 1";

        }

        public boolean updateState() {
            Log.v("FTC7391", "Lift DrivePosition1 State update");
            return Lift.isDone(); }

    }

    protected class MoveState extends State {

        private double inches;
        private double power;

        public MoveState(double i, double p){
            inches = i;
            power = p;
            Log.i("FTC7391", "Auto: " + "MoveState constructor  inches:" + inches + " power:" + power + " i:" + i + " p:" + p);
        }

        public void init() {
            super.init();

            Log.i("FTC7391", "Auto: " + "MoveState init  inches:" + inches + " power:" + power );
            DriveTrainAuto.moveInches(inches, power);
            dbgWriter.printf("Move Inches %.2f \n", inches);
            telemetry.addData(TAG, "Move Inches " + inches);
            stateStr = "MOVE INCHES" + inches;
            showTelemetryStateInfo();
        }

        public boolean updateState(){
            return DriveTrainAuto.isDone();
        }

    }

    protected class RotateState extends State {

        protected double degrees;
        protected double power;

        public RotateState(double d, double p){
            degrees = d;
            power = p;
            stateStr = "ROTATE" + d + "degrees";


        }

        public void init (){
            super.init();
            DriveTrainAuto.rotateDegrees(degrees, power);
            dbgWriter.printf("Rotate Degrees %f \n", degrees);
            telemetry.addData(TAG, "Rotate Degrees " + degrees);
            showTelemetryStateInfo();
        }

        public boolean updateState(){
            return DriveTrainAuto.isDone();
        }

    }

    enum Colors {
        RED("red"),BLUE("blue"),GRAY("gray"),WHITE("white");
        private String name;
        Colors(String n)
        {
           name = n;
        }
        public String toString()
        {
            return name;
        }

    }

    protected class ColorTestState extends State {

        public ColorTestState(){

        }

        public void init() {
            super.init();
            telemetry.addData(TAG, "Color Test State ");
            stateStr = "COLOR TEST";

            showTelemetryStateInfo();
        }

        public boolean updateState()
        {

            if (cnt%300 == 0) {
                showTelemetryStateInfo();
                Log.d("FTC7391", "COLOR: " + "Clear(Alpha)" + "" + colorLeft.alpha() + "   " + colorRight.alpha() + " " + colorLeftBottom.alpha() + "   " + colorRightBottom.alpha());
                Log.d("FTC7391", "COLOR: " + "Red         " + "" + colorLeft.red() + "   " + colorRight.red() + " " + colorLeftBottom.red() + "   " + colorRightBottom.red());
                Log.d("FTC7391", "COLOR: " + "Green       " + "" + colorLeft.green() + "   " + colorRight.green() +  " " + colorLeftBottom.green() + "   " + colorRightBottom.green());
                Log.d("FTC7391", "COLOR: " + "Blue        " + "" + colorLeft.blue() + "   " + colorRight.blue() +  " " + colorLeftBottom.blue() + "   " + colorRightBottom.blue());

                Log.d("FTC7391", "ColorLeft: " + getColor(colorLeft));
                Log.d("FTC7391", "ColorRight: " + getColor(colorRight));
                Log.d("FTC7391", "ColorLeftBottom: " + getColor(colorLeftBottom));
                Log.d("FTC7391", "ColorRightBottom: " + getColor(colorRightBottom));

            }



//            if (cnt%700 == 0)
//                return true;
            return  gamepad1.a;
            //return false;
        }


        public Colors getColor(ColorSensor cs)
        {
            if(cs.blue()>12) {
                if (cs.red() > 22)
                    return Colors.WHITE;
                else
                    return Colors.BLUE;
            }
            else
            {
                if(cs.red()>17)
                    return Colors.RED;
                else
                    return Colors.GRAY;
            }

        }

    }

    protected int getlastColor() { return isLastColorRed; }

    protected class ColorState extends State {

        public ColorState(int isRed){
            if(isRed == 1){
                colorSensor = colorRight;
                Log.d("FTC7391", "Red so Color Right");
            }
            else //if(isRed == -1)
            {
                colorSensor = colorLeft;
                Log.d("FTC7391", "Blue so Color Color Left");
            }
        }

        public void init() {
            super.init();
            telemetry.addData(TAG, "Color State ");
            stateStr = "COLOR";
            isLastColorRed = 0;

            showTelemetryStateInfo();
        }

        public boolean updateState(){

            showTelemetryStateInfo();
            int alpha = colorSensor.alpha();
            int red = colorSensor.red();
            int green = colorSensor.green();
            int blue = colorSensor.blue();

            Log.d("FTC7391", "COLOR: " + "Clear(Alpha)" + "" + alpha);
            Log.d("FTC7391", "COLOR: " + "Red         " + "" + red);
            Log.d("FTC7391", "COLOR: " + "Green       " + "" + green);
            Log.d("FTC7391", "COLOR: " + "Blue        " + "" + blue);

            if(red>blue){
                isLastColorRed = 1;
                Log.d("FTC7391", "COLOR: RED");
            }
            else if(blue>red){
                isLastColorRed = -1;
                Log.d("FTC7391", "COLOR: BLUE");
            }
            else{
                isLastColorRed = 0;
                Log.d("FTC7391", "COLOR: UNKNOWN");
            }

            //if(isLastColorRed!=0 || cnt%100 == 0)
            if(cnt%700 == 0)
                return true;
            //return  gamepad1.a;
            return  false;
        }

    }
    
    protected class MoveToColor extends State {

        private double inches;
        private double power;
        private boolean isMoving;
        private int isRed;
        
        public MoveToColor(int isRed, double inches, double power){
            this.isRed = isRed;
            this.inches = inches;
            this.power = power;
            isMoving = false;

            if(isRed == 1){
                colorSensor = colorRight;
                Log.d("FTC7391", "Red so Color Right");
            }
            else //if(isRed == -1)
            {
                colorSensor = colorLeft;
                Log.d("FTC7391", "Blue so Color Color Left");
            }

         }

        public void init() {
            super.init();
            telemetry.addData(TAG, "MoveToColor State ");
            stateStr = "MOVE_TO_COLOR";
            isLastColorRed = 0;
            distMovedToColor = 0;

            showTelemetryStateInfo();
        }

        public boolean updateState(){
            if(!isMoving){
                int alpha = colorSensor.alpha();
                int red = colorSensor.red();
                int green = colorSensor.green();
                int blue = colorSensor.blue();

                Log.d("FTC7391", "COLOR: " + "Clear(Alpha)" + "" + alpha);
                Log.d("FTC7391", "COLOR: " + "Red         " + "" + red);
                Log.d("FTC7391", "COLOR: " + "Green       " + "" + green);
                Log.d("FTC7391", "COLOR: " + "Blue        " + "" + blue);

                //might need to add filter or read value more than once
                if(red>blue){
                    isLastColorRed = 1;
                    Log.d("FTC7391", "COLOR: RED");
                }
                else if(blue>red){
                    isLastColorRed = -1;
                    Log.d("FTC7391", "COLOR: BLUE");
                }
                else{
                    isLastColorRed = 0;
                    Log.d("FTC7391", "COLOR: UNKNOWN");
                }

                showTelemetryStateInfo();

                if(isLastColorRed == isRed){  //check if color is our alliance color

                    return true;
                }
                else{  //move to other half of beacon
                    Log.i("FTC7391", "Auto: " + "MoveState init  inches:" + inches + " power:" + power );
                    DriveTrainAuto.moveInches(inches, power);
                    isMoving = true;
                    distMovedToColor = inches;
                    return false;
                }
            }
            else{ //isMoving == true
                return DriveTrainAuto.isDone();
            }

        }

    }

    protected class MoveToWhiteLineState extends State {

        private double inches;
        private double power;
        private boolean isMoving;
        private int isRed;

        public MoveToWhiteLineState(double inches, double power){
            this.inches = inches;
            this.power = power;
            isMoving = false;
        }

        public void init() {
            super.init();
            telemetry.addData(TAG, "MoveToColor State ");
            stateStr = "MOVE_TO_WHITELINE";
            colorRightBottom.enableLed(true);
            colorLeftBottom.enableLed(true);
            showTelemetryStateInfo();
        }

        private void checkWhite(){
            int alpha = (colorRightBottom.alpha() + colorLeftBottom.alpha())/2;
            int red = (colorRightBottom.red() + colorLeftBottom.red())/2;
            int green = (colorRightBottom.green() + colorLeftBottom.green())/2;
            int blue = (colorRightBottom.blue() + colorLeftBottom.blue())/2;

            Log.d("FTC7391", "COLOR: " + "Clear(Alpha)" + "" + alpha);
            Log.d("FTC7391", "COLOR: " + "Red         " + "" + red);
            Log.d("FTC7391", "COLOR: " + "Green       " + "" + green);
            Log.d("FTC7391", "COLOR: " + "Blue        " + "" + blue);

            if(alpha>40){
                isWhite = true;
                Log.d("FTC7391", "COLOR: WHITE");
            }
            else if(alpha<30){
                isWhite = false;
                Log.d("FTC7391", "COLOR: NONWHITE");
            }
            else{
                isWhite = false;
                Log.d("FTC7391", "COLOR: UNKNOWN");
            }

            showTelemetryStateInfo();
        }

        public boolean updateState(){
            if(!isMoving){

                checkWhite();

                if(isWhite)//keep going until white is detected or distance is reached
                {
                    Log.d("FTC7391", "WHITE FOUND BEFORE MOVING");
                    colorRightBottom.enableLed(false);
                    colorLeftBottom.enableLed(false);
                    return true;
                }
                else{
                    Log.i("FTC7391", "Auto: " + "MoveState init  inches:" + inches + " power:" + power );
                    DriveTrainAuto.moveInches(inches, power);
                    isMoving = true;
                    return false;
                }
            }
            else{ //isMoving == true

                checkWhite();

                if(isWhite)//keep going until white is detected or distance is reached
                {
                    DriveTrainAuto.stop();
                    Log.d("FTC7391", "WHITE FOUND SO STOPPED");
                    colorRightBottom.enableLed(false);
                    colorLeftBottom.enableLed(false);
                    return true;
                }
                else if(DriveTrainAuto.isDone()){
                    Log.d("FTC7391", "MAX DISTANCE MOVED, WHITE NOT FOUND");
                    colorRightBottom.enableLed(false);
                    colorLeftBottom.enableLed(false);
                    return true;
                }
                return false;
            }

        }

    }





    protected class PusherState extends State {

        private int isRed;
        private long waitTime;
        private int counter;
        private long finalTime;
        private boolean toBeDeployed;

        public PusherState(int isRed, boolean toBeDeployed){
            this.isRed = isRed;
            this.toBeDeployed = toBeDeployed;

            waitTime = 100;  //milliseconds
            counter = 0;
            finalTime = 0;
        }

        public void init(){
            super.init();
            finalTime = System.currentTimeMillis() + waitTime;
            if(toBeDeployed) {
                if (isRed == -1) {
                    pusher_left.setDeployedPosition();
                    pusher_right.setRetractedPosition();
                } else {
                    pusher_right.setDeployedPosition();
                    pusher_left.setRetractedPosition();
                }
            }
            else{
                pusher_right.setRetractedPosition();
                pusher_left.setRetractedPosition();
            }
            stateStr = "PUSHER";
        }

        public boolean updateState(){
            counter++;
            return counter==waitTime;
        }

    }

      protected class MoveToSecondBeacon extends State {

        private double inches;
        private double power;

        public MoveToSecondBeacon(double inches, double power){
            this.inches = inches;
            this.power = power;
            Log.i("FTC7391", "Auto: " + "MoveToSecondBeacon constructor  inches:" + inches + " power:" + power );
        }

        public void init() {
            super.init();
            inches = inches - distMovedToColor;
            Log.i("FTC7391", "Auto: " + "MoveToSecondBeacon init  inches:" + inches + " power:" + power );
            DriveTrainAuto.moveInches(inches, power);
            dbgWriter.printf("Move Inches %.2f \n", inches);
            telemetry.addData(TAG, "Move Inches " + inches);
            stateStr = "MOVE TO SECOND BEACON" + inches;
            showTelemetryStateInfo();
        }

        public boolean updateState(){
            return DriveTrainAuto.isDone();
        }

    }


    protected class MoveToRightButtonBeacon2 extends State {

        private double inches;
        private double power;

        public MoveToRightButtonBeacon2(double inches, double power){
            this.inches = inches;
            this.power = power;
            Log.i("FTC7391", "Auto: " + "MoveToRightButtonBeacon2 constructor  inches:" + inches + " power:" + power );
        }

        public void init() {
            super.init();
            inches = inches - distMovedToColor;
            Log.i("FTC7391", "Auto: " + "MoveToRightButtonBeacon2 init  inches:" + inches + " power:" + power );
            DriveTrainAuto.moveInches(inches, power);
            dbgWriter.printf("Move Inches %.2f \n", inches);
            telemetry.addData(TAG, "Move Inches " + inches);
            stateStr = "MOVE TO RIGHT BUTTON BEACON2" + inches;
            showTelemetryStateInfo();
        }

        public boolean updateState(){
            return DriveTrainAuto.isDone();
        }

    }


    protected class PauseState extends State {

        private int counter = 0;

        public PauseState(){

        }

        public void init(){
            super.init();
            DriveTrainAuto.moveInches(0, 0);
        }

        public boolean updateState(){
            counter++;
            return (counter == 1);
        }

    }

    protected class StopState extends State {

        public StopState(){
        }

        public void init(){
            super.init();
            DriveTrainAuto.moveInches(0, 0);
            stateStr = "STOP";
        }

        public boolean updateState(){
            return false;
        }

    }









    private void showTelemetry() {
        Log.i("FTC7391", "showTelemetry");
        showTelemetryState();
        showTelemetryDrivetrain();
        showTelemetryLift();
      }

    private void showTelemetryStateInfo() {
        telemetry.addData("10", String.format("Auto STATE %s", stateStr));
        Log.i("FTC7391", "Auto: " + "STATE:" + stateStr + "  step " + step);
    }
     private void showTelemetryState() {
        telemetry.addData("12",String.format("Auto STATE %s", stateStr));
         Log.d("FTC7391", "Auto: " +  "STATE:" + stateStr + "  step " + step);
     }

    private void showTelemetryDrivetrain() {
        telemetry.addData("20 " , String.format("DriveTrain Right %s", DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_RIGHT)));
        telemetry.addData("21 " , String.format("DriveTrain Left  %s", DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_LEFT)));
        dbgWriter.printf("r:%s l:%s \n",
            DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_RIGHT),
            DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_LEFT));

        Log.d("FTC7391", "Auto: " + "Right:" + DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_RIGHT));
        Log.d("FTC7391", "Auto: " + "Left :" + DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_LEFT));

    }

    private void showTelemetryLift() {
        telemetry.addData("30 " , String.format("High  : original: %d current: %d", Lift.getOriginalTicksHigh(), Lift.getTicksLiftHigh()));
        telemetry.addData("31 " , String.format("Low   : original: %d current: %d", Lift.getOriginalTicksLow(), Lift.getTicksLiftLow()));
        telemetry.addData("32 " , String.format("Angle : original: %d current: %d", Lift.getOriginalTicksMid(), Lift.getTicksLiftMid()));
        telemetry.addData("33 " , String.format("Hook  : original: %d currnet: %d", Lift.getOriginalTicksWrist(), Lift.getTicksLiftWrist()));
        dbgWriter.printf("High %d %d    Low %d %d    Angle %d %d     Hook %d %d \n",
            Lift.getOriginalTicksHigh(), Lift.getTicksLiftHigh(),
            Lift.getOriginalTicksLow(), Lift.getTicksLiftLow(),
            Lift.getOriginalTicksMid(), Lift.getTicksLiftMid(),
            Lift.getOriginalTicksWrist(), Lift.getTicksLiftWrist()
        );

        Log.d("FTC7391", "Auto: " + "High     : original:" + Lift.getOriginalTicksHigh() + "|| end: " + Lift.getTicksLiftHigh());
        Log.d("FTC7391", "Auto: " + "Low      : original:" + Lift.getOriginalTicksLow()+ "|| end: " + Lift.getTicksLiftLow());
        Log.d("FTC7391", "Auto: " + "Mid : original:" + Lift.getOriginalTicksMid() + "|| end: " + Lift.getTicksLiftMid());
        Log.d("FTC7391", "Auto: " + "Wrist    : original:" + Lift.getOriginalTicksWrist() + "|| end: " + Lift.getTicksLiftWrist());

    }





//    protected class StickDrivePositionState extends State {
//
//        private int counter = 0;
//        private int waitTime;
//
//        public StickDrivePositionState(){
//            waitTime = 100;
//        }
//
//        public void init(){
//            super.init();
//            DriveTrainAuto.moveInches(0, 0);
//            stateStr = "STICK DRIVE POSITION";
//        }
//
//        public boolean updateState(){
//            counter++;
//            return (counter == waitTime || gamepad1.b);
//        }
//
//    }
//
//    protected class StickState extends State {
//
//        private int counter = 0;
//        private int waitTime;
//
//        public StickState(){
//            waitTime = 50;
//        }
//
//        public void init(){
//            super.init();
//            DriveTrainAuto.moveInches(0, 0);
//            stateStr = "STICK";
//        }
//
//        public boolean updateState(){
//            counter++;
//            return (counter == waitTime || gamepad1.b);
//        }
//
//    }
//
//    protected class StickMoveState extends State {
//
//        private int inches;
//        private double power;
//
//        public StickMoveState(int i, double p){
//
//            inches = i;
//            power = p;
//
//        }
//
//        public void init(){
//            super.init();
//            DriveTrainAuto.moveInches(inches, power);
//            stateStr = "STICK MOVE" + inches + "inches";
//        }
//
//        public boolean updateState(){
//            return Lift.isDone();
//        }
//
//    }
//    protected class StickLiftState extends State {
//
//        private int counter = 0;
//        private int waitTime;
//
//        public StickLiftState(){
//            waitTime = 500;
//        }
//
//        public void init(){
//            super.init();
//            DriveTrainAuto.moveInches(0, 0);
//            //Lift.stickLift();
//            stateStr = "STICK LIFT";
//        }
//
//        public boolean updateState(){
//            counter++;
//            return (counter == waitTime || gamepad1.b);
//        }
//
//    }
//
//
//    protected class DrivePosition2State extends State {
//
//        public void init(){
//            super.init();
//            Lift.drivePosition2();
//            stateStr = "DRIVE POSITION 2";
//
//        }
//
//        public boolean updateState() { return Lift.isDone();}
//    }
//
//    protected class ClimbPositionState extends State {
//
//        public void init(){
//            super.init();
//            Lift.climbPosition();
//            stateStr = "CLIMB POSITION";
//
//        }
//        public boolean updateState(){
//            return Lift.isDone();
//        }
//    }
//
//    protected class ReadyToHangPositionState extends State {
//
//        public void init(){
//            super.init();
//            Lift.readyToHangPosition();
//            stateStr = "READY TO HANG POSITION";
//        }
//
//        public boolean updateState(){
//            return Lift.isDone();
//        }
//    }
//
//    protected class StraightHook extends State {
//
//        public void init(){
//            super.init();
//            Lift.straightHook();
//            stateStr = "STRAIGHT HOOK";
//        }
//
//        public boolean updateState(){ return Lift.isDone(); }
//    }



}