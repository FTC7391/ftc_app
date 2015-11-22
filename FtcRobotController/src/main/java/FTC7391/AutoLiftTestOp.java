package FTC7391;

/**
 * Created by Allana on 10/3/2015.
 */
public class AutoLiftTestOp extends AutoOpBase
{
    private static final String TAG = AutoLiftTestOp.class.getSimpleName();
    private static final int INFINITE_WAIT = 0;

    @Override
    public void init()
    {
        super.init();

    }

    @Override
    public void loop(){
        if (currentState != null && !currentState.update()) return;
        step++;
        switch (step){
            case 1: currentState = new WaitState(0); break;
//            case 2: currentState = new RotateState(90, .8);break;
//            case 3: currentState = new WaitState(0); break;
//            case 4: currentState = new RotateState(-90, .8);break;
//            case 5: currentState = new WaitState(0); break;
//            case 2: currentState = new MoveState(24, .7);break;
//            case 3: currentState = new WaitState(0); break;
//            case 4: currentState = new MoveState(-24, .7);break;
//            case 5: currentState = new WaitState(0); break;
//            case 6: currentState = new RotateState(90, 1.0); break;
//            case 7: currentState = new WaitState(0); break;
//            case 8: currentState = new RotateState(-90, 1.0); break;
//            case 9: currentState = new WaitState(0); break;
            case 2: currentState = new RotateState(90, 1.0); break;
            case 3: currentState = new WaitState(0); break;
            case 4: currentState = new RotateState(-90, 1.0); break;
            case 5: currentState = new WaitState(0); break;
            case 6: currentState = new RotateState(180, 1.0); break;
            case 7: currentState = new WaitState(0); break;
            case 8: currentState = new RotateState(-180, 1.0); break;
            case 9: currentState = new WaitState(0); break;
            case 10: currentState = new RotateState(360, 1.0); break;
            case 11: currentState = new WaitState(0); break;
            case 12: currentState = new RotateState(-360, 1.0); break;
            case 14: currentState = new WaitState(0); break;
            case 15: currentState = new RotateState(360, 1.0); break;
            case 16: currentState = new WaitState(0); break;
            case 17: currentState = new RotateState(-360, 1.0); break;
            case 18: currentState = new StopState(); break;
        }
    }

    @Override
    public void stop()
    {
        telemetry.addData(TAG, "Test Stopped");
        currentState = new StopState();
    }

}
