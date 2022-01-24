package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "mecanumTeleOp", group = "TeleOp")
public class mecanumTeleOp extends LinearOpMode {
    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backRight;
    private DcMotor backLeft;
    private DcMotor leftIntake;
    private DcMotor rightIntake;
    private DcMotor armMotor;

    //Hardware robot = new Hardware();

    @Override
    public void runOpMode() throws InterruptedException {
        frontRight = hardwareMap.dcMotor.get("frontRight");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backRight = hardwareMap.dcMotor.get("backRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        leftIntake = hardwareMap.dcMotor.get("leftIntake");
        rightIntake = hardwareMap.dcMotor.get("rightIntake");
        armMotor = hardwareMap.dcMotor.get("armMotor");

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        backRight.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        waitForStart();
        while(opModeIsActive()) {
//            frontLeft.setPower(gamepad1.right_stick_x + -gamepad1.left_stick_y + gamepad1.left_stick_x);
//            backLeft.setPower(-gamepad1.right_stick_x + gamepad1.left_stick_y + gamepad1.left_stick_x);
//            frontRight.setPower(-gamepad1.right_stick_x + -gamepad1.left_stick_y - gamepad1.left_stick_x);
//            backRight.setPower(gamepad1.right_stick_x + gamepad1.left_stick_y - gamepad1.left_stick_x);
//            double y = -gamepad1.left_stick_y; // Remember, this is reversed!
//            double x = gamepad1.right_stick_x;
//            double rx = gamepad1.left_stick_x;
//
//
//            frontLeft.setPower(y + x + rx);
//            backLeft.setPower(y - x + rx);
//            frontRight.setPower(y - x - rx);
//            backRight.setPower(y + x - rx);
//            idle();
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.right_stick_x;
            double rx = gamepad1.left_stick_x;

            frontLeft.setPower(y + x + rx);
            backLeft.setPower(y - x + rx);
            frontRight.setPower(y - x - rx);
            backRight.setPower(y + x - rx);

            leftIntake.setPower(-gamepad2.left_stick_y/3);
            rightIntake.setPower(gamepad2.left_stick_y/3);
            armMotor.setPower(gamepad2.right_stick_y/2);

            idle();
        }
    }
}
