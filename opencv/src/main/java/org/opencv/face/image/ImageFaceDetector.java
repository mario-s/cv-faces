package org.opencv.face.image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.face.AbstractFaceDetector;
import org.opencv.face.ClassifierFactory;
import org.opencv.objdetect.CascadeClassifier;

import static com.google.common.collect.Lists.newArrayList;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.imgproc.Imgproc.rectangle;


/**
 *
 * @author spindizzy
 */
public class ImageFaceDetector extends AbstractFaceDetector {

    private static final String FACE = "_face_";
    private static final String JPG = ".jpg";
    
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
     *
     * @param imgFile
     * @return boolean
     */
    public boolean containsFace(File imgFile) {
        return !findFaces(readImage(imgFile)).empty();
    }

    /**
     * Marks all faces which were found and save the result to a new image.
     *
     * @param sourceFile image to look for faces
     * @param targetFile image with the marked faces.
     */
    public void saveMarkedFaces(File sourceFile, File targetFile) {
        Mat image = readImage(sourceFile);
        MatOfRect faceRect = findFaces(image);
        if (!faceRect.empty()) {
            faceRect.toList().forEach((Rect rect) -> {
                //draw the rectangle
                rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), COLOR);
            });
            imwrite(targetFile.getPath(), image);
        }
    }

    /**
     * Extracts all faces which were found to new image files in the given
     * directory.
     *
     * @param sourceFile image to look for faces
     * @param targetDirectory directory to save extracted faces.
     */
    public void extractFaces(File sourceFile, File targetDirectory) {
        List<Mat> faces = extractFaces(sourceFile);
        int id = 0;
        for (Mat face : faces) {
            File targetFile = createFaceFile(sourceFile, id, targetDirectory);
            imwrite(targetFile.getPath(), face);
            id++;
        }
    }

    private File createFaceFile(File sourceFile, int id, File targetDirectory) {
        String sourceName = sourceFile.getName();
        int dotPos = sourceName.lastIndexOf(".");
        String name = sourceName.substring(0, dotPos) + FACE + id + JPG;
        return new File(targetDirectory, name);
    }

    

    /**
     * Extracts all faces from the given image.
     *
     * @param sourceFile image file.
     * @return all faces which were found.
     */
    public List<Mat> extractFaces(File sourceFile) {
        List<Mat> extractedFaces = new ArrayList<>();
        Mat image = readImage(sourceFile);
        MatOfRect faceRect = findFaces(image);
        faceRect.toList().forEach((Rect rect) -> {
            //extract face
            Mat face = image.submat(rect);
            extractedFaces.add(face);
        });
        return extractedFaces;
    }

    @Override
    public MatOfRect findFaces(Mat image) {
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
    
    private static Mat readImage(File imgFile) {
        Mat image = new Mat();
        if (imgFile != null && imgFile.exists() && imgFile.canRead()) {
            String path = imgFile.getPath();
            image = imread(path);
        }
        return image;
    }

}
