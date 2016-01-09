package FTC7391;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Allana on 10/3/2015.
 */
public abstract class AutoOp extends AutoOpBase
{
    private static final String TAG = AutoOp.class.getSimpleName();
    protected static int isRed = 1; //1 if we are red, -1 if we are blue


    @Override
    public void init()
    {
        super.init();
        
        stepsList.add(new RunToPositionState(20));
        stepsList.add(new DrivePosition1State());
        stepsList.add(new WaitState (0));
        stepsList.add(new DrivePosition2State());
        stepsList.add(new WaitState (0));
        stepsList.add(new MoveState(24, 1));
        stepsList.add(new WaitState(0));
        stepsList.add(new RotateState(60 * isRed, 1));
        stepsList.add(new WaitState(0));
        stepsList.add(new MoveState(75, 1));
        stepsList.add(new WaitState(0));
        stepsList.add(new RotateState(40 * isRed, 1));
        stepsList.add(new WaitState(0));
        stepsList.add(new StickLiftState());
        stepsList.add(new WaitState(0));
        stepsList.add(new MoveState(18, 1));
        stepsList.add(new WaitState(0));
        stepsList.add(new StickState());
        stepsList.add(new WaitState(0));
        stepsList.add(new StickMoveState (-6, 1));
        stepsList.add(new WaitState(0));
        stepsList.add(new MoveState(-48,1));
        stepsList.add(new WaitState(0));
        stepsList.add(new RotateState(-130 * isRed, 1));
        stepsList.add(new WaitState(0));
        stepsList.add(new MoveState(-48, 1));
        stepsList.add(new StopState());

    }

    public abstract void setRed();

}
