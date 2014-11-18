package org.opencv.face.image;

import java.io.File;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.face.AbstractFaceDetector;
import org.opencv.face.ClassifierFactory;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;

import static com.google.common.collect.Lists.newArrayList;

/**
 *
 * @author spindizzy
 */
public class ImageFaceDetector extends AbstractFaceDetector{
    
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

    /**
     * Returns <code>true</code> when at least one face is found, otherwise
     * <code>false</code>.
     * @param imgFile
     * @return boolean
     */
    public boolean containsFace(File imgFile) {
        return !findFace(readImage(imgFile)).empty();
    }

    /**
     * Marks all faces which were found and save the result to a new image.
     * @param sourceFile image to look for faces
     * @param targetFile image with the marked faces.
     */
    public void saveMarkedFaces(File sourceFile, File targetFile) {
        Mat image = readImage(sourceFile);
        MatOfRect faceRect = findFace(image);
        if (!faceRect.empty()) {
            faceRect.toList().forEach((Rect rect) -> {
                //draw the rectangle
                Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), COLOR);
            });
            Highgui.imwrite(targetFile.getPath(), image);
        }
    }
    
    /**
     * Extracts all faces which were found to new image files in the given directory.
     * @param sourceFile image to look for faces
     * @param targetDirectory directory to save extracted faces.
     */
    public void extractFaces(File sourceFile, File targetDirectory) {
        Mat image = readImage(sourceFile);
        MatOfRect faceRect = findFace(image);
        if (!faceRect.empty()) {
            int id = 0;
            for(Rect rect : faceRect.toList()){
                //extract the face
                Mat face = image.submat(rect);
                File targetFile = createFaceFile(sourceFile, id, targetDirectory);
                Highgui.imwrite(targetFile.getPath(), face);
                id++;
            }
        }
    }

    private File createFaceFile(File sourceFile, int id, File targetDirectory) {
        String sourceName = sourceFile.getName();
        int dotPos = sourceName.lastIndexOf(".");
        String name = sourceName.substring(0, dotPos) + "_face_" + id + ".jpg";
        File targetFile = new File(targetDirectory, name);
        return targetFile;
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
