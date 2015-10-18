package FTC7391;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

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
        //driveTrain = new DriveTrainAuto(hardwareMap);
        driveTrainState = DriveTrainState.FORWARD;
       // DcMotor motorFrontRight = hardwareMap.dcMotor.get("motor_2");
        //DcMotor motorFrontLeft = hardwareMap.dcMotor.get("motor_1");
        //driveTrain.moveInches(5, .5);
        //motorFrontRight.setPower(0.3);
        //motorFrontLeft.setPower(0.3);
        //driveTrain.lateralMoveInches(true, 10, .50);
        //driveTrain.setPowerOfMotors(0.5,0.5,0.5,0.5);
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
