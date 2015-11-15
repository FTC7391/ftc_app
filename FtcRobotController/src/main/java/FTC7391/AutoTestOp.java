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
        currentState = null;
        step = 0;
    }

    @Override
    public void loop(){
        if (currentState != null && !currentState.update()) return;
        step++;
        switch (step){
            case 1: currentState = new MoveState(6, 0.2); break;
            case 2: currentState = new WaitState(30); break;
            case 3: currentState = new MoveState(-6, -0.4);break;
            case 4: currentState = new WaitState(30); break;
            case 5: currentState = new RotateState(-90, 0.4); break;
            case 6: currentState = new WaitState(30); break;
            case 7: currentState = new RotateState(90, 0.4); break;
            case 8: currentState = new StopState(); break;
        }
    }

    @Override
    public void stop()
    {
        telemetry.addData(TAG, "Test Stopped");
        currentState = new StopState();
    }



    private class MoveState extends State {

        public MoveState(int inches, double power){
            DriveTrainAuto.moveInches(inches,  power);
        }

    }

    private class WaitState extends State {

        private int counter = 0;
        private int waitTime;

        public WaitState(int seconds){
            DriveTrainAuto.moveInches(0,0);
            waitTime = (int)(seconds / 40);
        }

        @Override
        public boolean update(){
            counter++;
            return (counter == waitTime || gamepad1.a);
        }

    }

    private class RotateState extends State {

        public RotateState(int degrees, double power){
            DriveTrainAuto.rotateDegrees(degrees, power);
        }

    }

    private class StopState extends State {

        public StopState(){
            DriveTrainAuto.moveInches(0,0);
        }

        @Override
        public boolean update(){
            return false;
        }

    }
}
