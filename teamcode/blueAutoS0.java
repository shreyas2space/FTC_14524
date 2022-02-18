package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

// Control Hub ADB Terminal Command for Reference
// adb.exe connect 192.168.43.1:5555

@TeleOp(name = "blueAutoS0", group = "TeleOp")
public class blueAutoS0 extends LinearOpMode {

    // Declare OpMode members.
    private GenericDetector rf = null;
    private String result = "";
    private Hardware bot = new Hardware();

    @Override
    public void runOpMode() {
        try {
            try {
                //initialize the bot
                bot.initialize(hardwareMap);

                //initialize the detector. It will run on its own thread continuously
                rf = new GenericDetector(this.hardwareMap,  this,  telemetry);
                Thread detectThread = new Thread(rf);
                detectThread.start();
                telemetry.update();
            } catch (Exception ex) {
                telemetry.addData("Error", String.format("Unable to initialize Detector. %s", ex.getMessage()));
                telemetry.update();
                sleep(5000);
                return;
            }

            // Wait for the game to start (driver presses PLAY)
            telemetry.update();
            waitForStart();

            rf.stopDetection();

            result = rf.getResult();

            // run until the end of the match (driver presses STOP)
            while (opModeIsActive()) {

                //show recognition result
                telemetry.addData("Detection result", result);
                telemetry.update();
            }
        } catch (Exception ex) {
            telemetry.addData("Init Error", ex.getMessage());
            telemetry.update();
        } finally {
            if (rf != null) {
                rf.stopDetection();
            }
        }
    }
}