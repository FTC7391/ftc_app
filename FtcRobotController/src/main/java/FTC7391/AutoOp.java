package FTC7391;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Allana on 10/3/2015.
 */
public class AutoOp extends AutoOpBase
{
    private static final String TAG = AutoOp.class.getSimpleName();
    protected static int isRed = 1; //1 if we are red, -1 if we are blue


    @Override
    public void init()
    {
        super.init();


    }

    @Override
    public void loop(){
        if (currentState != null && !currentState.update()) return;
        step++;
        switch(step) {
            case 1:
                currentState = new MoveState(-24, 1);
                break;
            case 2:
                currentState = new WaitState(0);
                break;
            case 3:
                currentState = new RotateState(50 * isRed, 1);
                break;
            case 4:
                currentState = new WaitState(0);
                break;
            case 5:
                currentState = new MoveState(-58, 1);
                break;
            case 6:
                currentState = new WaitState(0);
                break;
            case 7:
                currentState = new RotateState(50 * isRed, 1);
                break;
            case 8:
                currentState = new WaitState(0);
                break;
            case 9:
                currentState = new MoveState(-26, 1);
                break;
            case 10:
                currentState = new WaitState(0);
                break;
            case 11:
                currentState = new StickState();
                break;
            case 12:
                currentState = new WaitState(0);
                break;
            case 13:
                currentState = new StickMoveState (6, 1);
                break;
            case 14:
                currentState = new WaitState(0);
                break;
            case 15:
                currentState = new MoveState(48,1);
                break;
            case 16:
                currentState = new WaitState(0);
                break;
            case 17:
                currentState = new RotateState(-130 * isRed, 1);
                break;
            case 18:
                currentState = new WaitState(0);
                break;
            case 19:
                currentState = new MoveState(48, 1);
                break;
            default:
                currentState = new StopState();
                break;
        }
    }


}
