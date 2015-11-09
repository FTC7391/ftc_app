package FTC7391;

/**
 * Created by Allana on 10/3/2015.
 */
public class AutoTestOp extends AutoOpBase
{
    private static final String TAG = AutoTestOp.class.getSimpleName();

    @Override
    public void init()
    {
        super.init();
        currentState = new MoveForwardState();
    }

    @Override
    public void stop()
    {
        telemetry.addData(TAG, "Test Stopped");
        currentState = new StopState();
    }

    private class MoveForwardState extends State {

        public MoveForwardState(){
            nextState = new WaitStateOne();
            DriveTrainAuto.moveInches(24,  1);
        }

        public void update(){
            done = DriveTrainAuto.isDone();
        }

    }

    private class WaitStateOne extends State {

        private int counter = 0;

        public WaitStateOne(){
            nextState = new MoveBackwardState();
            DriveTrainAuto.moveInches(0,0);
        }

        public void update(){
            counter++;
            done = counter == 20;
        }

    }

    private class MoveBackwardState extends State {

        public MoveBackwardState(){
            nextState = new WaitStateTwo();
            DriveTrainAuto.moveInches(24,  -1);
        }

        public void update(){
            done = DriveTrainAuto.isDone();
        }

    }

    private class WaitStateTwo extends State {

        private int counter = 0;

        public WaitStateTwo(){
            nextState = new RotateRightState();
            DriveTrainAuto.moveInches(0,0);
        }

        public void update(){
            counter++;
            done = counter == 20;
        }

    }

    private class RotateRightState extends State {

        public RotateRightState(){
            nextState = new WaitStateThree();
            DriveTrainAuto.rotateDegrees(-90, 1);
        }

        public void update(){
            done = DriveTrainAuto.isDone();
        }

    }

    private class WaitStateThree extends State {

        private int counter = 0;

        public WaitStateThree(){
            nextState = new RotateLeftState();
            DriveTrainAuto.moveInches(0, 0);
        }

        public void update(){
            counter++;
            done = counter == 20;
        }

    }

    private class RotateLeftState extends State {

        public RotateLeftState(){
            nextState = new StopState();
            DriveTrainAuto.rotateDegrees(90, 1);
        }

        public void update(){
            done = DriveTrainAuto.isDone();
        }

    }

    private class StopState extends State {

        public StopState(){
            nextState = new StopState();
            DriveTrainAuto.moveInches(0,0);
        }

        public void update(){

        }

    }
}
