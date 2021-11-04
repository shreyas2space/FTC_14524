package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "mecanumTeleOp", group = "TeleOp")
public class mecanumTeleOpv2 extends LinearOpMode {

    Hardware robot = new Hardware(hardwareMap);

    //Hardware robot = new Hardware();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.initialize(hardwareMap);

        waitForStart();
        while(opModeIsActive()) {
            if (gamepad1.left_bumper) {
                robot.frontRight.setPower(-1);
                robot.frontLeft.setPower(-1);
                robot.backRight.setPower(1);
                robot.backLeft.setPower(1);
            }
            else if (gamepad1.right_bumper) {
                robot.frontRight.setPower(1);
                robot.frontLeft.setPower(1);
                robot.backRight.setPower(-1);
                robot.backLeft.setPower(-1);
            }
            else {
                robot.frontRight.setPower(gamepad1.left_stick_y);
                robot.frontLeft.setPower(gamepad1.left_stick_y);
                robot.backRight.setPower(gamepad1.right_stick_y);
                robot.backLeft.setPower(gamepad1.right_stick_y);
            }

            robot.leftIntake.setPower(-gamepad2.left_stick_y/3);
            robot.rightIntake.setPower(gamepad2.left_stick_y/3);
            robot.armMotor.setPower(gamepad2.right_stick_y/2);

            idle();
        }
    }
}
