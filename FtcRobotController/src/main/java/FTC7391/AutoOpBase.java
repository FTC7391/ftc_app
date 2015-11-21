package FTC7391;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Allana Evans on 9/26/15.
 */
public abstract class AutoOpBase extends OpMode {

    private static final String TAG = AutoOpBase.class.getSimpleName();
    protected State currentState;
    protected int step;
    protected Stick stick;

    public void init(){
        telemetry.addData(TAG, "AutoOp Init");
        DriveTrainAuto.init(hardwareMap);
        showTelemetryDrivetrain();
        showTelemetryDrivetrain();
        currentState = null;
        step = 0;
        stick = new Stick(hardwareMap);
        stick.setRetractedPosition();

    }

    public abstract void loop();

    public void stop(){
        telemetry.addData(TAG, "Test Stopped");
        currentState = new StopState();
    }

    protected class State{

        public State(){
            //perform state action
        }

        public boolean update(){
            showTelemetryDrivetrain();
            return DriveTrainAuto.isDone();
        }

    }

    protected class MoveState extends State {

        public MoveState(int inches, double power){

            DriveTrainAuto.moveInches(inches,  power);
            stick.setDrivePosition();
        }

    }

    protected class WaitState extends State {

        private int counter = 0;
        private int waitTime;

        public WaitState(int seconds){
            DriveTrainAuto.moveInches(0,0);
            showTelemetryDrivetrain();
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
            stick.setDrivePosition();
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

    protected class StickState extends State {

        private int counter = 0;
        private int waitTime;

        public StickState(){

            DriveTrainAuto.moveInches(0,0);
            stick.setDeployedPosition();
            waitTime = 40;
        }

        @Override
        public boolean update(){
            counter++;
            return (counter == waitTime || gamepad1.a);
        }

    }

    protected class StickMoveState extends State {

        public StickMoveState(int inches, double power){

            DriveTrainAuto.moveInches(inches, power);

        }

    }


    private void showTelemetryDrivetrain() {
        telemetry.addData("DriveTrain FrontRight", DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_FRONT_RIGHT));
        telemetry.addData("DriveTrain FrontLeft ", DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_FRONT_LEFT));
        telemetry.addData("DriveTrain BackRight ", DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_BACK_RIGHT));
        telemetry.addData("DriveTrain BackLeft  ", DriveTrainAuto.getPosition(DriveTrain.TestModes.MODE_MOVE_BACK_LEFT));
    }

}
