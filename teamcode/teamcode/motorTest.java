package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "motorTest", group = "TeleOp")
public class motorTest extends LinearOpMode {

    private DcMotor motor;

    @Override
    public void runOpMode() throws InterruptedException {
        motor = hardwareMap.dcMotor.get("testMotor");

        waitForStart();
        while(opModeIsActive()) {

            if (gamepad1.a) {
                motor.setPower(1);
            }
            else if (gamepad1.b) {
                motor.setPower(0);
            }

            idle();
        }
    }
}