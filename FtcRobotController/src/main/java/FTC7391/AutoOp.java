package FTC7391;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Allana on 10/3/2015.
 */
public class AutoOp extends AutoOpBase
{
    private static final String TAG = AutoOp.class.getSimpleName();
    protected static final int isRed = 1; //1 if we are red, -1 if we are blue

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
        switch(step) {
            case 1:
                currentState = new MoveState(24, 0.5);
                break;
            case 2:
                currentState = new RotateState(45 * isRed, 0.5);
                break;
            case 3:
                currentState = new MoveState(48, 0.5);
                break;
            case 4:
                currentState = new RotateState(45 * isRed, 0.5);
                break;
            case 5:
                currentState = new MoveState(24, 0.5);
                break;
            case 6:
                currentState = new StopState();
                break;
        }
    }

    @Override
    public void stop()
    {
        telemetry.addData(TAG, "Test Stopped");
        currentState = new StopState();
    }

}
