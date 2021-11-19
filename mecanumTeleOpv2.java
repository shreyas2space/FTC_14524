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
            double x = gamepad1.left_stick_x;
            double rx = -gamepad1.right_stick_x;

            robot.frontLeft.setPower((-y + x + rx)/2);
            robot.backLeft.setPower((-y - x + rx)/2);
            robot.frontRight.setPower((-y - x - rx)/2);
            robot.backRight.setPower((-y + x - rx)/2);

            robot.leftIntake.setPower(gamepad2.left_stick_y/2.5);
            robot.rightIntake.setPower(gamepad2.left_stick_y/2.5);
            robot.armMotor.setPower(gamepad2.right_stick_y/3);

//            if(gamepad2.x) {
//                robot.carouselMotor.setPower(1);
//            }
//            else {
//                robot.carouselMotor.setPower(0);
//            }

            idle();
        }
    }
}
