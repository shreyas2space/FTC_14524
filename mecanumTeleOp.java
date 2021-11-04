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

    //Hardware robot = new Hardware();

    @Override
    public void runOpMode() throws InterruptedException {
        frontRight = hardwareMap.dcMotor.get("frontRight");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backRight = hardwareMap.dcMotor.get("backRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        waitForStart();
        while(opModeIsActive()) {
            if (gamepad1.left_bumper) {
                frontRight.setPower(-1);
                frontLeft.setPower(-1);
                backRight.setPower(1);
                backLeft.setPower(1);
            }
            else if (gamepad1.right_bumper) {
                frontRight.setPower(1);
                frontLeft.setPower(1);
                backRight.setPower(-1);
                backLeft.setPower(-1);
            }
            else {
                frontRight.setPower(gamepad1.left_stick_y);
                frontLeft.setPower(gamepad1.left_stick_y);
                backRight.setPower(gamepad1.right_stick_y);
                backLeft.setPower(gamepad1.right_stick_y);
            }
            idle();
        }
    }
}
