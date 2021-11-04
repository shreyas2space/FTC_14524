package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "autonTest", group = "Autonomous")
public class AutoTest extends LinearOpMode {
    private DcMotor testMotor;
    private ElapsedTime runtime = new ElapsedTime();

    static final double COUNTS_PER_MOTOR_40_1 = 1440;
    @Override
    public void runOpMode() throws InterruptedException {
        testMotor = hardwareMap.dcMotor.get("testMotor");

        testMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        testMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        encoderDrive(1, 560, 5.0);
    }

    public void encoderDrive(double speed, double distance_counts, double timeout){
        int target;

        if (opModeIsActive()) {

            target = testMotor.getCurrentPosition() + (int)distance_counts;
            testMotor.setTargetPosition(target);

            testMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();
            testMotor.setPower(speed);


            while(opModeIsActive() &&
                    (runtime.seconds() < timeout) &&
                    (testMotor.isBusy())) {

                telemetry.addData("Path", "Running to %7d", target);
                telemetry.addData("Current position", testMotor.getCurrentPosition());
                telemetry.update();
            }

            testMotor.setPower(-0.1);
            sleep(100);
            testMotor.setPower(0);

            testMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
}
