package FTC7391;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Allana on 10/3/2015.
 */
public class AutoOp extends AutoOpBase
{
    private static final String TAG = AutoOp.class.getSimpleName();
    protected static final int isRed = 1; //1 if we are red, -1 if we are blue

    @Override
    public void init()
    {
        super.init();
        currentState = new MoveForwardState(24, 0.5);
    }

    @Override
    public void loop(){
        if (!currentState.update()) return;
        step++;
        switch(step) {
            case 2:
                currentState = new RotateState(45 * isRed, 0.5);
                break;
            case 3:
                currentState = new MoveForwardState(48, 0.5);
                break;
            case 4:
                currentState = new RotateState(45 * isRed, 0.5);
                break;
            case 5:
                currentState = new MoveForwardState(24, 0.5);
                break;
            case 6:
                currentState = new StopState();
                break;
        }
    }

    @Override
    public void stop()
    {
        telemetry.addData(TAG, "Test Stopped");
        currentState = new StopState();
    }

    private class MoveForwardState extends State {

        public MoveForwardState(int inches, double power){
            DriveTrainAuto.moveInches(inches,  power);
        }

    }

    private class RotateState extends State {

        public RotateState(int degrees, double power){
            DriveTrainAuto.rotateDegrees(degrees, power);
        }

    }

    private class StopState extends State {

        public StopState(){
            DriveTrainAuto.moveInches(0,0);
        }

        @Override
        public boolean update(){
            return false;
        }

    }
}
