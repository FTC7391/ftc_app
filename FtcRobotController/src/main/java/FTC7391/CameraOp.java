package FTC7391;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;


import FTC7391.CameraPreview;
import com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.util.Log;

/**
 * TeleOp Mode
 * <p>
 *Enables control of the robot via the gamepad
 */
public class CameraOp extends OpMode {
    private Camera camera;
    public CameraPreview preview;
    public Bitmap image;
    private int width;
    private int height;
    private YuvImage yuvImage = null;
    private int looped = 0;
    private String data;
    private static final String TAG = CameraOp.class.getSimpleName();

    private int red(int pixel) {
        return (pixel >> 16) & 0xff;
    }

    private int green(int pixel) {
        return (pixel >> 8) & 0xff;
    }

    private int blue(int pixel) {
        return pixel & 0xff;
    }

    private Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera camera)
        {
            Camera.Parameters parameters = camera.getParameters();
            width = parameters.getPreviewSize().width;
            height = parameters.getPreviewSize().height;
            yuvImage = new YuvImage(data, ImageFormat.NV21, width, height, null);
            looped += 1;
        }
    };

    private void convertImage() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(new Rect(0, 0, width, height), 0, out);
        byte[] imageBytes = out.toByteArray();
        image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }
    /*
     * Code to run when the op mode is first enabled goes here
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
    @Override
    public void init() {
        camera = ((FtcRobotControllerActivity)hardwareMap.appContext).camera;
        camera.setPreviewCallback(previewCallback);

        Camera.Parameters parameters = camera.getParameters();
        data = parameters.flatten();

        ((FtcRobotControllerActivity) hardwareMap.appContext).initPreview(camera, this, previewCallback);
    }

    /*
     * This method will be called repeatedly in a loop
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
     */
    public int highestColor(int red, int green, int blue) {
        int[] color = {red,green,blue};
        int value = 0;
        for (int i = 1; i < 3; i++) {
            if (color[value] < color[i]) {
                value = i;
            }
        }
        return value;
    }

    @Override
    public void loop() {
        String answer="";
        if (yuvImage != null) {

            convertImage();
            int pixel = image.getPixel(25, 25);
            int pixel2 = image.getPixel(width - 20, height - 20);
            if (red(pixel) > red(pixel2)) {
                System.out.println("left");
            } else {
                System.out.println("right");
            }

            for (int i = 0; i < width; i++) {
                for (int z = 0; z < height; z++) {

                    int pixel3 =image.getPixel(i,z);
                    int color = highestColor(red(pixel3),blue(pixel3),green(pixel3));
                    if(color==0){
                        if(i<width/2){
                            answer  = "left";
                        }else{
                            answer = "right";
                            // System.out.println("i is " + i + "z is " + z + "out of " + width + "color is " + color);
                            telemetry.addData(TAG, "answer " + answer);
                        }
                    }

                }

            }
            FTC7391PrintWriter data1Writer = new FTC7391PrintWriter("Camera" , "TestFile1");

            for (int j = 0; j < height; j+=10 ) {
                for (int i = 0; i < width; i+=80 ) {
                    pixel = image.getPixel(i, j);
                    data1Writer.printf("%x %x %x *  ", red(pixel), green(pixel), blue(pixel));
                }
                data1Writer.printf("\n");
                Log.d(TAG, "\n");
            }
            data1Writer.close();


        }

    }
}
