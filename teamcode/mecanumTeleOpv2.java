package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "mecanumTeleOpv2", group = "TeleOp")
public class mecanumTeleOpv2 extends LinearOpMode {

    Hardware robot = new Hardware();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.initialize(hardwareMap);

        waitForStart();
        while(opModeIsActive()) {
            double y = -gamepad1.left_stick_y;
            double x = -gamepad1.left_stick_x;
            double rx = -gamepad1.right_stick_x;

            robot.frontLeft.setPower((-y + x + rx)*.7);
            robot.backLeft.setPower((-y - x + rx)*.7);
            robot.frontRight.setPower((-y - x - rx)*.7);
            robot.backRight.setPower((-y + x - rx)*.7);

            if(gamepad2.a) {
                robot.intakeMotor.setPower(.3);
            }
            else if(gamepad2.b) {
                robot.intakeMotor.setPower(-.3);
            }
            else if(gamepad2.x) {
                robot.intakeMotor.setPower(-1);
            }
            else if(gamepad2.y) {
                robot.intakeMotor.setPower(1);
            }
            else if (gamepad2.dpad_down) {
                robot.intakeMotor.setPower(0);
            }

            robot.armMotor.setPower(-gamepad2.left_stick_y*0.5);

            if (gamepad2.left_bumper) {
                robot.carouselMotor.setPower(1);
            }
            else if (gamepad2.right_bumper) {
                robot.carouselMotor.setPower(-1);
            }
            else {
                robot.carouselMotor.setPower(0);
            }

            idle();
        }
    }
}