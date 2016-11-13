package org.firstinspires.ftc.team7391;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by nikashkhanna on 10/23/2016.
 */
@Autonomous(name = "Auto: DriveTest" + "", group = "Practice")
public class AutoDriveTestOp extends AutoOpBase
{
    private static final String TAG = AutoDriveTestOp.class.getSimpleName();
    private static final int INFINITE_WAIT = 0;

    @Override
    public void init()
    {
        super.init();
        stepsList.add(new WaitState(0));//press a on gamepad 1
        stepsList.add(new DrivePosition1State());

        stepsList.add(new WaitState(0));//press a on gamepad 1
        stepsList.add(new DrivePosition2State());

        stepsList.add(new WaitState(0));//press a on gamepad 1
        stepsList.add(new DrivePosition1State());

        stepsList.add(new WaitState(0));//press a on gamepad 1
        stepsList.add(new MoveState(24,.30));
        stepsList.add(new WaitState(0));//press a on gamepad 1
        stepsList.add(new MoveState(-24,.30));
        stepsList.add(new WaitState(0));//press a on gamepad 1
        stepsList.add(new MoveState(24,.40));
        stepsList.add(new WaitState(0));//press a on gamepad 1
        stepsList.add(new MoveState(-24,.40));

        stepsList.add(new WaitState(0));//press a on gamepad 1
        stepsList.add(new RotateState(90,.3));
        stepsList.add(new WaitState(0));//press a on gamepad 1
        stepsList.add(new RotateState(90,.3));


        stepsList.add(new WaitState(0));//press a on gamepad 1
        stepsList.add(new RotateState(-90,.3));
        stepsList.add(new WaitState(0));//press a on gamepad 1
        stepsList.add(new RotateState(-90,.3));


        stepsList.add(new WaitState(0));//press a on gamepad 1
        stepsList.add(new RotateState(45,.3));
        stepsList.add(new WaitState(0));//press a on gamepad 1
        stepsList.add(new RotateState(45,.3));


        stepsList.add(new WaitState(0));//press a on gamepad 1
        stepsList.add(new RotateState(-45,.3));
        stepsList.add(new WaitState(0));//press a on gamepad 1
        stepsList.add(new RotateState(-45,.3));


    }


    @Override
    public void stop()
    {
        telemetry.addData(TAG, "Test Stopped");
        currentState = new StopState();
    }

}
