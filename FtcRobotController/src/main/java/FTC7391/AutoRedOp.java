package FTC7391;

/**
 * Created by Allana on 10/3/2015.
 */
public class AutoRedOp extends AutoOp
{
    private static final String TAG = AutoRedOp.class.getSimpleName();

    @Override
    public void init()
    {
        super.init();
        isRed = 1;
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
