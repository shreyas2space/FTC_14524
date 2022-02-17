package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

//@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "fun", group = "TeleOp")
@Disabled
public class fun extends LinearOpMode {

    Hardware robot = new Hardware();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.initialize(hardwareMap);

        waitForStart();
        while(opModeIsActive()) {
            double y = -gamepad1.left_stick_y;
            double x = -gamepad1.left_stick_x;
            double rx = -gamepad1.right_stick_x;

//            double servoPos = 0.0;

            robot.frontLeft.setPower((-y + x + rx));
            robot.backLeft.setPower((-y - x + rx));
            robot.frontRight.setPower((-y - x - rx));
            robot.backRight.setPower((-y + x - rx));

            robot.intakeMotor.setPower(gamepad2.left_stick_y);
            robot.armMotor.setPower(gamepad2.right_stick_y);

            robot.carouselMotor.setPower(gamepad2.left_trigger);
            robot.carouselMotor.setPower(-gamepad2.right_trigger);

            idle();
        }
    }
}