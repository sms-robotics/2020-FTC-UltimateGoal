package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;

/**
 * Demonstrates OpMode using the VisionManager
 */
@TeleOp(name = "Grand Unified Vision Example", group = "Concept")
public class GrandUnifiedVisionExample extends LinearOpMode {
    private static final String TAG = "Grand Unified Vision Example";

    /**
     * State regarding our interaction with the camera
     */
    private final VisionManager visionManager = new VisionManager();

    private final WebcamScanner webcamScanner = new WebcamScanner();
    private String state = "Start";

    @Override
    public void runOpMode() {
        boolean successfullyInitialized = visionManager.initialize(hardwareMap);

        if (!successfullyInitialized) {
            telemetry.addData("FATAL", "Initialization failure!");
            telemetry.update();
            return;
        }

        webcamScanner.initialize(hardwareMap);

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();

        state = "Scanning";

        waitForStart();

        webcamScanner.goToStartingPosition();
        webcamScanner.startScanning();

        visionManager.activate();

        try {
            while (opModeIsActive()) {
                //  If we're in the "scanning mode":
                if (state == "Scanning") {
                    webcamScanner.loop(1000);

                    if (webcamScanner.isDoneScanning()) {
                        state = "Make Decision";
                    }
                }

                if (state == "Make Decision") {
                    // TBD

                }

                visionManager.loop();

                telemetry.addData("Last saw Quad rings    ", String.format("%3.2f seconds ago", visionManager.getHowManySecondsAgoSawQuadRings()));
                telemetry.addData("Last saw Single ring   ", String.format("%3.2f seconds ago", visionManager.getHowManySecondsAgoSawSingleRing()));

                telemetry.addData("Blue Target Visible    ", visionManager.isBlueTargetVisible());
                telemetry.addData("Last saw Blue Target   ", String.format("%3.2f seconds ago", visionManager.getHowManySecondsAgoSawBlueTarget()));

                telemetry.addData("Red Target Visible     ", visionManager.isRedTargetVisible());
                telemetry.addData("Last saw Red Target    ", String.format("%3.2f seconds ago", visionManager.getHowManySecondsAgoSawRedTarget()));
                telemetry.addData("% Orange Pixels        ", String.format("%2.3f %%", visionManager.getPercentageOfPixelsThatAreOrange() * 100.0));

                OpenGLMatrix lastComputedLocation = visionManager.getLastComputedLocation();
                if (lastComputedLocation != null) {
                    telemetry.addData("Position                        ", lastComputedLocation.formatAsTransform());
                } else {
                    telemetry.addData("Position                        ", "Unknown");
                }

                telemetry.update();
            }
        } catch (Exception e) {
            RobotLog.ee(TAG, e, "OpMode Loop Error");
        } finally {
            visionManager.shutdown();
        }
    }
}
