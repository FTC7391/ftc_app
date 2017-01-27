package org.firstinspires.ftc.team7391;


/**
 * Created by Allana on 10/3/2015.
 */
public abstract class AutoBeaconOp extends AutoOpBase
{
    private static final String TAG = AutoBeaconOp.class.getSimpleName();
    protected static int isRed = 1; //1 if we are red, -1 if we are blue

    private static final double DISTANCE_BETWEEN_BUTTONS = 5.1; //5.5
    private static final double BUTTON_DISTANCE_2ND_BEACON = 4.5; //5.5


    @Override
    public void init()
    {
        super.init();

        FTC7391PrintWriter data1Writer = new FTC7391PrintWriter("Alliance" , "color");
        setRed();

        stepsList.add(new DrivePosition1State());
        //stepsList.add(new WaitState(0));
        stepsList.add(new MoveState(-7.6, DEFAULT_MOVE_POWER));
        //stepsList.add(new WaitState(0));
        stepsList.add(new RotateState(35*isRed,DEFAULT_TURN_POWER));
        //stepsList.add(new WaitState(0));
        stepsList.add(new MoveState(-71, DEFAULT_MOVE_POWER));
        //stepsList.add(new WaitState(0));
        stepsList.add(new RotateState(-32*isRed,DEFAULT_TURN_POWER));//-34
        //stepsList.add(new WaitState(0));

        //stepsList.add(new MoveState(-31.5, DEFAULT_MOVE_POWER)); //Moved .5 to 1 inch too far
        stepsList.add(new MoveState(-30.7, DEFAULT_MOVE_POWER));

        stepsList.add(new WaitState(0)); //-5.1, 3, 5 , 35(37), 5.1, 2.5
        stepsList.add(new MoveToColor(isRed, -5.3, DEFAULT_MOVE_POWER));
        stepsList.add(new WaitState(0));
        stepsList.add(new MoveState(3, DEFAULT_MOVE_POWER/2));  //move past first button before deploying pusher
        //stepsList.add(new WaitState(0));
        stepsList.add(new PusherState(isRed, true));   //true = deploy
        //stepsList.add(new WaitState(0));
        stepsList.add(new MoveState(5, DEFAULT_MOVE_POWER/2));
        //stepsList.add(new WaitState(0));
        stepsList.add(new PusherState(isRed, false));   //false = retract

        stepsList.add(new WaitState(0));
         //stepsList.add(new MoveToWhiteLineState(-46, 0.2));
        stepsList.add(new MoveToSecondBeacon(33.5,DEFAULT_MOVE_POWER )); //power was   .4, 34

        stepsList.add(new WaitState(0));
        stepsList.add(new MoveToColor(isRed, BUTTON_DISTANCE_2ND_BEACON, DEFAULT_MOVE_POWER)); //5.1
        stepsList.add(new WaitState(0));
        stepsList.add(new MoveState(3, DEFAULT_MOVE_POWER/2));  //move past first button before deploying pusher
        //stepsList.add(new WaitState(0));
        stepsList.add(new PusherState(isRed, true));   //true = deploy
        //stepsList.add(new WaitState(0));
        stepsList.add(new MoveState(5, DEFAULT_MOVE_POWER/2));
        //stepsList.add(new WaitState(0));
        stepsList.add(new PusherState(isRed, false));   //false = retract


        stepsList.add(new WaitState(0));//42, 12, -82, 10
        stepsList.add(new MoveToRightButtonBeacon2(BUTTON_DISTANCE_2ND_BEACON, DEFAULT_MOVE_POWER));
        //stepsList.add(new WaitState(0));
        stepsList.add(new RotateState(40*isRed,DEFAULT_TURN_POWER));//10, 15,-10,15 was original
        //stepsList.add(new WaitState(0));
        stepsList.add(new MoveState(15, DEFAULT_MOVE_POWER)); //20 too far That is in middle of ramp, 15 too far
        //stepsList.add(new WaitState(0));
        stepsList.add(new RotateState(-75*isRed,DEFAULT_TURN_POWER));
        //stepsList.add(new WaitState(0));
        stepsList.add(new MoveState(12, DEFAULT_MOVE_POWER)); //15 too far


//        stepsList.add(new RotateState(45,1));
//        stepsList.add(new WaitState(0));
//        stepsList.add(new MoveState(-15, 1));
//        stepsList.add(new WaitState(0));
//        stepsList.add(new RotateState(-90,1));
//        stepsList.add(new WaitState(0));

        stepsList.add(new StopState());




    }

    public abstract void setRed();

}
