package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "autonTest", group = "Autonomous")
public class AutoTest extends LinearOpMode {
    Hardware robot = new Hardware();

    private ElapsedTime runtime = new ElapsedTime();

    static final double COUNTS_PER_MOTOR_40_1 = 1440;
    @Override
    public void runOpMode() throws InterruptedException {
        robot.initialize(hardwareMap);
//        testMotor = hardwareMap.dcMotor.get("testMotor");
//
//        testMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        testMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        moveForward(1, -1440, 5.0);
    }
    public void moveForward(double speed, double distance_counts, double timeout) {
//        encoderDrive(speed, distance_counts, timeout, robot.frontRight);
//        encoderDrive(speed, distance_counts, timeout, robot.backRight);
//        encoderDrive(speed, distance_counts, timeout, robot.frontLeft);
//        encoderDrive(speed, distance_counts, timeout, robot.backLeft);

        int targetfl;
        int targetfr;
        int targetbl;
        int targetbr;

        if (opModeIsActive()) {

            targetfl = robot.frontLeft.getCurrentPosition() + (int)distance_counts;
            targetfr = robot.frontRight.getCurrentPosition() + (int)distance_counts;
            targetbl = robot.backLeft.getCurrentPosition() + (int)distance_counts;
            targetbr = robot.backRight.getCurrentPosition() + (int)distance_counts;
            robot.frontLeft.setTargetPosition(targetfl);
            robot.frontRight.setTargetPosition(targetfl);
            robot.backLeft.setTargetPosition(targetfl);
            robot.backRight.setTargetPosition(targetfl);

            robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();
            robot.frontLeft.setPower(speed);
            robot.frontRight.setPower(speed);
            robot.backLeft.setPower(speed);
            robot.backRight.setPower(speed);


            while(opModeIsActive() &&
                    (runtime.seconds() < timeout) &&
                    (robot.frontLeft.isBusy())) {

                telemetry.addData("Current position", robot.frontLeft.getCurrentPosition());
                telemetry.addData("Current position", robot.frontRight.getCurrentPosition());
                telemetry.addData("Current position", robot.backLeft.getCurrentPosition());
                telemetry.addData("Current position", robot.backRight.getCurrentPosition());

                telemetry.update();
            }

            robot.frontLeft.setPower(-0.1);
            robot.frontRight.setPower(-0.1);
            robot.backLeft.setPower(-0.1);
            robot.backRight.setPower(-0.1);
            sleep(100);
            robot.frontLeft.setPower(0);
            robot.frontRight.setPower(0);
            robot.backLeft.setPower(0);
            robot.backRight.setPower(0);

            robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }
    }

    public void encoderDrive(double speed, double distance_counts, double timeout, DcMotor motor){
        int target;

        if (opModeIsActive()) {

            target = motor.getCurrentPosition() + (int)distance_counts;
            motor.setTargetPosition(target);

            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();
            motor.setPower(speed);


            while(opModeIsActive() &&
                    (runtime.seconds() < timeout) &&
                    (motor.isBusy())) {

                telemetry.addData("Path", "Running to %7d", target);
                telemetry.addData("Current position", motor.getCurrentPosition());
                telemetry.update();
            }

            motor.setPower(-0.1);
            sleep(100);
            motor.setPower(0);

            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }
    }
}
