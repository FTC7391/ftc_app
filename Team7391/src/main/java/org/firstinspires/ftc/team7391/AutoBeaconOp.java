package org.firstinspires.ftc.team7391;


/**
 * Created by Allana on 10/3/2015.
 */
public abstract class AutoBeaconOp extends AutoOpBase
{
    private static final String TAG = AutoBeaconOp.class.getSimpleName();
    protected static int isRed = 1; //1 if we are red, -1 if we are blue


    @Override
    public void init()
    {
        super.init();

        FTC7391PrintWriter data1Writer = new FTC7391PrintWriter("Alliance" , "color");
        setRed();


        stepsList.add(new WaitState(0));
        stepsList.add(new MoveState(8.6, 0.4));
        stepsList.add(new WaitState(0));
        stepsList.add(new RotateState(-40*isRed,0.5));
        stepsList.add(new WaitState(0));
        stepsList.add(new MoveState(77.77, 0.4));
        stepsList.add(new WaitState(0));
        stepsList.add(new RotateState(31*isRed,0.5));
        stepsList.add(new WaitState(0));
        stepsList.add(new MoveState(26, 0.4));
        stepsList.add(new WaitState(0));
        stepsList.add(new MoveToColor(isRed, 5.5, 0.4));
        stepsList.add(new MoveState(-3, 0.4));  //move past first button before deploying pusher
        //stepsList.add(new WaitState(0));
        stepsList.add(new PusherState(isRed, true));   //true = deploy
        //stepsList.add(new WaitState(0));
        stepsList.add(new MoveState(-5, 0.2));
        //stepsList.add(new WaitState(0));
        stepsList.add(new PusherState(isRed, false));   //false = retract

        //stepsList.add(new StickDrivePositionState());
        stepsList.add(new WaitState(0));
        //stepsList.add(new DrivePosition1State());
        stepsList.add(new MoveToSecondState());
        stepsList.add(new WaitState(0));
        //stepsList.add(new DrivePosition2State());
        //stepsList.add(new WaitState(0));
        stepsList.add(new MoveToColor(isRed, -5.5, 0.4));
        stepsList.add(new MoveState(-3, 0.4));  //move past first button before deploying pusher
        //stepsList.add(new WaitState(0));
        stepsList.add(new PusherState(isRed, true));   //true = deploy
        //stepsList.add(new WaitState(0));
        stepsList.add(new MoveState(-5, 0.2));
        //stepsList.add(new WaitState(0));
        stepsList.add(new PusherState(isRed, false));   //false = retract


//        stepsList.add(new RotateState(45,1));
//        stepsList.add(new WaitState(0));
//        stepsList.add(new MoveState(-15, 1));
//        stepsList.add(new WaitState(0));
//        stepsList.add(new RotateState(-90,1));
//        stepsList.add(new WaitState(0));

        // stepsList.add(new StickState());
        //stepsList.add(new WaitState(0));
        //stepsList.add(new StickMoveState (6, 1));
        //stepsList.add(new WaitState(0));
        //stepsList.add(new MoveState(-6, 1));
        //stepsList.add(new WaitState(0));

        //stepsList.add(new RotateState(-90,1));
        //stepsList.add(new WaitState(0));
        stepsList.add(new StopState());




/* End of 2016-01-08 Meeting
        // stepsList.add(new WaitState(0));
        stepsList.add(new DrivePosition1State());
        //stepsList.add(new WaitState(0));
        //stepsList.add(new DrivePosition2State());
        //stepsList.add(new WaitState(0));
        stepsList.add(new MoveState(-95, 1));
        //stepsList.add(new WaitState(0));
        stepsList.add(new RotateState(45,1));
        //stepsList.add(new WaitState(0));
        //stepsList.add(new MoveState(-6, 1));
        //stepsList.add(new WaitState(0));
        stepsList.add(new RotateState(135, 1));
 */
//        stepsList.add(new RunToPositionState(20));
//        stepsList.add(new DrivePosition1State());
//        stepsList.add(new WaitState (0));
//        stepsList.add(new DrivePosition2State());
//        stepsList.add(new WaitState (0));
//        stepsList.add(new MoveState(24, 1));
//        stepsList.add(new WaitState(0));
//        stepsList.add(new RotateState(60 * isRed, 1));
//        stepsList.add(new WaitState(0));
//        stepsList.add(new MoveState(75, 1));
//        stepsList.add(new WaitState(0));
//        stepsList.add(new RotateState(40 * isRed, 1));
//        stepsList.add(new WaitState(0));
//        stepsList.add(new StickLiftState());
//        stepsList.add(new WaitState(0));
//        stepsList.add(new MoveState(18, 1));
//        stepsList.add(new WaitState(0));
//        stepsList.add(new StickState());
//        stepsList.add(new WaitState(0));
//        stepsList.add(new StickMoveState (-6, 1));
//        stepsList.add(new WaitState(0));
//        stepsList.add(new MoveState(-48,1));
//        stepsList.add(new WaitState(0));
//        stepsList.add(new RotateState(-130 * isRed, 1));
//        stepsList.add(new WaitState(0));
//        stepsList.add(new MoveState(-48, 1));
//        stepsList.add(new StopState());

    }

    public abstract void setRed();

}
