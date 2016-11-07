package org.firstinspires.ftc.team7391;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Allana on 10/3/2015.
 */
@Autonomous(name = "Auto: LiftTest" , group = "Practice")
public class AutoLiftTestOp extends AutoOpBase
{
    private static final String TAG = AutoLiftTestOp.class.getSimpleName();
    private static final int INFINITE_WAIT = 0;

    @Override
    public void init()
    {
        super.init();
        stepsList.add(new WaitState(0));//press a on gamepad 1
        stepsList.add(new ClimbPositionState());
        stepsList.add(new WaitState(0));//press a on gamepad 1
        stepsList.add(new ReadyToHangPositionState());

    }


    @Override
    public void stop()
    {
        telemetry.addData(TAG, "Test Stopped");
        currentState = new StopState();
    }

}
