package org.firstinspires.ftc.team7391;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Axis;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by ArjunVerma on 10/29/17.
 */

@Autonomous(name = "Tournament: VuforiaTest" + "", group = "Tournament")


public class VuforiaOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        params.vuforiaLicenseKey = "AZJRlI7/////AAAAGTBxgU8aaEzAtjrlIjdAMABjEBsYNQWL4LJl7tYcmjp+kJuMWOdMzKxhmV6I4/xbNxuDBKg4bzdDVobYIPonBAS5Ezv765DJ9clmAYZpzDJ2jxBX8vIXOxJa2OGx6T//0R+XmEnh+ckZ4vF45wnOCOcDaDUKu5pfOIE7OUpTNYzdVwQ/Rcz9TpCN7eQjJr/Px0sgAILvrOCFphQs5sYkQLNZyNoUPVWYY30O063EDxasYOjGMO7T44sWSYqASjbX+P936m6qDD7Xv1u7szw2elif8QdYAH61yG+3AnKH9U5SkMO096cp4IVY0VHLFL/Ay9CSRsUMyC6n/rfQZYuhybhuAU6PKz0nkbUX6iCXalRr";
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(params);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        VuforiaTrackables beacons = vuforia.loadTrackablesFromAsset("RelicVuMark"); // TBD!!! NEED TO GET IMAGES FROM FTC
        beacons.get(0).setName("pictograph_left.png");
        beacons.get(1).setName("pictograph_right.png");
        beacons.get(2).setName("pictograph_center");

        waitForStart();

        beacons.activate();

        while(opModeIsActive()){
            for(VuforiaTrackable beac : beacons) {
                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) beac.getListener()).getPose();

                if(pose != null){
                    VectorF translation = pose.getTranslation();

                    telemetry.addData(beac.getName() + "-Translation", translation);
                    double degreesToTurn = Math.toDegrees(Math.atan2(translation.get(1), translation.get(2)));

                    telemetry.addData(beac.getName() + "-Degrees", degreesToTurn);

                }

            }
            telemetry.update();
        }

    }

}
