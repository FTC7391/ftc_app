package org.firstinspires.ftc.team7391;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Allana on 10/3/2015.
 */

@Autonomous(name = "Auto: Test Beacon" + "", group = "Practice")
public abstract class AutoBeaconTestOp extends AutoOpBase
{
    private static final String TAG = AutoBeaconTestOp.class.getSimpleName();
    protected static int isRed = 1; //1 if we are red, -1 if we are blue

    private static final double DISTANCE_BETWEEN_BUTTONS = 5.1; //5.5
    private static final double BUTTON_DISTANCE_2ND_BEACON = 4.5; //5.5


    @Override
    public void init()
    {
        super.init();

        //FTC7391PrintWriter data1Writer = new FTC7391PrintWriter("Alliance" , "color");
       // setRed();
        telemetry.addData(TAG, "Test Started");

        //stepsList.add(new DrivePosition1State());
        //stepsList.add(new WaitState(0));
        stepsList.add(new MoveState(3,DEFAULT_MOVE_POWER));
        stepsList.add(new WaitState(0)); //-5.1, 3, 5 , 35(37), 5.1, 2.5
        stepsList.add(new MoveToColor(-1, -5.3, DEFAULT_MOVE_POWER));
        stepsList.add(new WaitState(0));
        stepsList.add(new MoveState(3, DEFAULT_MOVE_POWER/2));  //move past first button before deploying pusher

        stepsList.add(new StopState());




    }

    public void stop()
    {
        telemetry.addData(TAG, "Test Stopped");
        currentState = new StopState();
    }

    //public abstract void setRed();

}
