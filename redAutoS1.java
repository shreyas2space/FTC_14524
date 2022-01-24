package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.concurrent.TimeUnit;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "redAutoS1", group = "Autonomous")
public class redAutoS1 extends LinearOpMode {
    Hardware robot = new Hardware();

    private ElapsedTime runtime = new ElapsedTime();

    static final double COUNTS_PER_MOTOR_40_1 = 1440;
    @Override
    public void runOpMode() throws InterruptedException {
        robot.initialize(hardwareMap);

        waitForStart();

        Thread thread1 = new Thread() {
            public void run() {
                robot.armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                moveArm(.1, 100, 1.0);
                moveStrafe(1, 300, 2.0, true);
                moveCarousel(1, 10000, 5.0);
                moveForward(1, -3550, 5.1);
                moveTurn(1, 1300, 5.0, true);
                moveForward(1, 2600, 5.0);
                moveArm(.04, 750, 2);
                moveArm(.04,-750, 2);
//                moveTurn(1, 250, 1.0, false);
//                moveForward(1, -2700, 5.0);
            }
        };

        Thread thread2 = new Thread() {
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                moveIntake(1, 1000000000, 22.5);
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

            targetfl = robot.frontLeft.getCurrentPosition() + (int)distance_counts;
            targetfr = robot.frontRight.getCurrentPosition() + (int)distance_counts;
            targetbl = robot.backLeft.getCurrentPosition() + (int)distance_counts;
            targetbr = robot.backRight.getCurrentPosition() + (int)distance_counts;
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

    public void moveStrafe(double speed, double distance_counts, double timeout, boolean left) {

        int targetfl;
        int targetfr;
        int targetbl;
        int targetbr;

        if (opModeIsActive()) {


            if (left) {
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

            if (left) {
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

    public void moveTurn(double speed, double distance_counts, double timeout, boolean left) {

        int targetfl;
        int targetfr;
        int targetbl;
        int targetbr;

        if (opModeIsActive()) {


            if (left) {
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

            if (left) {
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

        int targetleft;
        int targetright;

        if (opModeIsActive()) {

            targetright = robot.leftIntake.getCurrentPosition() + (int)distance_counts;
            targetleft = robot.rightIntake.getCurrentPosition() + (int)distance_counts;
            robot.leftIntake.setTargetPosition(targetleft);
            robot.rightIntake.setTargetPosition(targetright);

            robot.leftIntake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightIntake.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();
            robot.leftIntake.setPower(speed);
            robot.rightIntake.setPower(speed);


            while(opModeIsActive() &&
                    (runtime.seconds() < timeout) &&
                    (robot.leftIntake.isBusy())) {

                telemetry.addData("Current left intake position", robot.leftIntake.getCurrentPosition());
                telemetry.addData("Current right intake position", robot.rightIntake.getCurrentPosition());

                telemetry.update();
            }

            robot.leftIntake.setPower(0.1);
            robot.rightIntake.setPower(0.1);
            sleep(100);
            robot.leftIntake.setPower(0);
            robot.rightIntake.setPower(0);

            robot.leftIntake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.rightIntake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            robot.leftIntake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rightIntake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

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