package FTC7391;

import com.qualcomm.robotcore.hardware.I2cController;
//BASED OFF OF IRSEEKERSENSOR CLASS

/**
 * Created by ArjunVerma on 9/5/15.
 */
public class OpticalDistanceSensor implements I2cController.I2cPortReadyCallback {

    public void portIsReady(int x){}

    public int getDistance(){return 0;}

}
