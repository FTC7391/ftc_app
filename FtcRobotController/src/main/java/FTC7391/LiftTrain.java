package FTC7391;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by ArjunVerma on 10/3/15.
 */
public class LiftTrain {

    private static DcMotor liftHigh;
    private static DcMotor liftLow;

    private static int driveModeTicks = 0; //to be added
    private int currentTicks1 = liftHigh.getCurrentPosition();
    private int currentTicks2 = liftLow.getCurrentPosition();
    private int overallCurrent = currentTicks1 + currentTicks2;
    private static int originalTicks = 0; //to be added

    private static final int TOTAL_TICKS = 1120;
    private static final int TOTAL_DEGREES = 360;
    private static final double DIAMETER= 1.0;  //??????????
    private static final int OFFSET = (int) (TOTAL_TICKS / (Math.PI * DIAMETER));


    public LiftTrain(){}

    public void setDriveMode(){

        if(currentTicks1 < driveModeTicks) {
            while (currentTicks1 <= driveModeTicks) {
                liftHigh.setPower(10);
                currentTicks1 = liftHigh.getCurrentPosition();
            }
        }
        else if(currentTicks1 > driveModeTicks) {
            while(currentTicks1 >= driveModeTicks){
                liftHigh.setPower(-10);
                currentTicks1 = liftHigh.getCurrentPosition();
            }
        }
        else{}

    }

    public void raise(int targetTicks){

        int differentTicks = targetTicks - currentTicks1;
        double target = 0.5*differentTicks + targetTicks;

        LiftTrain.setMotorTargetPosition((int)target, (int)target);
        LiftTrain.setPowerOfMotors(1, 1);
        LiftTrain.isDone();
    }

    public void lower(int targetTicks){

        int differentTicks = currentTicks1 - targetTicks;
        double target = targetTicks - 0.5*differentTicks;

        LiftTrain.setMotorTargetPosition((int)target, (int)target);
        LiftTrain.setPowerOfMotors(-1, -1);
        LiftTrain.isDone();


    }





    public static boolean isDone() {
//        if(getCurrentPosition() >= targetMotorPosition)
        return true;
    }

    public static void setMotorTargetPosition(int liftHighDifference, int liftLowDifference) {
        liftHigh.setTargetPosition(liftHigh.getCurrentPosition() + liftHighDifference);
        liftLow.setTargetPosition(liftHigh.getCurrentPosition() + liftLowDifference);

    }

    public static void setPowerOfMotors(double liftHighPower, double liftLowPower) {
        liftHigh.setPower(liftHighPower);
        liftLow.setPower(liftLowPower);

    }

}
