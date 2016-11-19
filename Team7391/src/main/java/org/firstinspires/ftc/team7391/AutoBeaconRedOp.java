package org.firstinspires.ftc.team7391;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Ishaan on 11/13/2016.
 */
@Autonomous(name = "Tournament: Red Beacon" + "", group = "Tournament")
//@Disabled

public class AutoBeaconRedOp extends AutoBeaconOp{
    private static final String TAG = AutoBlueOp.class.getSimpleName();

    @Override
    public void init()
    {
        super.init();
    }

    public void setRed(){
        isRed = 1;
        Log.i("FTC7391","Auto: " + "Red Beacon");
    }

}
