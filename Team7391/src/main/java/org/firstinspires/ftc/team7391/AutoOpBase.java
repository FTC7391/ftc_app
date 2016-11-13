package org.firstinspires.ftc.team7391;

import android.util.Log;

import java.util.ArrayList;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

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

    private Zipline ziplineBlue;
    private Zipline ziplineRed;

    private static int nAutoLoop = 0;

//    public ArrayList<State>  getStepsList() {
//        return stepsList;
//    }
//

    public void init(){
        //telemetry.addData(TAG, "AutoOp Init");
        currentState = null;
        step = -1;

        DriveTrainAuto.init(hardwareMap);
        Lift.init(hardwareMap);
        Claw.init(hardwareMap);
        Lift.resetEncoders();
        ziplineBlue = new Zipline(hardwareMap,1, 1, .5, "pusher_left");
        ziplineRed = new Zipline(hardwareMap, 0, 0, .5, "pusher_right");
        ziplineBlue.setRetractedPosition();
        ziplineRed.setRetractedPosition();

        stateStr = "INIT";
        showTelemetry();

        //add steps to stepsList

    }


    public void loop(){
        nAutoLoop++;
        if (nAutoLoop == 10)
            nAutoLoop = 0;
        if (currentState != null && !currentState.update()) return;
        step++;
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
            Lift.runToPosition();
            //perform state action
        }

        public boolean update(){
            cnt++;
            if (cnt%100 == 0) {
                showTelemetryStateInfo();
            }
            return (updateState());
        }

        public abstract boolean updateState();

    }

    protected class MoveState extends State {

        private double inches;
        private double power;

        public MoveState(double i, double p){
            inches = i;
            power = p;
            Log.d("Autonomous", "MoveState constructor  inches:" + inches + " power:" + power + " i:" + i + " p:" + p);
        }

        public void init() {
            super.init();

            Log.d("Autonomous", "MoveState init  inches:" + inches + " power:" + power );
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

    protected class ColorState extends State {


        ColorSensor colorSensor;

        public ColorState(){

        }

        public void init() {
            super.init();
            colorSensor = hardwareMap.colorSensor.get("sensor_color");

            showTelemetryStateInfo();
        }

        public boolean updateState(){

            Log.d("Clear","" + colorSensor.alpha());
            Log.d("Red  ", "" + colorSensor.red());
            Log.d("Green", "" + colorSensor.green());
            Log.d("Blue ", "" + colorSensor.blue());

            return  gamepad1.a;
        }

    }

    protected class WaitState extends State {

        private int counter = 0;
        private int waitTime;

        public WaitState(int seconds)
        {
            waitTime = (int)(seconds * 40);
            Log.i("Autonomous", "WaitTime constructor " + "waitTime:" + waitTime);
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
            if (counter%1 == 0)
                Log.d("Autonomous", "WaitSate counter:" + counter  + " waitTime:" + waitTime + " gamepad1.a:" + gamepad1.a);

            return (counter == waitTime || gamepad1.a);
            //else
                //return (counter == waitTime || gamepad1.b);

        }

    }


    protected class RunToPositionState extends State {

        private int counter = 0;
        private int waitTime;

        public RunToPositionState(int seconds){
            waitTime = (int)(seconds * 40);
        }

        public void init(){
            super.init();
            DriveTrainAuto.moveInches(0, 0);
            stateStr = "RUN TO POSITION STATE";
            showTelemetryStateInfo();
        }

        public boolean updateState(){
            counter++;
            //if(counter%2 == 1)
            return (counter == waitTime || gamepad1.b);
            //else
            //return (counter == waitTime || gamepad1.b);

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

    protected class RotateState extends State {

        protected int degrees;
        protected double power;

        public RotateState(int d, double p){
            degrees = d;
            power = p;
            stateStr = "ROTATE" + d + "degrees";


        }

        public void init (){
            super.init();
            DriveTrainAuto.rotateDegrees(degrees, power);
            dbgWriter.printf("Rotate Degrees %d \n", degrees);
            telemetry.addData(TAG, "Rotate Degrees " + degrees);
            showTelemetryStateInfo();
        }

        public boolean updateState(){
            return DriveTrainAuto.isDone();
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

    protected class StickLiftState extends State {

        private int counter = 0;
        private int waitTime;

        public StickLiftState(){
            waitTime = 500;
        }

        public void init(){
            super.init();
            DriveTrainAuto.moveInches(0, 0);
            Lift.stickLift();
            stateStr = "STICK LIFT";
        }

        public boolean updateState(){
            counter++;
            return (counter == waitTime || gamepad1.b);
        }

    }
    protected class StickDrivePositionState extends State {

        private int counter = 0;
        private int waitTime;

        public StickDrivePositionState(){
            waitTime = 100;
        }

        public void init(){
            super.init();
            DriveTrainAuto.moveInches(0, 0);
            stateStr = "STICK DRIVE POSITION";
        }

        public boolean updateState(){
            counter++;
            return (counter == waitTime || gamepad1.b);
        }

    }



    protected class StickState extends State {

        private int counter = 0;
        private int waitTime;

        public StickState(){
            waitTime = 50;
        }

        public void init(){
            super.init();
            DriveTrainAuto.moveInches(0, 0);
            stateStr = "STICK";
        }

        public boolean updateState(){
            counter++;
            return (counter == waitTime || gamepad1.b);
        }

    }

    protected class StickMoveState extends State {

        private int inches;
        private double power;

        public StickMoveState(int i, double p){

            inches = i;
            power = p;

        }

        public void init(){
            super.init();
            DriveTrainAuto.moveInches(inches, power);
            stateStr = "STICK MOVE" + inches + "inches";
        }

        public boolean updateState(){
            return Lift.isDone();
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

        public boolean updateState() { return Lift.isDone(); }

    }

    protected class DrivePosition2State extends State {

        public void init(){
            super.init();
            Lift.drivePosition2();
            stateStr = "DRIVE POSITION 2";

        }

        public boolean updateState() { return Lift.isDone();}
    }

    protected class ClimbPositionState extends State {

        public void init(){
            super.init();
            Lift.climbPosition();
            stateStr = "CLIMB POSITION";

        }
        public boolean updateState(){
            return Lift.isDone();
        }
    }

    protected class ReadyToHangPositionState extends State {

        public void init(){
            super.init();
            Lift.readyToHangPosition();
            stateStr = "READY TO HANG POSITION";
        }

        public boolean updateState(){
            return Lift.isDone();
        }
    }

    protected class StraightHook extends State {

        public void init(){
            super.init();
            Lift.straightHook();
            stateStr = "STRAIGHT HOOK";
        }

        public boolean updateState(){ return Lift.isDone(); }
    }


    private void showTelemetry() {
        showTelemetryState();
        showTelemetryDrivetrain();
        showTelemetryLift();
      }

    private void showTelemetryStateInfo() {
        telemetry.addData("10", String.format("Auto STATE %s", stateStr));
        Log.i("Autonomous", "STATE:" + stateStr + "  step " + step);
    }
     private void showTelemetryState() {
        telemetry.addData("10",String.format("Auto STATE %s", stateStr));
         Log.d("Autonomous",  "STATE:" + stateStr + "  step " + step);
     }

    private void showTelemetryDrivetrain() {
        telemetry.addData("20 " , String.format("DriveTrain Right %s", DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_RIGHT)));
        telemetry.addData("21 " , String.format("DriveTrain Left  %s", DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_LEFT)));
        dbgWriter.printf("r:%s l:%s \n",
            DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_RIGHT),
            DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_LEFT));

        Log.d("Autonomous", "Right:" + DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_RIGHT));
        Log.d("Autonomous", "Left :" + DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_LEFT));

    }

    private void showTelemetryLift() {
        telemetry.addData("30 " , String.format("High  : original: %d current: %d", Lift.originalTicksHigh, Lift.getTicksLiftHigh()));
        telemetry.addData("31 " , String.format("Low   : original: %d current: %d", Lift.originalTicksLow, Lift.getTicksLiftLow()));
        telemetry.addData("32 " , String.format("Angle : original: %d current: %d", Lift.originalTicksShoulder, Lift.getTicksliftShoulder()));
        telemetry.addData("33 " , String.format("Hook  : original: %d currnet: %d", Lift.originalTicksWrist, Lift.getTicksliftWrist()));
        dbgWriter.printf("High %d %d    Low %d %d    Angle %d %d     Hook %d %d \n",
            Lift.originalTicksHigh, Lift.getTicksLiftHigh(),
            Lift.originalTicksLow, Lift.getTicksLiftLow(),
            Lift.originalTicksShoulder, Lift.getTicksliftShoulder(),
            Lift.originalTicksWrist, Lift.getTicksliftWrist()
        );

        Log.d("Autonomous", "High     : original:" + Lift.originalTicksHigh + "|| end: " + Lift.getTicksLiftHigh());
        Log.d("Autonomous", "Low      : original:" + Lift.originalTicksLow + "|| end: " + Lift.getTicksLiftLow());
        Log.d("Autonomous", "Shoulder : original:" + Lift.originalTicksShoulder + "|| end: " + Lift.getTicksliftShoulder());
        Log.d("Autonomous", "Wrist    : original:" + Lift.originalTicksWrist + "|| end: " + Lift.getTicksliftWrist());

    }




}