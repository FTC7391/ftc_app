package org.firstinspires.ftc.team7391;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Allana on 10/3/2015.
 */
@Autonomous(name = "Auto: ColorTest" , group = "Practice")
public class AutoColorTestOp extends AutoOpBase
{
    private static final String TAG = AutoColorTestOp.class.getSimpleName();
    private static final int INFINITE_WAIT = 0;

    @Override
    public void init()
    {
        super.init();

        stepsList.add(new ColorState());

    }


    @Override
    public void stop()
    {
        telemetry.addData(TAG, "Test Stopped");
        currentState = new StopState();
    }

}
