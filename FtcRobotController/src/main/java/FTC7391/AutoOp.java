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
        stepsList.add(new MoveState(-24, 1));
        stepsList.add(new WaitState(0));
        stepsList.add(new RotateState(50 * isRed, 1));
        stepsList.add(new WaitState(0));
        stepsList.add(new MoveState(-58, 1));
        stepsList.add(new WaitState(0));
        stepsList.add(new RotateState(50 * isRed, 1));
        stepsList.add(new WaitState(0));
        stepsList.add(new MoveState(-26, 1));
        stepsList.add(new WaitState(0));
        stepsList.add(new StickState());
        stepsList.add(new WaitState(0));
        stepsList.add(new StickMoveState (6, 1));
        stepsList.add(new WaitState(0));
        stepsList.add(new MoveState(48,1));
        stepsList.add(new WaitState(0));
        stepsList.add(new RotateState(-130 * isRed, 1));
        stepsList.add(new WaitState(0));
        stepsList.add(new MoveState(48, 1));
        stepsList.add(new StopState());

    }

    @Override
    public void loop(){
        if (currentState != null && !currentState.update()) return;
        step++;
        currentState = stepsList.get(step);

    }


}
