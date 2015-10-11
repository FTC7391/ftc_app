package FTC7391;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Allana on 10/3/2015.
 */
public class AutoOp extends AutoOpBase
{
    private static final String TAG = AutoOp.class.getSimpleName();

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
            nextState = new StopState();
            DriveTrainAuto.moveInches(10,  0.5);
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
