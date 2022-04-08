//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//import org.firstinspires.ftc.robotcore.external.ClassFactory;
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
//import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
//import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
//
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//@Autonomous(name = "blueAutoCOOLEST", group = "Autonomous")
//public class blueAutoCOOLEST extends LinearOpMode {
//    Hardware robot = new Hardware();
//
//    private ElapsedTime runtime = new ElapsedTime();
//
//    static final double COUNTS_PER_MOTOR_40_1 = 1440;
//    /* Note: This sample uses the all-objects Tensor Flow model (FreightFrenzy_BCDM.tflite), which contains
//     * the following 4 detectable objects
//     *  0: Ball,
//     *  1: Cube,
//     *  2: Duck,
//     *  3: Marker (duck location tape marker)
//     *
//     *  Two additional model assets are available which only contain a subset of the objects:
//     *  FreightFrenzy_BC.tflite  0: Ball,  1: Cube
//     *  FreightFrenzy_DM.tflite  0: Duck,  1: Marker
//     */
//    private static final String TFOD_MODEL_ASSET = "FreightFrenzy_BCDM.tflite";
//    private static final String[] LABELS = {
//            "Ball",
//            "Cube",
//            "Duck",
//            "Marker"
//    };
//
//    /*
//     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
//     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
//     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
//     * web site at https://developer.vuforia.com/license-manager.
//     *
//     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
//     * random data. As an example, here is a example of a fragment of a valid key:
//     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
//     * Once you've obtained a license key, copy the string from the Vuforia web site
//     * and paste it in to your code on the next line, between the double quotes.
//     */
//    private static final String VUFORIA_KEY =
//            "AcRzWnD/////AAABmTPAZHM3KEliqPV0TtJ2gHwBAtGUgWByXJatLFJRAZHCuqszAOeGolQ1diwOB0p7wTm1R8Vz5QhVdQp2P/iQVAJDLXGyrkjJw7bAMpi0SknaVqQgnOSgCl+gBG4w8PFUUjvBXzgozbOcfWiEKnRnyRhj39c0Cyp9a+DNmbO64Z2R3Bo9s84Dk9iLoJqW692UR0KcroY0YkB+1PoMKyvuuB9yQg39aMltx5lI82RwuanQXbxQd3Vy47JAa6vbTBUOdj8hBIQtaxZ/FSJgk81VZMTZdk66SuHz/3oRhV1teTvnIp+6v3X8al6b2jZPL5qe54i3VwSJB8iNxyZuxqQys/iEP06d4w/iq7wFbzDDxTyC";
//
//    /**
//     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
//     * localization engine.
//     */
//    private VuforiaLocalizer vuforia;
//
//    /**
//     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
//     * Detection engine.
//     */
//    private TFObjectDetector tfod;
//    int duckPos = 2;
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
//        // first.
//        robot.initialize(hardwareMap);
//        initVuforia();
//        initTfod();
//
//        /**
//         * Activate TensorFlow Object Detection before we wait for the start command.
//         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
//         **/
//        if (tfod != null) {
//            tfod.activate();
//
//            // The TensorFlow software will scale the input images from the camera to a lower resolution.
//            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
//            // If your target is at distance greater than 50 cm (20") you can adjust the magnification value
//            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
//            // should be set to the value of the images used to create the TensorFlow Object Detection model
//            // (typically 16/9).
//            tfod.setZoom(2.5, 16.0/9.0);
//        }
//
//        /** Wait for the game to begin */
//        telemetry.addData(">", "Press Play to start op mode");
//        telemetry.update();
//        waitForStart();
//
//        if (opModeIsActive()) {
//            if (tfod != null) {
//                // getUpdatedRecognitions() will return null if no new information is available since
//                // the last time that call was made.
//                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
//                if (updatedRecognitions != null) {
//                    telemetry.addData("# Object Detected", updatedRecognitions.size());
//                    // step through the list of recognitions and display boundary info.
//                    int i = 0;
//                    for (Recognition recognition : updatedRecognitions) {
//                        if (recognition.getLeft() > 1) {
//                            duckPos = 1;
//                        }
//                        else if (recognition.getRight() > 1) {
//                            duckPos = 3;
//                        }
//                        else {
//                            duckPos = 2;
//                        }
//                        telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
//                        telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
//                                recognition.getLeft(), recognition.getTop());
//                        telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
//                                recognition.getRight(), recognition.getBottom());
//                        i++;
//                    }
//                    telemetry.update();
//                }
//            }
//            Thread thread1 = new Thread() {
//                public void run() {
//                    moveArm(0.3, 250, 100.0);
//                    moveStrafe(0.6, 400, 100.0, false);
//                    if (duckPos == 3) {
//                        moveForward(0.7, -1850, 400.0);
//                    }
//                    else if (duckPos == 1) {
//                        moveForward(0.7, -800, 400.0);
//                    }
//                    else {
//                        moveForward(0.7, -1100, 400.0);
//                    }
//                    // LOOP START
//                    moveArm(0.3, 450, 100.0);
//                    moveArm(0.3, -450, 100.0);
//                    moveForward(0.7, 1200, 500.0);
//                    moveTurn(0.3, 500, 500.0, true);
//                    moveStrafe(0.6, 500, 600.0, false);
//                    moveForward(0.5, 1000, 600.0);
//                    // DO OBJECT DETECTION HERE. IN WAREHOUSE AT THIS POINT.
//                    moveForward(1, -3400, 500.1);
//                    moveStrafe(SMALL AMOUNT SO THAT THERE IS ROOM TO TURN);
//                    moveTurn(MOVE LEFT);
//                    moveForward(BACKWARD)
//                    // LOOP END
//                }
//            };
//
//            Thread thread2 = new Thread() {
//                public void run() {
//                    try {
//                        TimeUnit.SECONDS.sleep(7);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
////                moveIntake(1, 1000000000, 22.5);
//                }
//            };
//
//            thread1.start();
//            thread2.start();
//
//            thread1.join();
//            thread2.join();
////        moveArm(1, 1000, 5.0);
////        moveIntake(1, 10000, 5.0);
//        }
//    }
//
//    /**
//     * Initialize the Vuforia localization engine.
//     */
//    private void initVuforia() {
//        /*
//         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
//         */
//        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
//
//        parameters.vuforiaLicenseKey = VUFORIA_KEY;
//        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");
//
//        //  Instantiate the Vuforia engine
//        vuforia = ClassFactory.getInstance().createVuforia(parameters);
//
//        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
//    }
//
//    /**
//     * Initialize the TensorFlow Object Detection engine.
//     */
//    private void initTfod() {
//        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
//                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
//        tfodParameters.minResultConfidence = 0.8f;
//        tfodParameters.isModelTensorFlow2 = true;
//        tfodParameters.inputSize = 320;
//        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
//        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
//    }
//    public void moveForward(double speed, double distance_counts, double timeout) {
//
//        int targetfl;
//        int targetfr;
//        int targetbl;
//        int targetbr;
//
//        if (opModeIsActive()) {
//
//            targetfl = robot.frontLeft.getCurrentPosition() - (int)distance_counts;
//            targetfr = robot.frontRight.getCurrentPosition() - (int)distance_counts;
//            targetbl = robot.backLeft.getCurrentPosition() - (int)distance_counts;
//            targetbr = robot.backRight.getCurrentPosition() - (int)distance_counts;
//            robot.frontLeft.setTargetPosition(targetfl);
//            robot.frontRight.setTargetPosition(targetfr);
//            robot.backLeft.setTargetPosition(targetbl);
//            robot.backRight.setTargetPosition(targetbr);
//
//            robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//            runtime.reset();
//            robot.frontLeft.setPower(speed);
//            robot.frontRight.setPower(speed);
//            robot.backLeft.setPower(speed);
//            robot.backRight.setPower(speed);
//
//
//            while(opModeIsActive() &&
//                    (runtime.seconds() < timeout) &&
//                    (robot.frontLeft.isBusy())) {
//
//                telemetry.addData("Current position", robot.frontLeft.getCurrentPosition());
//                telemetry.addData("Current position", robot.frontRight.getCurrentPosition());
//                telemetry.addData("Current position", robot.backLeft.getCurrentPosition());
//                telemetry.addData("Current position", robot.backRight.getCurrentPosition());
//
//                telemetry.update();
//            }
//
//            robot.frontLeft.setPower(0.1);
//            robot.frontRight.setPower(0.1);
//            robot.backLeft.setPower(0.1);
//            robot.backRight.setPower(0.1);
//            sleep(100);
//            robot.frontLeft.setPower(0);
//            robot.frontRight.setPower(0);
//            robot.backLeft.setPower(0);
//            robot.backRight.setPower(0);
//
//            robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//            robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        }
//    }
//
//    public void moveStrafe(double speed, double distance_counts, double timeout, boolean right) {
//
//        int targetfl;
//        int targetfr;
//        int targetbl;
//        int targetbr;
//
//        if (opModeIsActive()) {
//
//
//            if (right) {
//                targetfl = robot.frontLeft.getCurrentPosition() - (int)distance_counts;
//                targetfr = robot.frontRight.getCurrentPosition() + (int)distance_counts;
//                targetbl = robot.backLeft.getCurrentPosition() + (int)distance_counts;
//                targetbr = robot.backRight.getCurrentPosition() - (int)distance_counts;
//            }
//            else {
//                targetfl = robot.frontLeft.getCurrentPosition() + (int)distance_counts;
//                targetfr = robot.frontRight.getCurrentPosition() - (int)distance_counts;
//                targetbl = robot.backLeft.getCurrentPosition() - (int)distance_counts;
//                targetbr = robot.backRight.getCurrentPosition() + (int)distance_counts;
//            }
//            robot.frontLeft.setTargetPosition(targetfl);
//            robot.frontRight.setTargetPosition(targetfr);
//            robot.backLeft.setTargetPosition(targetbl);
//            robot.backRight.setTargetPosition(targetbr);
//
//            robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//            runtime.reset();
//            robot.frontLeft.setPower(speed);
//            robot.frontRight.setPower(speed);
//            robot.backLeft.setPower(speed);
//            robot.backRight.setPower(speed);
//
//
//            while(opModeIsActive() &&
//                    (runtime.seconds() < timeout) &&
//                    (robot.frontLeft.isBusy())) {
//
//                telemetry.addData("Current position", robot.frontLeft.getCurrentPosition());
//                telemetry.addData("Current position", robot.frontRight.getCurrentPosition());
//                telemetry.addData("Current position", robot.backLeft.getCurrentPosition());
//                telemetry.addData("Current position", robot.backRight.getCurrentPosition());
//
//                telemetry.update();
//            }
//
////            if (right) {
////                robot.frontLeft.setPower(-0.1);
////                robot.frontRight.setPower(0.1);
////                robot.backLeft.setPower(0.1);
////                robot.backRight.setPower(-0.1);
////            }
////            else {
////                robot.frontLeft.setPower(0.1);
////                robot.frontRight.setPower(-0.1);
////                robot.backLeft.setPower(-0.1);
////                robot.backRight.setPower(0.1);
////            }
//            sleep(100);
//            robot.frontLeft.setPower(0);
//            robot.frontRight.setPower(0);
//            robot.backLeft.setPower(0);
//            robot.backRight.setPower(0);
//
//            robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//            robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        }
//    }
//
//    public void moveTurn(double speed, double distance_counts, double timeout, boolean left) {
//
//        int targetfl;
//        int targetfr;
//        int targetbl;
//        int targetbr;
//
//        if (opModeIsActive()) {
//
//
//            if (left) {
//                targetfl = robot.frontLeft.getCurrentPosition() + (int)distance_counts;
//                targetfr = robot.frontRight.getCurrentPosition() - (int)distance_counts;
//                targetbl = robot.backLeft.getCurrentPosition() + (int)distance_counts;
//                targetbr = robot.backRight.getCurrentPosition() - (int)distance_counts;
//            }
//            else {
//                targetfl = robot.frontLeft.getCurrentPosition() - (int)distance_counts;
//                targetfr = robot.frontRight.getCurrentPosition() + (int)distance_counts;
//                targetbl = robot.backLeft.getCurrentPosition() - (int)distance_counts;
//                targetbr = robot.backRight.getCurrentPosition() + (int)distance_counts;
//            }
//            robot.frontLeft.setTargetPosition(targetfl);
//            robot.frontRight.setTargetPosition(targetfr);
//            robot.backLeft.setTargetPosition(targetbl);
//            robot.backRight.setTargetPosition(targetbr);
//
//            robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//            runtime.reset();
//            robot.frontLeft.setPower(speed);
//            robot.frontRight.setPower(speed);
//            robot.backLeft.setPower(speed);
//            robot.backRight.setPower(speed);
//
//
//            while(opModeIsActive() &&
//                    (runtime.seconds() < timeout) &&
//                    (robot.frontLeft.isBusy())) {
//
//                telemetry.addData("Current position", robot.frontLeft.getCurrentPosition());
//                telemetry.addData("Current position", robot.frontRight.getCurrentPosition());
//                telemetry.addData("Current position", robot.backLeft.getCurrentPosition());
//                telemetry.addData("Current position", robot.backRight.getCurrentPosition());
//
//                telemetry.update();
//            }
//
//            if (left) {
//                robot.frontLeft.setPower(0.1);
//                robot.frontRight.setPower(-0.1);
//                robot.backLeft.setPower(0.1);
//                robot.backRight.setPower(-0.1);
//            }
//            else {
//                robot.frontLeft.setPower(-0.1);
//                robot.frontRight.setPower(0.1);
//                robot.backLeft.setPower(-0.1);
//                robot.backRight.setPower(0.1);
//            }
//            sleep(100);
//            robot.frontLeft.setPower(0);
//            robot.frontRight.setPower(0);
//            robot.backLeft.setPower(0);
//            robot.backRight.setPower(0);
//
//            robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//            robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        }
//    }
//
//    public void moveCarousel(double speed, double distance_counts, double timeout) {
//
//        int targetcarousel;
//
//        if (opModeIsActive()) {
//
//            targetcarousel = (int) (robot.carouselMotor.getCurrentPosition() + (speed*(int)distance_counts));
//            robot.carouselMotor.setTargetPosition(targetcarousel);
//
//            robot.carouselMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//            runtime.reset();
//            robot.carouselMotor.setPower(speed);
//
//
//            while(opModeIsActive() &&
//                    (runtime.seconds() < timeout) &&
//                    (robot.carouselMotor.isBusy())) {
//
//                telemetry.addData("Current carousel position", robot.carouselMotor.getCurrentPosition());
//
//                telemetry.update();
//            }
//
//            robot.carouselMotor.setPower(0.1*speed);
//            sleep(100);
//            robot.carouselMotor.setPower(0);
//
//            robot.carouselMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//            robot.carouselMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        }
//    }
//
//    public void moveIntake(double speed, double distance_counts, double timeout) {
//
//        int target;
//
//        if (opModeIsActive()) {
//
//            target = robot.intakeMotor.getCurrentPosition() + (int)distance_counts;
//            robot.intakeMotor.setTargetPosition(target);
//
//            robot.intakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//            runtime.reset();
//            robot.intakeMotor.setPower(speed);
//
//
//            while(opModeIsActive() &&
//                    (runtime.seconds() < timeout) &&
//                    (robot.intakeMotor.isBusy())) {
//
//                telemetry.addData("Current intake position", robot.intakeMotor.getCurrentPosition());
//
//                telemetry.update();
//            }
//
//            robot.intakeMotor.setPower(0.1);
//            sleep(100);
//            robot.intakeMotor.setPower(0);
//
//            robot.intakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//            robot.intakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        }
//    }
//
//    public void moveArm(double speed, double distance_counts, double timeout) {
//
//        int target;
//
//        if (opModeIsActive()) {
//
//            target = robot.armMotor.getCurrentPosition() + (int)distance_counts;
//            robot.armMotor.setTargetPosition(target);
//
//            robot.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//            runtime.reset();
//            robot.armMotor.setPower(speed);
//
//
//            while(opModeIsActive() &&
//                    (runtime.seconds() < timeout) &&
//                    (robot.armMotor.isBusy())) {
//
//                telemetry.addData("Current arm position", robot.armMotor.getCurrentPosition());
//
//                telemetry.update();
//            }
//
//            robot.armMotor.setPower(0.1);
//            sleep(100);
//            robot.armMotor.setPower(0);
//
//            robot.armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//            robot.armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        }
//    }
//}
