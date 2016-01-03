package FTC7391;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * TeleOp Mode
 * <p>
 *Enables control of the robot via the gamepad
 */

public class Gyro implements SensorEventListener {

    private static final String TAG = Gyro.class.getSimpleName();
    private static FTC7391PrintWriter dataWriter1;

    private static SensorManager sensorManager;
    private static Sensor rotationSensor;
    private static Sensor gameRotationSensor;

    private static float[] mRotationMatrix = new float[16];
    private static float[] mGameRotationMatrix = new float[16];
    private static float[] mOrientation= new float[3];
    private static float[] mSensorData= null;

    // Create a constant to convert nanoseconds to seconds.
    private static final float NS2S = 1.0f / 1000000000.0f;
    private final double[] deltaRotationVector = new double[4];
    private double[] end = new double[3];

    private float timestamp;

    public Gyro(Context context) {
        dataWriter1 = new FTC7391PrintWriter("Gyro" , "GyroTest1");
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        gameRotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);

    }

    public void start() {
        sensorManager.registerListener(this, rotationSensor,10000);
        sensorManager.registerListener(this, gameRotationSensor,10000);
    }

    public void stop() {
        sensorManager.unregisterListener(this, rotationSensor);
        sensorManager.unregisterListener(this, gameRotationSensor);
    }


    public void onSensorChanged(SensorEvent event) {
       synchronized (this) {

           boolean success = false;

           dataWriter1.print("Time" + timestamp);

           // This timestep's delta rotation to be multiplied by the current rotation
           // after computing it from the gyro sample data.
           if (timestamp != 0) {

               //      // onSensorChanged gets called for each sensor so we have to remember the values
//      if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
//        mAccelerometer = event.values;
//      }
//
               //      if (mAccelerometer != null && mGeomagnetic != null) {
//        float R[] = new float[9];
//        float I[] = new float[9];
//        boolean success = SensorManager.getRotationMatrix(R, I, mAccelerometer, mGeomagnetic);
//        mAccelerometer = null;
//        mGeomagnetic = null;
//
//        if (success) {
//          float orientation[] = new float[3];
//          SensorManager.getOrientation(R, orientation);
//          // at this point, orientation contains the azimuth(direction), pitch and roll values.
//          double azimuth = 180 * orientation[0] / Math.PI;
//          double pitch = 180 * orientation[1] / Math.PI;
//          double roll = 180 * orientation[2] / Math.PI;
//          gyroWriter.printf("gyro:  azimuth:%4.2f  pitch:%4.2f  roll:%4.2f\n", azimuth, pitch, roll);
//
//        }
//      }
               switch (event.sensor.getType()) {
                   case Sensor.TYPE_ROTATION_VECTOR:
                       dataWriter1.printf("ROTATION");
                       //Log.d(TAG, "ROTATION");
                       mSensorData = event.values;
                       calcOrientation(mSensorData);
                       break;

                   case Sensor.TYPE_GAME_ROTATION_VECTOR:
                       dataWriter1.printf("GAME_ROTATION                                                        ");
                       Log.d(TAG, "GAME_ROTATION                                                        ");
                       mSensorData = event.values;
                       calcOrientation(mSensorData);
                       break;
                   default:
                       break;

               }



//
//            final float dT = (event.timestamp - timestamp) * NS2S;
//            // Axis of the rotation sample, not normalized yet.
//            float axisX = event.values[0];
//            float axisY = event.values[1];
//            float axisZ = event.values[2];
//
//            // Calculate the angular speed of the sample
//            double omegaMagnitude = Math.sqrt(axisX*axisX + axisY*axisY + axisZ*axisZ);
//
//            // Normalize the rotation vector if it's big enough to get the axis
//            // (that is, EPSILON should represent your maximum allowable margin of error)
//            if (omegaMagnitude > (Math.PI/30)) {
//                axisX /= omegaMagnitude;
//                axisY /= omegaMagnitude;
//                axisZ /= omegaMagnitude;
//            }
//
//            // Integrate around this axis with the angular speed by the timestep
//            // in order to get a delta rotation from this sample over the timestep
//            // We will convert this axis-angle representation of the delta rotation
//            // into a quaternion before turning it into the rotation matrix.
//            double thetaOverTwo = omegaMagnitude * dT / 2.0f;
//            double sinThetaOverTwo = Math.sin(thetaOverTwo);
//            double cosThetaOverTwo = Math.cos(thetaOverTwo);
//            deltaRotationVector[0] = sinThetaOverTwo * axisX;
//            deltaRotationVector[1] = sinThetaOverTwo * axisY;
//            deltaRotationVector[2] = sinThetaOverTwo * axisZ;
//            deltaRotationVector[3] = cosThetaOverTwo;
//        }
//        float[] drv = new float[deltaRotationVector.length];
//        for(int i = 0; i<drv.length; i++) {
//            drv[i] = (float)(deltaRotationVector[i]);
           }
           timestamp = event.timestamp;
       }
//        float[] deltaRotationMatrix = new float[9];
//        SensorManager.getRotationMatrixFromVector(deltaRotationMatrix, drv);
        // User code should concatenate the delta rotation we computed with the current rotation
        // in order to get the updated rotation.
        // rotationCurrent = rotationCurrent * deltaRotationMatrix;

    }

    void calcOrientation (float [] sensorData){
        SensorManager.getRotationMatrixFromVector(mRotationMatrix, mSensorData);
        //boolean success = SensorManager.remapCoordinateSystem(mRotationMatrix, SensorManager.AXIS_X, SensorManager.AXIS_Z, mRotationMatrix);
        SensorManager.getOrientation(mRotationMatrix, mOrientation);

        // Optionally convert the result from radians to degrees
        mOrientation[0] = (float) Math.toDegrees(mOrientation[0]);
        mOrientation[1] = (float) Math.toDegrees(mOrientation[1]);
        mOrientation[2] = (float) Math.toDegrees(mOrientation[2]);

        dataWriter1.printf("azimuth:%4.2f  pitch:%4.2f  roll:%4.2f\n", mOrientation[0], mOrientation[1], mOrientation[2]);
        Log.d(TAG, "azimuth:" + mOrientation[0] + "  pitch:" + mOrientation[1] + "  roll:" + mOrientation[2]);
    }




    @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

    }
