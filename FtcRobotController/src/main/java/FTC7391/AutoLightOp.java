package FTC7391;

/**
 * Created by Allana on 10/3/2015.
 */
public class AutoLightOp extends AutoOpBase
{
    private static final String TAG = AutoLightOp.class.getSimpleName();

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
            nextState = new LightState();
            DriveTrainAuto.moveInches(10,  0.5);
        }

        public void update(){
            done = DriveTrainAuto.isDone();
        }

    }

    private class LightState extends State {

        public LightState(){
            nextState = new StopState();

        }

        public void update() {done = true;}
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
