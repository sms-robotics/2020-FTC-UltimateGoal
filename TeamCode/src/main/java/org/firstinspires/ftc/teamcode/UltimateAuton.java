package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Auton", group="Pushbot")
public class UltimateAuton extends LinearOpMode {

    /* Declare OpMode members. */
    UltimateHardware robot = new UltimateHardware();   // Use a Pushbot's hardware
    double forward = 0.5;
    double right = 0;
    double intRunTime = 0;
    int msToRun = 2500;

    ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap, false);
        //move to hardware code and only run in AUTON

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        telemetry.update();
        float powerReducer = 0.5f;
        // Wait for the game to start (driver presses PLAY)

        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            timedDrive(forward, right, msToRun);
            timedDrive(0, 0, 1000);
            timedDrive(forward*-1, right, msToRun);
            break;
        }
    }
    public void timedDrive(double forward, double right, double msToRun)
    {

        telemetry.update();

        robot.frontRightDrive.setPower(forward + right);
        robot.frontLeftDrive.setPower(forward - right);
        robot.rearRightDrive.setPower(forward - right);
        robot.rearLeftDrive.setPower(forward + right);
        intRunTime = runtime.milliseconds() + msToRun;
        while (runtime.milliseconds() < intRunTime) {
            telemetry.addData("drive-l", forward);
            telemetry.addData("drive-r", right);
            telemetry.addData("time", runtime.milliseconds());
            telemetry.update();
            idle();
        }
        robot.frontRightDrive.setPower(0.0);
        robot.frontLeftDrive.setPower(0.0);
        robot.rearRightDrive.setPower(0.0);
        robot.rearLeftDrive.setPower(0.0);
    }
}
