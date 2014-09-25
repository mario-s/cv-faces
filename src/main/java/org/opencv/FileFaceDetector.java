package org.opencv;

import java.io.File;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.highgui.Highgui;

/**
 *
 * @author spindizzy
 */
public class FileFaceDetector extends FaceDetector{

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
    
}
