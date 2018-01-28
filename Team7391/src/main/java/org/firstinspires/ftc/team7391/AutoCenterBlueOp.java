package org.firstinspires.ftc.team7391;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

/**
 * Created by Ishaan on 11/13/2016.
 */
@Autonomous(name = "Tournament: Blue Center" + "", group = "Tournament")
@Disabled
public class AutoCenterBlueOp extends AutoCenterOp{
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
