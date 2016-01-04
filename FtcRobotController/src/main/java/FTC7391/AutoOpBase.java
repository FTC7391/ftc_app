package FTC7391;

import android.util.Log;

import java.util.ArrayList;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Allana Evans on 9/26/15.
 */
public class AutoOpBase extends OpMode {

    private static final String TAG = AutoOpBase.class.getSimpleName();
    protected State currentState;
    protected int step;
     protected ArrayList<State> stepsList = new ArrayList<State> (20);
    protected FTC7391PrintWriter autoWriter = new FTC7391PrintWriter("Auto", "telemetryWait");
    private String stateStr = "";

    protected Stick stick;
    private Zipline ziplineBlue;
    private Zipline ziplineRed;


    public void init(){
        //telemetry.addData(TAG, "AutoOp Init");
        currentState = null;
        step = -1;

        DriveTrainAuto.init(hardwareMap);
        Lift.init(hardwareMap);
        Lift.runToPosition();
        stick = new Stick(hardwareMap);
        stick.setRetractedPosition();
        ziplineBlue = new Zipline(hardwareMap,1, 1, .5, "zipline_blue");
        ziplineRed = new Zipline(hardwareMap, 0, 0, .5, "zipline_red");
        ziplineBlue.setRetractedPosition();
        ziplineRed.setRetractedPosition();

        stateStr = "INIT";
        showTelemetry();

        //add steps to stepsList

    }

    public void loop(){
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
            //perform state action
            //showTelemetryDrivetrain();
            showTelemetry();
        }

        public boolean update(){
            cnt++;
            if (cnt%10 == 0) {
                showTelemetry();
            }
            return (updateState());
        }

        public abstract boolean updateState();

    }

    protected class MoveState extends State {

        private int inches;
        private double power;

        public MoveState(int i, double p){
            inches = i;
            power = p;
        }

        public void init() {
            super.init();

            DriveTrainAuto.moveInches(inches, power);
            stick.setDrivePosition();
            autoWriter.printf("Move Inches %d \n", inches);
            //telemetry.addData(TAG, "Move Inches " + inches);
            stateStr = "MOVE INCHES" + inches;
            showTelemetry();
        }

        public boolean updateState(){
            return DriveTrainAuto.isDone();
        }

    }

    protected class WaitState extends State {

        private int counter = 0;
        private int waitTime;

        public WaitState(int seconds){
            waitTime = (int)(seconds * 40);
        }

        public void init(){
            super.init();
            DriveTrainAuto.moveInches(0, 0);
        }

        public boolean updateState(){
            counter++;
            //if(counter%2 == 1)
                return (counter == waitTime || gamepad1.a);
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
            DriveTrainAuto.moveInches(0,0);
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
            stick.setDrivePosition();
            autoWriter.printf("Rotate Degrees %d \n", degrees);
            telemetry.addData(TAG, "Rotate Degrees " + degrees);
            showTelemetryDrivetrain();
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

    protected class StickState extends State {

        private int counter = 0;
        private int waitTime;

        public StickState(){
            waitTime = 500;
        }

        public void init(){
            super.init();
            DriveTrainAuto.moveInches(0, 0);
            stick.setDeployedPosition();
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
            return DriveTrainAuto.isDone();
        }

    }

    protected class TestPositionState extends State {

        public void init(){
            super.init();
            Lift.testPosition();
            stick.setDrivePosition();
            stateStr = "TEST POSITION";
        }

        public boolean updateState() { return Lift.isDone(); }
    }

    protected class DrivePosition1State extends State {

        public void init(){
            super.init();
            Lift.drivePosition1();
            stick.setDrivePosition();
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
            stick.setDrivePosition();
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
            stick.setDrivePosition();
            stateStr = "READY TO HANG POSITION";
        }

        public boolean updateState(){
            return Lift.isDone();
        }
    }


    private void showTelemetry() {
        showTelemetryDrivetrain();
        showTelemetryLift();
        showTelemetryState();
     }

    private void showTelemetryState() {
        telemetry.addData("Auto STATE", stateStr);
        Log.d("Auto STATE", stateStr);
   }

    private void showTelemetryDrivetrain() {
        telemetry.addData("DriveTrain FrontRight", DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_FRONT_RIGHT));
        telemetry.addData("DriveTrain FrontLeft ", DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_FRONT_LEFT));
        telemetry.addData("DriveTrain BackRight ", DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_BACK_RIGHT));
        telemetry.addData("DriveTrain BackLeft  ", DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_BACK_LEFT));
        autoWriter.printf("fr:%s fl:%s br:%s bl:%s \n",
            DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_FRONT_RIGHT),
            DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_FRONT_LEFT),
            DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_BACK_RIGHT),
            DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_BACK_LEFT));

        Log.d("DriveTrain FrontRight", DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_FRONT_RIGHT));
        Log.d("DriveTrain FrontLeft ", DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_FRONT_LEFT));
        Log.d("DriveTrain BackRight ", DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_BACK_RIGHT));
        Log.d("DriveTrain BackLeft  ", DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_BACK_LEFT));

    }


    private void showTelemetryLift() {
        telemetry.addData("High", "original: "  + Lift.originalTicksHigh + "|| end: " + Lift.getTicksLiftHigh());
        telemetry.addData("Low", "original: "   + Lift.originalTicksLow + "|| end: " + Lift.getTicksLiftLow());
        telemetry.addData("Angle", "original: " + Lift.originalTicksAngle + "|| end: " + Lift.getTicksLiftAngle());
        telemetry.addData("Hook", "original: "  + Lift.originalTicksHook + "|| end: " + Lift.getTicksLiftHook());
        autoWriter.printf("High %d %d    Low %d %d    Angle %d %d     Hook %d %d \n",
            Lift.originalTicksHigh, Lift.getTicksLiftHigh(),
            Lift.originalTicksLow, Lift.getTicksLiftLow(),
            Lift.originalTicksAngle, Lift.getTicksLiftAngle(),
            Lift.originalTicksHook, Lift.getTicksLiftHook()
        );

        Log.d("High", "original: "  + Lift.originalTicksHigh + "|| end: " + Lift.getTicksLiftHigh());
        Log.d("Low", "original: "   + Lift.originalTicksLow + "|| end: " + Lift.getTicksLiftLow());
        Log.d("Angle", "original: " + Lift.originalTicksAngle + "|| end: " + Lift.getTicksLiftAngle());
        Log.d("Hook", "original: " + Lift.originalTicksHook + "|| end: " + Lift.getTicksLiftHook());

    }


}