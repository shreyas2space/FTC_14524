/*
This program is a simple testing program used to test individual components of the robot
without interrupting any other processes
 */



package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Testing", group = "TeleOp")
public class Testing extends LinearOpMode {
    private DcMotor leftIntake;
    private DcMotor rightIntake;
    private DcMotor armMotor;
    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor carouselMotor;

    @Override
    public void runOpMode() throws InterruptedException {
        leftIntake = hardwareMap.dcMotor.get("leftIntake");
        rightIntake = hardwareMap.dcMotor.get("rightIntake");
//        armMotor = hardwareMap.dcMotor.get("armMotor");
//        frontLeft = hardwareMap.dcMotor.get("frontLeft");
//        frontRight = hardwareMap.dcMotor.get("frontRight");
//        backLeft = hardwareMap.dcMotor.get("backLeft");
//        backRight = hardwareMap.dcMotor.get("backRight");
//        carouselMotor = hardwareMap.dcMotor.get("carouselMotor");


//
        rightIntake.setDirection(DcMotorSimple.Direction.REVERSE);
//        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
//        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
//        armMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        while(opModeIsActive()) {
//          Intake tests
            leftIntake.setPower(gamepad2.left_stick_y*0.6);
            rightIntake.setPower(gamepad2.left_stick_y*0.6);
//            armMotor.setPower(gamepad2.right_stick_y*0.6);

//            Carousel Test
//            carouselMotor.setPower(gamepad2.left_trigger);

//          Connection Test
//            leftIntake.setPower(1);
//            rightIntake.setPower(1);

            idle();
        }
    }
}