package FTC7391;

/**
 * Created by Allana on 10/3/2015.
 */
public class AutoTestOp extends AutoOpBase
{
    private static final String TAG = AutoTestOp.class.getSimpleName();
    private static final int INFINITE_WAIT = 0;

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
            case 1: currentState = new MoveState(6, 0.1); break;
            case 2: currentState = new WaitState(0); break;
            case 3: currentState = new MoveState(-6, -0.1);break;
            case 4: currentState = new WaitState(0); break;
            case 5: currentState = new RotateState(-90, 0.1); break;
            case 6: currentState = new WaitState(0); break;
            case 7: currentState = new RotateState(90, 0.1); break;
            case 8: currentState = new StopState(); break;
        }
    }

    @Override
    public void stop()
    {
        telemetry.addData(TAG, "Test Stopped");
        currentState = new StopState();
    }

}
