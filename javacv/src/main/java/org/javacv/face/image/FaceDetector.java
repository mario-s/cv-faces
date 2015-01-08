/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javacv.face.image;

import java.io.File;
import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.Mat;
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
     * Marks all faces which were found and save the result to a new image.
     *
     * @param provider object which provides the image
     * @param targetFile folder with the saved images
     * 
     * @return <code>true</code> if any face was saved to a new image
     */
    public boolean saveMarkedFaces(ImageProvideable provider, File targetFile) {
        Mat image = provider.provide();
        Rect rect = findFaces(image);
        int limit = rect.limit();
        if (limit > 0) {
            for (int i = 0; i < limit; i++) {
                Rect pos = rect.position(i);
                rectangle(image, pos, color);
            }
            imwrite(targetFile.getPath(), image);
        }
        return limit > 0;
    }

    private Rect findFaces(Mat image) {
        Rect rect = new Rect();
        classifier.detectMultiScale(image, rect);
        return rect;
    }
}
