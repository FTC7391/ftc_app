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

}
