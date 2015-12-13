package FTC7391;

import java.util.ArrayList;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Allana Evans on 9/26/15.
 */
public class AutoOpBase extends OpMode {

    private static final String TAG = AutoOpBase.class.getSimpleName();
    protected State currentState;
    protected int step;
    protected Stick stick;
    protected ArrayList<State> stepsList = new ArrayList<State> (20);

    public void init(){
        telemetry.addData(TAG, "AutoOp Init");
        DriveTrainAuto.init(hardwareMap);
        Lift.init(hardwareMap);
        showTelemetryDrivetrain();
        showTelemetryDrivetrain();
        currentState = null;
        step = -1;
        stick = new Stick(hardwareMap);
        stick.setRetractedPosition();
        //add steps to stepsList
    }

    public void loop(){
        if (currentState != null && !currentState.update()) return;
        step++;
        currentState = stepsList.get(step);
        currentState.init();
    }

    public void stop(){
        telemetry.addData(TAG, "Test Stopped");
        currentState = new StopState();
    }

    protected class State{

        public void init(){
            //perform state action
        }

        public boolean update(){
            showTelemetryDrivetrain();
            return DriveTrainAuto.isDone();
        }

    }

    protected class MoveState extends State {

        private int inches;
        private double power;

        public MoveState(int i, double p){
            inches = i;
            power = p;
        }

        public void init(){
            DriveTrainAuto.moveInches(inches,  power);
            stick.setDrivePosition();
        }

    }

    protected class WaitState extends State {

        private int counter = 0;
        private int waitTime;

        public WaitState(int seconds){
            waitTime = (int)(seconds * 40);
        }

        public void init(){
            DriveTrainAuto.moveInches(0,0);
            showTelemetryDrivetrain();
        }

        @Override
        public boolean update(){
            counter++;
            return (counter == waitTime || gamepad1.a);
        }

    }

    protected class RotateState extends State {

        protected int degrees;
        protected double power;

        public RotateState(int d, double p){
            degrees = d;
            power = p;

        }

        public void init (){
            DriveTrainAuto.rotateDegrees(degrees, power);
            stick.setDrivePosition();
        }

    }

    protected class StopState extends State {

        public StopState(){


        }

        public void init(){
            DriveTrainAuto.moveInches(0,0);
        }

        @Override
        public boolean update(){
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
            DriveTrainAuto.moveInches(0,0);
            stick.setDeployedPosition();
        }

        @Override
        public boolean update(){
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
            DriveTrainAuto.moveInches(inches, power);
        }

    }

    protected class ClimbPositionState extends State {

        public void init(){
            Lift.climbPosition();

        }
        public boolean update(){
            return Lift.isDone();
        }
    }

    protected class ReadyToHangPositionState extends State {

        public void init(){
            Lift.readyToHangPosition();
        }
        public boolean update(){
            return Lift.isDone();
        }
    }


    private void showTelemetryDrivetrain() {
        telemetry.addData("DriveTrain FrontRight", DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_FRONT_RIGHT));
        telemetry.addData("DriveTrain FrontLeft ", DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_FRONT_LEFT));
        telemetry.addData("DriveTrain BackRight ", DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_BACK_RIGHT));
        telemetry.addData("DriveTrain BackLeft  ", DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_BACK_LEFT));
    }

}
