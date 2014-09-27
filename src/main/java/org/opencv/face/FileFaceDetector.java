package org.opencv.face;

import java.io.File;
import java.net.URL;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;

import static com.google.common.collect.Lists.newArrayList;

/**
 *
 * @author spindizzy
 */
public class FileFaceDetector extends AbstractFaceDetector{
    
    private static final List<String> CLASSIFIER_FILES = newArrayList(
            //        "haarcascade_eye.xml",
            //        "haarcascade_eye_tree_eyeglasses.xml",
            //        "haarcascade_frontalface_alt.xml",
            //        "haarcascade_frontalface_alt2.xml",
            "haarcascade_frontalface_alt_tree.xml",
            //        "haarcascade_frontalface_default.xml",
            "haarcascade_fullbody.xml",
            "haarcascade_profileface.xml"
    //        "haarcascade_upperbody.xml"
    );

    public boolean containsFace(File imgFile) {
        return !findFace(readImage(imgFile)).empty();
    }

    public void saveMarkedFaces(File sourceFile, File targetFile) {
        Mat image = readImage(sourceFile);
        MatOfRect faceRect = findFace(image);
        if (!faceRect.empty()) {
            faceRect.toList().forEach((Rect rect) -> {
                Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), COLOR);
            });
            Highgui.imwrite(targetFile.getPath(), image);
        }
    }

    private Mat readImage(File imgFile) {
        Mat image = new Mat();
        if (imgFile != null && imgFile.exists() && imgFile.canRead()) {
            String path = imgFile.getPath();
            image = Highgui.imread(path);
        }
        return image;
    }
    
    @Override
    public MatOfRect findFace(Mat image) {
        MatOfRect faceRect = new MatOfRect();

        if (!image.empty()) {
            CLASSIFIER_FILES.forEach((cf) -> {
                if (faceRect.empty()) {
                    
                    CascadeClassifier classifier = ClassifierFactory.Instance.create(cf);
                    classifier.detectMultiScale(image, faceRect);

                    if (log.isDebugEnabled() && !faceRect.empty()) {
                        log.debug("Detected faces: {}, with: {}", faceRect.toList().size(), cf);
                    }
                }
            });
        }

        return faceRect;
    }
    
}
