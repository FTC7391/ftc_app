package FTC7391;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Allana Evans on 9/26/15.
 */
public abstract class AutoOpBase extends OpMode {

    private static final String TAG = AutoOpBase.class.getSimpleName();
    protected State currentState;
    protected int step;

    public void init(){
        telemetry.addData(TAG, "AutoOp Init");
        DriveTrainAuto.init(hardwareMap);
        step = 1;
        //initialize current state

    }

    public abstract void loop();

    public abstract void stop();

    protected class State{

        public State(){
            //perform state action
        }

        public boolean update(){
            telemetry.addData(TAG, DriveTrainAuto.getPosition());
            return DriveTrainAuto.isDone();
        }

    }

    protected class MoveState extends State {

        public MoveState(int inches, double power){
            DriveTrainAuto.moveInches(inches,  power);
        }

    }

    protected class WaitState extends State {

        private int counter = 0;
        private int waitTime;

        public WaitState(int seconds){
            DriveTrainAuto.moveInches(0,0);
            waitTime = (int)(seconds * 40);
        }

        @Override
        public boolean update(){
            counter++;
            return (counter == waitTime || gamepad1.a);
        }

    }

    protected class RotateState extends State {

        public RotateState(int degrees, double power){
            DriveTrainAuto.rotateDegrees(degrees, power);
        }

    }

    protected class StopState extends State {

        public StopState(){
            DriveTrainAuto.moveInches(0,0);
        }

        @Override
        public boolean update(){
            return false;
        }

    }

}
