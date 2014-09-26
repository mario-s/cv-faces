package org.opencv.face.swing;

import java.io.File;
import java.net.URL;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.face.AbstractFaceDetector;
import org.opencv.objdetect.CascadeClassifier;

/**
 *
 * @author spindizzy
 */
public class FaceDetector extends AbstractFaceDetector {

    private static final String CF = "../haarcascade_frontalface_alt.xml";

    private final CascadeClassifier classifier;

    public FaceDetector() {
        URL resource = getClass().getResource(CF);
        File cFile = new File(resource.getPath());
        String cPath = cFile.getPath();

        classifier = new CascadeClassifier(cPath);
    }

    public void markFaces(Mat image) {
        MatOfRect faceRect = findFace(image);
        if (!faceRect.empty()) {
            faceRect.toList().forEach((Rect rect) -> {
                Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), COLOR);
            });
        }
    }

    @Override
    public MatOfRect findFace(Mat image) {
        MatOfRect faceRect = new MatOfRect();
        classifier.detectMultiScale(image, faceRect);
        return faceRect;
    }

}
