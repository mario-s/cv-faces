package org.opencv.face.video.javafx;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import org.opencv.core.Mat;
import org.opencv.face.video.FaceDetector;
import org.opencv.face.video.ImageConverter;
import org.opencv.highgui.VideoCapture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author spindizzy
 */
public class CameraTask extends Task<Void> {

    private static final Logger LOG = LoggerFactory.getLogger(CameraTask.class);

    private final FaceDetector faceDetector;

    private final VideoCapture capture;

    private final ObjectProperty<Image> imageProperty;

    public CameraTask(ObjectProperty<Image> imageProperty) {
        this.imageProperty = imageProperty;

        this.faceDetector = new FaceDetector();
        this.capture = new VideoCapture(0);
    }

    @Override
    protected Void call() throws Exception {
        
        LOG.info("starting to grap images");
        while (!isCancelled()) {
            Mat webcamImage = new Mat();
            capture.read(webcamImage);

            if (!webcamImage.empty()) {
                faceDetector.markFaces(webcamImage);
                Platform.runLater(() -> {
                    Image image = ImageConverter.toJavaFXImage(webcamImage);
                    if (image != null) {
                        imageProperty.set(image);
                    } else {
                        LOG.warn("No image!");
                    }
                });

            }
        }
        return null;
    }

    @Override
    protected void cancelled() {
        capture.release();
    }

}
