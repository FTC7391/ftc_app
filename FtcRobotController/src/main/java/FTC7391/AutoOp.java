package FTC7391;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Dylan on 7/19/2015.
 */
public class AutoOp extends OpMode
{
    private static final String TAG = AutoOp.class.getSimpleName();
    private static DriveTrain dt;
    enum DriveTrainState {FORWARD, STOP, BACKWARD}
    DriveTrainState dtState;
    @Override
    public void init()
    {
        telemetry.addData(TAG, "OpMode Started");
        dtState = DriveTrainState.FORWARD;
        dt.moveInches(24.0, 100);
    }
    @Override
    public void loop()
    {
       switch(dtState)
       {
           case FORWARD:
               if(dt.isDone())
               {
                   dtState = DriveTrainState.STOP;
                   telemetry.addData(TAG, "Moved FORWARD");
               }
           case STOP:
           {
               dtState = DriveTrainState.BACKWARD;
               dt.moveInches(-24.0, 100);
           }
           case BACKWARD:
               if(dt.isDone())
               {
                   dtState = DriveTrainState.STOP;
                   telemetry.addData(TAG, "Moved Backward");
               }
       }
//       if(dt.isDone() && dtState == DriveTrainState.FORWARD)
//       {
//           dtState = DriveTrainState.STOP;
//           telemetry.addData(TAG, "Moved Forward");
//       }
//       else if(dtState == DriveTrainState.STOP)
//       {
//           dtState = DriveTrainState.BACKWARD;
//           dt.moveInches(-24.0, 100);
//       }
//       else if(dt.isDone() && dtState == DriveTrainState.BACKWARD)
//       {
//           dtState = DriveTrainState.STOP;
//           telemetry.addData(TAG, "Moved Backward");
//       }
    }
    @Override
    public void stop()
    {

    }
}
