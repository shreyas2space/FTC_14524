package org.firstinspires.ftc.teamcode;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;

import org.firstinspires.ftc.robotcore.external.android.util.Size;
import org.firstinspires.ftc.robotcore.external.function.Continuation;
import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraCaptureRequest;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraCaptureSequenceId;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraCaptureSession;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraCharacteristics;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraException;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraFrame;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraManager;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.internal.collections.EvictingBlockingQueue;
import org.firstinspires.ftc.robotcore.internal.network.CallbackLooper;
import org.firstinspires.ftc.robotcore.internal.system.ContinuationSynchronizer;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;

import java.io.File;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import android.os.Handler;

import androidx.annotation.NonNull;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.Tensor;
import org.tensorflow.lite.support.common.FileUtil;
import org.tensorflow.lite.support.common.TensorProcessor;
import org.tensorflow.lite.support.common.ops.NormalizeOp;
import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.image.ops.ResizeOp;
import org.tensorflow.lite.support.label.TensorLabel;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "blueAutoS0", group = "TeleOp")
public class blueAutoS0 extends LinearOpMode {

    Hardware robot = new Hardware();

    private final String TAG = "blueAutoS0";

    private CameraManager cameraManager;
    private Camera camera;
    private CameraCaptureSession cameraCaptureSession;

    private static final int secondsPermissionTimeout = 3; //max wait time for permission

    private EvictingBlockingQueue<Bitmap> frameQueue;

    private Handler callbackHandler;
    @Override
    public void runOpMode() throws InterruptedException {
        robot.initialize(hardwareMap);

        telemetry.addData("Webcam attached" , robot.webcam.isAttached());

        cameraManager = ClassFactory.getInstance().getCameraManager();
        callbackHandler = CallbackLooper.getDefault().getHandler();

        initializeFrameQueue(2);

        try {
            openCamera();
            if (camera == null) return;

            startCamera();
            if (cameraCaptureSession == null) return;


            waitForStart();

            boolean pressSeen = false;
            boolean captureWhenAvailable = false;
            while(opModeIsActive()) {

                boolean pressed = gamepad1.a;
                if (pressed && !pressSeen) {
                    captureWhenAvailable = true;
                }
                pressSeen = true;

                if (captureWhenAvailable) {
                    Bitmap bmp = frameQueue.poll();
                    if (bmp != null) {
                        captureWhenAvailable = false;
                        onNewFrame(bmp);
                    }
                }
                idle();
            }

        } finally {
            closeCamera();
        }

    }

    /** Camera Utilities **/
    private void initializeFrameQueue(int capacity) {
        frameQueue = new EvictingBlockingQueue<Bitmap>(new ArrayBlockingQueue<Bitmap>(capacity));
        frameQueue.setEvictAction((frame) -> {
            //remove any excess frames from the queue
            frame.recycle();
        });
    }

    private void onNewFrame(Bitmap bmp) {
        String result = null;

        ImageProcessor imageProcessor =
                new ImageProcessor.Builder()
                        .add(new ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR))
                        .build();

        TensorImage tensorImage = new TensorImage(DataType.UINT8);

        tensorImage.load(bmp);
        tensorImage = imageProcessor.process(tensorImage);

        TensorBuffer probabilityBuffer =
                TensorBuffer.createFixedSize(new int[]{1, 1001}, DataType.UINT8);

        final String ASSOCIATED_AXIS_LABELS = "labels.txt";
        List<String> associatedAxisLabels = null;

        try {
            associatedAxisLabels = FileUtil.loadLabels(hardwareMap.appContext, ASSOCIATED_AXIS_LABELS);
        } catch (IOException e) {
            telemetry.log().add("Error reading labels");
        }

        try{
            MappedByteBuffer tfliteModel
                    = FileUtil.loadMappedFile(hardwareMap.appContext,
                    "assets.converted_tflite/model_unquant.tflite");
            try (Interpreter tflite = new Interpreter(tfliteModel)){
                if(null != tflite) {
                    tflite.run(tensorImage.getBuffer(), probabilityBuffer.getBuffer());
                }


            }
        } catch (IOException e){
            telemetry.log().add("Error reading model");
        }

        TensorProcessor probabilityProcessor =
                new TensorProcessor.Builder().add(new NormalizeOp(0, 255)).build();

