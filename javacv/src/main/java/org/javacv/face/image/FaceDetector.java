/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javacv.face.image;

import java.io.File;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.Scalar;
import static org.bytedeco.javacpp.opencv_core.rectangle;
import static org.bytedeco.javacpp.opencv_highgui.imwrite;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;

/**
 *
 * @author schroeder
 */
public class FaceDetector {

    private static final String CASCADE_XML = "haarcascade_frontalface_alt_tree.xml";

    private final Scalar color;

    private final CascadeClassifier classifier;

    public FaceDetector() {
        color = new Scalar(CvScalar.GREEN);
        File file = new File(getClass().getResource(CASCADE_XML).getPath());
        this.classifier = new CascadeClassifier(file.getAbsolutePath());
    }

    public boolean hasFace(ImageProvideable provider) {
        return countFaces(provider) > 0;
    }

    public int countFaces(ImageProvideable provider) {
        final Rect rect = findFaces(provider.provide());
        return rect.limit();
    }

    /**
     * Extracts all faces from the source image and saves it to a new folder.
     *
     * @param provider object which provides the image
     * @param targetFolder folder with extracted images
     * @return <code>true</code> if any face was extracted and saved in the
     * folder
     */
    public boolean extractFaces(ImageProvideable provider, File targetFolder) {
        Mat image = provider.provide();
        Rect rect = findFaces(image);
        int limit = rect.limit();
        if (limit > 0) {
            for (int i = 0; i < limit; i++) {
                Mat face = extractFace(rect, i, image);
                saveFace(targetFolder, face, i);
            }
        }
        return limit > 0;
    }

    private Mat extractFace(Rect rect, int position, Mat image) {
        Rect pos = rect.position(position);
        return image.apply(pos);
    }

    private void saveFace(File targetFolder, Mat face, int position) {
        String name = position + ".jpg";
        File f = new File(targetFolder, name);
        imwrite(f.getPath(), face);
    }

    /**
     * Marks all faces which were found and save the result to a new image.
     *
     * @param provider object which provides the image
     * @param targetFile file with the marked faces
     * @return <code>true</code> if any face was marked and saved to the image
     */
    public boolean saveMarkedFaces(ImageProvideable provider, File targetFile) {
        Mat image = provider.provide();
        int limit = markFaces(image);
        if (limit > 0) {
            imwrite(targetFile.getPath(), image);
        }
        return limit > 0;
    }
    
    public int markFaces(IplImage img){
        return markFaces(new Mat(img));
    }

    /**
     * Marks the faces on the image.
     *
     * @param image
     * @return number of detected faces.
     */
    public int markFaces(Mat image) {
        Rect rect = findFaces(image);
        int limit = rect.limit();
        if (limit > 0) {
            for (int i = 0; i < limit; i++) {
                Rect pos = rect.position(i);
                rectangle(image, pos, color);
            }
        }
        return limit;
    }

    private Rect findFaces(Mat image) {
        Rect rect = new Rect();
        classifier.detectMultiScale(image, rect);
        return rect;
    }
}
