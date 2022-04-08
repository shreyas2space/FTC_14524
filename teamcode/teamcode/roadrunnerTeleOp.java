package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "roadrunnerTeleOp", group = "TeleOp")
public class roadrunnerTeleOp extends LinearOpMode {

    Hardware robot = new Hardware();
    SampleMecanumDrive e = new SampleMecanumDrive(hardwareMap);

    @Override
    public void runOpMode() throws InterruptedException {
        robot.initialize(hardwareMap);

        waitForStart();
        while(opModeIsActive()) {
            double y = -gamepad1.left_stick_y;
            double x = -gamepad1.left_stick_x;
            double rx = -gamepad1.right_stick_x;

//            robot.frontLeft.setPower((-y + x + rx)*.7);
//            robot.backLeft.setPower((-y - x + rx)*.7);
//            robot.frontRight.setPower((-y - x - rx)*.7);
//            robot.backRight.setPower((-y + x - rx)*.7);
            e.setMotorPowers((-y + x + rx)*.7, (-y - x + rx)*.7, (-y - x - rx)*.7, (-y + x - rx)*.7);
            if(gamepad2.cross) {
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

            if (gamepad2.left_trigger > 0) {
                robot.carouselMotor.setPower(1);
            }
            else if (gamepad2.right_trigger > 0) {
                robot.carouselMotor.setPower(-1);
            }
            else {
                robot.carouselMotor.setPower(0);
            }

            idle();
        }
    }
}