        if (null != associatedAxisLabels) {
            // Map of labels and their corresponding probability
            TensorLabel labels = new TensorLabel(associatedAxisLabels,
                    probabilityProcessor.process(probabilityBuffer));

            // Create a map to access the result based on label
            Map<String, Float> floatMap = labels.getMapWithFloatValue();

            Map.Entry<String, Float> maxEntry = null;

            for (Map.Entry<String, Float> entry : floatMap.entrySet())
            {
                if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
                {
                    maxEntry = entry;
                }
            }

            result = maxEntry.getKey();
        }



        telemetry.log().add("Image Processed");
        telemetry.addData("Image result", result);
        telemetry.update();

    }

    private void startCamera() {
        if (cameraCaptureSession != null) return;

        final int imageFormat = ImageFormat.YUY2;

        //make sure image format is readable by webcam
        CameraCharacteristics cameraCharacteristics = robot.webcam.getCameraCharacteristics();
        if (!contains(cameraCharacteristics.getAndroidFormats(), imageFormat)) {
            telemetry.log().add("Image format not supported");
            telemetry.update();
            return;
        }
        final Size size = cameraCharacteristics.getDefaultSize(imageFormat);
        final int fps = cameraCharacteristics.getMaxFramesPerSecond(imageFormat, size);

        //add a synchronizer to wait until everything finishes
        final ContinuationSynchronizer<CameraCaptureSession> synchronizer = new ContinuationSynchronizer<>();
        try {
            //create session to request camera
            camera.createCaptureSession(Continuation.create(callbackHandler, new CameraCaptureSession.StateCallbackDefault() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    try {
                        //start requesting frames
                        final CameraCaptureRequest captureRequest = camera.createCaptureRequest(imageFormat, size, fps);
                        session.startCapture(captureRequest, new CameraCaptureSession.CaptureCallback() {
                            @Override
                            public void onNewFrame(@NonNull CameraCaptureSession session, @NonNull CameraCaptureRequest request, @NonNull CameraFrame cameraFrame) {
                                //copy frame into bitmap
                                Bitmap bmp =captureRequest.createEmptyBitmap();
                                cameraFrame.copyToBitmap(bmp);
                                frameQueue.offer(bmp);
                            }
                        },Continuation.create(callbackHandler, new CameraCaptureSession.StatusCallback() {
                            @Override
                            public void onCaptureSequenceCompleted(@NonNull CameraCaptureSession session, CameraCaptureSequenceId cameraCaptureSequenceId, long lastFrameNumber) {
                                RobotLog.ii(TAG, "capture sequence %s reports completed: lastFrame=%d", cameraCaptureSequenceId, lastFrameNumber);
                            }
                        }));
                    } catch (CameraException|RuntimeException e) {
                        telemetry.log().add("exception starting capture");
                        telemetry.update();
                        synchronizer.finish(null);
                    }
                }
            }));
        } catch (CameraException|RuntimeException e) {
            telemetry.log().add("exception starting camera");
            telemetry.update();
            synchronizer.finish(null);
        }

        //wait for everything to catch up
        try {
            synchronizer.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        cameraCaptureSession = synchronizer.getValue();
    }

    public void openCamera() {
        if (camera != null) return; //don't do anything if called already

        Deadline deadline = new Deadline(secondsPermissionTimeout, TimeUnit.SECONDS);
        camera = cameraManager.requestPermissionAndOpenCamera(deadline, robot.webcam, null);

        if (camera == null) {
            telemetry.log().add("Camera not found or incorrect permission");
            telemetry.update();
        }
    }

    public void closeCamera() {
        if (cameraCaptureSession != null) {
            cameraCaptureSession.stopCapture();
            cameraCaptureSession.close();
            cameraCaptureSession = null;
        }

        if (camera != null) {
            camera.close();
            camera = null;
        }
    }

    /**Tensorflow Basics **/
    public void createModel(File modelFile) {
        try (Interpreter interpreter = new Interpreter(modelFile)) {

        }
    }


    /** Basic Utilities **/
    private boolean contains(int[] androidFormats, int imageFormat) {
        for (int format : androidFormats) {
            if (format == imageFormat) return true;
        }
        return false;
    }
}
