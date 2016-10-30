package org.firstinspires.ftc.team7391;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Allana on 10/3/2015.
 */
//@Autonomous(name = "Auto: Blue" + "", group = "Tournament")
//@Disabled
public class AutoBlueOp extends AutoOp
{
    private static final String TAG = AutoBlueOp.class.getSimpleName();

    @Override
    public void init()
    {
        super.init();
    }

    public void setRed(){
        isRed = -1;
        Log.i("Auto", "Blue");
    }

}
