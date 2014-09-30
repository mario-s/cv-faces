package org.opencv.face.video.javafx;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
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

    private final ImageView videoView;

    public CameraTask(ImageView videoView) {
        this.videoView = videoView;
        this.faceDetector = new FaceDetector();
        this.capture = new VideoCapture(0);
    }

    @Override
    protected Void call() throws Exception {
        LOG.info("starting to get image");
        while (!isCancelled()) {
            Mat webcamImage = new Mat();
            LOG.info("trying to read from webcam");
            capture.read(webcamImage);
            LOG.info("trying to mark faces");
            faceDetector.markFaces(webcamImage);
            LOG.info("trying to convert");
            Image image = ImageConverter.toJavaFXImage(webcamImage);
            if (image != null) {
                LOG.info("got image");
                videoView.setImage(image);
            } else {
                LOG.warn("No image!");
            }
        }
        LOG.info("finished");
        return null;
    }

    @Override
    protected void cancelled() {
        capture.release();
    }

}
