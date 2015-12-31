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
    protected FTC7391PrintWriter autoWriter = new FTC7391PrintWriter("Auto", "telemetryWait");

    public void init(){
        telemetry.addData(TAG, "AutoOp Init");
        DriveTrainAuto.init(hardwareMap);
        Lift.init(hardwareMap);
        //showTelemetryDrivetrain();
        showTelemetryLift();
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

    protected abstract class State{
        protected int cnt = 0;

        public void init(){
            //perform state action
            //showTelemetryDrivetrain();
            showTelemetryLift();
        }

        public boolean update(){
            cnt++;
            if (cnt%10 == 0) {
                //showTelemetryDrivetrain();
                showTelemetryLift();
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

        public void init(){
            super.init();

            DriveTrainAuto.moveInches(inches, power);
            stick.setDrivePosition();
            autoWriter.printf("Move Inches %d \n", inches);
            telemetry.addData(TAG, "Move Inches " + inches);
            //showTelemetryDrivetrain();
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
            DriveTrainAuto.moveInches(0,0);
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
            DriveTrainAuto.moveInches(0,0);
            stick.setDeployedPosition();
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
        }

        public boolean updateState(){
            return DriveTrainAuto.isDone();
        }

    }

    protected class ClimbPositionState extends State {

        public void init(){
            super.init();
            Lift.climbPosition();
            stick.setDrivePosition();

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
        }
        public boolean updateState(){
            return Lift.isDone();
        }
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
    }


}