package org.firstinspires.ftc.team7391;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

/**
 * Created by Allana on 10/3/2015.
 */
//@Autonomous(name = "Auto: Red" + "", group = "Tournament")
@Disabled







public class AutoRedOp extends AutoOp
{
    private static final String TAG = AutoRedOp.class.getSimpleName();

    @Override
    public void init()
    {
        super.init();
    }

    public void setRed(){
        isRed = 1;
        Log.i("Auto", "Red");
    }


}
