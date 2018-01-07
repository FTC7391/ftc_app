package org.firstinspires.ftc.team7391;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by ArjunVerma on 1/7/18.
 */
@Autonomous(name = "Tournament: Zone One" + "", group = "Tournament")
//@Disabled

public class AutoZoneOneOp extends AutoOpBase {
    private static final String TAG = AutoOp.class.getSimpleName();
    protected static int isRed = 1; //1 if we are red, -1 if we are blue

    @Override
    public void init()
    {
        super.init();

        FTC7391PrintWriter data1Writer = new FTC7391PrintWriter("Alliance" , "color");

        stepsList.add(new MoveState(20, 0.5));

        stepsList.add(new StopState());

    }
}
