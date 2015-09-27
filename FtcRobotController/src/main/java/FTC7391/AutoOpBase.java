package FTC7391;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Allana Evans on 9/26/15.
 */
public class AutoOpBase extends OpMode {

    private static final String TAG = AutoOpBase.class.getSimpleName();
    private static DriveTrain driveTrain;
    private State currentState;


    public void init(){
        telemetry.addData(TAG, "OpMode Init");
        driveTrain = new DriveTrain(hardwareMap);
        //initialize current state

    }

    public void loop(){
        currentState.update();
        currentState = currentState.next();
    }

    public void stop(){

    }

    protected abstract class State{

        private boolean done = false;
        private State nextState;

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
