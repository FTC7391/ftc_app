package FTC7391;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Allana Evans on 9/26/15.
 */
public class AutoOpBase extends OpMode {

    private static final String TAG = AutoOpBase.class.getSimpleName();
    protected State currentState;


    public void init(){
        telemetry.addData(TAG, "AutoOp Init");
        DriveTrainAuto.init(hardwareMap);
        //initialize current state

    }

    public void loop(){
        currentState.update();
        currentState = currentState.next();
    }

    public void stop(){

    }

    protected abstract class State{

        protected boolean done = false;
        protected State nextState;

        public State(){
            //initialize nextState
        }

        public abstract void update(); //perform state actions

        public State next(){
            if (!done) return this;
            else return nextState;
        }


    }

}
