package org.firstinspires.ftc.team7391;

import android.util.Log;

/**
 * Created by Ishaan on 11/13/2016.
 */
public class AutoBeaconBlueOp extends AutoBeaconOp{
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
