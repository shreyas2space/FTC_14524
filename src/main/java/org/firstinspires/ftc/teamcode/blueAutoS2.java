package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "blueAutoS2", group = "Autonomous")
public class blueAutoS2 extends LinearOpMode {
    Hardware robot = new Hardware();

    private ElapsedTime runtime = new ElapsedTime();

    static final double COUNTS_PER_MOTOR_40_1 = 1440;
    @Override
    public void runOpMode() throws InterruptedException {
        robot.initialize(hardwareMap);

        waitForStart();

        Thread thread1 = new Thread() {
            public void run() {
                moveArm(0.3, 250, 1.0);
                moveForward(0.7, 1850, 4.0);
                moveArm(0.3, 450, 1.0);
                moveArm(0.3, -450, 1.0);
                moveForward(0.7, -700, 5.0);
                moveTurn(0.5, 1350, 3.0, false);
                moveStrafe(0.6, 1800, 6.0, false);
                moveForward(0.7, -4000, 7.0);

            }
        };

        Thread thread2 = new Thread() {
            public void run() {

//                moveIntake(1, 1000000000, 22.5);
            }
        };

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
//        moveArm(1, 1000, 5.0);
//        moveIntake(1, 10000, 5.0);
    }
    public void moveForward(double speed, double distance_counts, double timeout) {

        int targetfl;
        int targetfr;
        int targetbl;
        int targetbr;

        if (opModeIsActive()) {

            targetfl = robot.frontLeft.getCurrentPosition() - (int)distance_counts;
            targetfr = robot.frontRight.getCurrentPosition() - (int)distance_counts;
            targetbl = robot.backLeft.getCurrentPosition() - (int)distance_counts;
            targetbr = robot.backRight.getCurrentPosition() - (int)distance_counts;
            robot.frontLeft.setTargetPosition(targetfl);
            robot.frontRight.setTargetPosition(targetfr);
            robot.backLeft.setTargetPosition(targetbl);
            robot.backRight.setTargetPosition(targetbr);

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

            robot.frontLeft.setPower(0.1);
            robot.frontRight.setPower(0.1);
            robot.backLeft.setPower(0.1);
            robot.backRight.setPower(0.1);
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

    public void moveStrafe(double speed, double distance_counts, double timeout, boolean right) {

        int targetfl;
        int targetfr;
        int targetbl;
        int targetbr;

        if (opModeIsActive()) {


            if (right) {
                targetfl = robot.frontLeft.getCurrentPosition() - (int)distance_counts;
                targetfr = robot.frontRight.getCurrentPosition() + (int)distance_counts;
                targetbl = robot.backLeft.getCurrentPosition() + (int)distance_counts;
                targetbr = robot.backRight.getCurrentPosition() - (int)distance_counts;
            }
            else {
                targetfl = robot.frontLeft.getCurrentPosition() + (int)distance_counts;
                targetfr = robot.frontRight.getCurrentPosition() - (int)distance_counts;
                targetbl = robot.backLeft.getCurrentPosition() - (int)distance_counts;
                targetbr = robot.backRight.getCurrentPosition() + (int)distance_counts;
            }
            robot.frontLeft.setTargetPosition(targetfl);
            robot.frontRight.setTargetPosition(targetfr);
            robot.backLeft.setTargetPosition(targetbl);
            robot.backRight.setTargetPosition(targetbr);

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

            if (right) {
                robot.frontLeft.setPower(-0.1);
                robot.frontRight.setPower(0.1);
                robot.backLeft.setPower(0.1);
                robot.backRight.setPower(-0.1);
            }
            else {
                robot.frontLeft.setPower(0.1);
                robot.frontRight.setPower(-0.1);
                robot.backLeft.setPower(-0.1);
                robot.backRight.setPower(0.1);
            }
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

    public void moveTurn(double speed, double distance_counts, double timeout, boolean right) {

        int targetfl;
        int targetfr;
        int targetbl;
        int targetbr;

        if (opModeIsActive()) {


            if (right) {
                targetfl = robot.frontLeft.getCurrentPosition() + (int)distance_counts;
                targetfr = robot.frontRight.getCurrentPosition() - (int)distance_counts;
                targetbl = robot.backLeft.getCurrentPosition() + (int)distance_counts;
                targetbr = robot.backRight.getCurrentPosition() - (int)distance_counts;
            }
            else {
                targetfl = robot.frontLeft.getCurrentPosition() - (int)distance_counts;
                targetfr = robot.frontRight.getCurrentPosition() + (int)distance_counts;
                targetbl = robot.backLeft.getCurrentPosition() - (int)distance_counts;
                targetbr = robot.backRight.getCurrentPosition() + (int)distance_counts;
            }
            robot.frontLeft.setTargetPosition(targetfl);
            robot.frontRight.setTargetPosition(targetfr);
            robot.backLeft.setTargetPosition(targetbl);
            robot.backRight.setTargetPosition(targetbr);

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

            if (right) {
                robot.frontLeft.setPower(0.1);
                robot.frontRight.setPower(-0.1);
                robot.backLeft.setPower(0.1);
                robot.backRight.setPower(-0.1);
            }
            else {
                robot.frontLeft.setPower(-0.1);
                robot.frontRight.setPower(0.1);
                robot.backLeft.setPower(-0.1);
                robot.backRight.setPower(0.1);
            }
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

    public void moveCarousel(double speed, double distance_counts, double timeout) {

        int targetcarousel;

        if (opModeIsActive()) {

            targetcarousel = (int) (robot.carouselMotor.getCurrentPosition() + (speed*(int)distance_counts));
            robot.carouselMotor.setTargetPosition(targetcarousel);

            robot.carouselMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();
            robot.carouselMotor.setPower(speed);


            while(opModeIsActive() &&
                    (runtime.seconds() < timeout) &&
                    (robot.carouselMotor.isBusy())) {

                telemetry.addData("Current carousel position", robot.carouselMotor.getCurrentPosition());

                telemetry.update();
            }

            robot.carouselMotor.setPower(0.1*speed);
            sleep(100);
            robot.carouselMotor.setPower(0);

            robot.carouselMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            robot.carouselMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }
    }

    public void moveIntake(double speed, double distance_counts, double timeout) {

        int target;

        if (opModeIsActive()) {

            target = robot.intakeMotor.getCurrentPosition() + (int)distance_counts;
            robot.intakeMotor.setTargetPosition(target);

            robot.intakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();
            robot.intakeMotor.setPower(speed);


            while(opModeIsActive() &&
                    (runtime.seconds() < timeout) &&
                    (robot.intakeMotor.isBusy())) {

                telemetry.addData("Current intake position", robot.intakeMotor.getCurrentPosition());

                telemetry.update();
            }

            robot.intakeMotor.setPower(0.1);
            sleep(100);
            robot.intakeMotor.setPower(0);

            robot.intakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            robot.intakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }
    }

    public void moveArm(double speed, double distance_counts, double timeout) {

        int target;

        if (opModeIsActive()) {

            target = robot.armMotor.getCurrentPosition() + (int)distance_counts;
            robot.armMotor.setTargetPosition(target);

            robot.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();
            robot.armMotor.setPower(speed);


            while(opModeIsActive() &&
                    (runtime.seconds() < timeout) &&
                    (robot.armMotor.isBusy())) {

                telemetry.addData("Current arm position", robot.armMotor.getCurrentPosition());

                telemetry.update();
            }

            robot.armMotor.setPower(0.1);
            sleep(100);
            robot.armMotor.setPower(0);

            robot.armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            robot.armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }
    }
}