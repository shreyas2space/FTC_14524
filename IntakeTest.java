package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "IntakeTest", group = "TeleOp")
public class IntakeTest extends LinearOpMode {
    private DcMotor leftIntake;
    private DcMotor rightIntake;
    private DcMotor armMotor;

    @Override
    public void runOpMode() throws InterruptedException {
        leftIntake = hardwareMap.dcMotor.get("leftIntake");
        rightIntake = hardwareMap.dcMotor.get("rightIntake");
        armMotor = hardwareMap.dcMotor.get("armMotor");
        waitForStart();
        while(opModeIsActive()) {
            leftIntake.setPower(-gamepad2.left_stick_y/3);
            rightIntake.setPower(gamepad2.left_stick_y/3);
            armMotor.setPower(gamepad2.right_stick_y/2);
            idle();
        }
    }
}