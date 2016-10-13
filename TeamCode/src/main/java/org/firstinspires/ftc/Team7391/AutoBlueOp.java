package FTC7391;

import android.util.Log;

/**
 * Created by Allana on 10/3/2015.
 */
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
