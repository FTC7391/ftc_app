package org.firstinspires.ftc.team7391;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by ArjunVerma on 1/7/18.
 */
@Autonomous(name = "Tournament: Zone One" + "", group = "Tournament")
//@Disabled

public class AutoZoneTwoOp extends AutoOpBase {
    private static final String TAG = AutoOp.class.getSimpleName();
    protected static int isRed = 1; //1 if we are red, -1 if we are blue

    @Override
    public void init()
    {
        super.init();

        FTC7391PrintWriter data1Writer = new FTC7391PrintWriter("Alliance" , "color");

        stepsList.add(new MoveState(27, 0.8));
        //stepsList.add(new WaitState(0));
        //stepsList.add(new MoveLateralState(24,0.8));
        //stepsList.add(new WaitState(0));
       // stepsList.add(new RotateState(90,0.8));
        //stepsList.add(new WaitState(0));
        stepsList.add(new LiftPosition2State());

        stepsList.add(new StopState());

    }
}
