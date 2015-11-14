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
        currentState = new MoveState(24, 1);
        step = 1;
    }

    @Override
    public void loop(){
        if (!currentState.update()) return;
        step++;
        switch (step){
            case 2: currentState = new WaitState(); break;
            case 3: currentState = new MoveState(24, -1);break;
            case 4: currentState = new WaitState(); break;
            case 5: currentState = new RotateState(-90, 1); break;
            case 6: currentState = new WaitState(); break;
            case 7: currentState = new RotateState(90,1); break;
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

        public WaitState(){
            DriveTrainAuto.moveInches(0,0);
        }

        @Override
        public boolean update(){
            counter++;
            return counter == 20;
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
