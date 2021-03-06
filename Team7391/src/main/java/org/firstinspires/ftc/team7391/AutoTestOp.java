package org.firstinspires.ftc.team7391;

/**
 * Created by Allana on 10/3/2015.
 */

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

//@Autonomous(name = "Auto: TestOp" + "", group = "Test")
//@Disabled
public class AutoTestOp extends AutoOpBase
{
    private static final String TAG = AutoTestOp.class.getSimpleName();
    private static final int INFINITE_WAIT = 0;

    @Override
    public void init()
    {
        super.init();
        FTC7391PrintWriter data1Writer = new FTC7391PrintWriter("Alliance" , "color");
        data1Writer.print("red");
        int isRed = -1;



        stepsList.add(new WaitState(0));
        stepsList.add(new MoveState(12, 1));
        stepsList.add(new WaitState(0));
        stepsList.add(new MoveState(-12, 1));

        stepsList.add(new WaitState(0));
        stepsList.add(new MoveState(24, 1));
        stepsList.add(new WaitState(0));
        stepsList.add(new MoveState(-24, 1));

        stepsList.add(new WaitState(0));
        stepsList.add(new MoveLateralState(24, 1));
        stepsList.add(new WaitState(0));
        stepsList.add(new MoveLateralState(-24, 1));

        stepsList.add(new WaitState(0));
        stepsList.add(new RotateState(90, 1));
        stepsList.add(new WaitState(0));
        stepsList.add(new RotateState(-90, 1));

//
//
//        stepsList.add(new WaitState(0));
//        stepsList.add(new MoveState(12, 1));
//        stepsList.add(new WaitState(0));
//        stepsList.add(new MoveState(-12, 1));
//        stepsList.add(new WaitState(0));
//        stepsList.add(new RotateState(-90 * isRed, 1));
//        stepsList.add(new WaitState(0));
//        stepsList.add(new RotateState(-90 * isRed, 1));
//        stepsList.add(new WaitState(0));
////        stepsList.add(new RotateState(90 * isRed, 1));
////        stepsList.add(new WaitState(0));
////        stepsList.add(new RotateState(90 * isRed, 1));
////
////        stepsList.add(new WaitState(0));
////        stepsList.add(new ClimbPositionState());
////        stepsList.add(new WaitState(0));
//        //stepsList.add(new DrivePositionState());
//
//        //stepsList.add(new WaitState(0));
//
//
//        stepsList.add(new RotateState(180 * isRed, 1));
//        stepsList.add(new WaitState(0));
//        stepsList.add(new RotateState(270 * isRed, 1));
//        stepsList.add(new WaitState(0));
//        stepsList.add(new RotateState(360 * isRed, 1));
//        stepsList.add(new WaitState(0));
//        stepsList.add(new RotateState(-90 * isRed, 1));
//        stepsList.add(new WaitState(0));
//        stepsList.add(new RotateState(-180 * isRed, 1));
//        stepsList.add(new WaitState(0));
//        stepsList.add(new RotateState(-270 * isRed, 1));
//        stepsList.add(new WaitState(0));
//        stepsList.add(new RotateState(-360* isRed, 1));
//
        stepsList.add(new StopState());

    }

}
