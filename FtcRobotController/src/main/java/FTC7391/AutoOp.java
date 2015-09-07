package FTC7391;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Dylan on 7/19/2015.
 */
public class AutoOp extends OpMode
{
    private static final String TAG = AutoOp.class.getSimpleName();
    private static DriveTrain driveTrain;
    enum DriveTrainState {FORWARD, STOP, BACKWARD}
    DriveTrainState driveTrainState;
    public AutoOp(){

    }

    @Override
    public void init()
    {
        telemetry.addData(TAG, "OpMode Init");
        driveTrain = new DriveTrain(hardwareMap);
        driveTrainState = DriveTrainState.FORWARD;

        driveTrain.moveInches(24, 50);
        driveTrain.lateraMoveInches(true, 10, 50);
    }
    @Override
    public void loop()
    {
//       switch(driveTrainState)
//       {
//           case FORWARD:
//               if(driveTrain.isDone())
//               {
//                   driveTrainState = DriveTrainState.STOP;
//                   telemetry.addData(TAG, "Moved FORWARD");
//               }
//           case STOP:
//           {
//               driveTrainState = DriveTrainState.BACKWARD;
//               driveTrain.moveInches(-24.0, 100);
//           }
//           case BACKWARD:
//               if(driveTrain.isDone())
//               {
//                   driveTrainState = DriveTrainState.STOP;
//                   telemetry.addData(TAG, "Moved Backward");
//               }
//       }
//       if(driveTrain.isDone() && driveTrainState == DriveTrainState.FORWARD)
//       {
//           driveTrainState = DriveTrainState.STOP;
//           telemetry.addData(TAG, "Moved Forward");
//       }
//       else if(driveTrainState == DriveTrainState.STOP)
//       {
//           driveTrainState = DriveTrainState.BACKWARD;
//           driveTrain.moveInches(-24.0, 100);
//       }
//       else if(driveTrain.isDone() && driveTrainState == DriveTrainState.BACKWARD)
//       {
//           driveTrainState = DriveTrainState.STOP;
//           telemetry.addData(TAG, "Moved Backward");
//       }
        telemetry.addData(TAG, "Test Moving");
        //driveTrain.moveInches(1, 10);

    }
    @Override
    public void stop()
    {
        telemetry.addData(TAG, "Test Stopped");
        //driveTrain.moveInches(1, 0);
    }
}
