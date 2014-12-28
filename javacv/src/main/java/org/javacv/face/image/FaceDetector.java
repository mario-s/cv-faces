/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javacv.face.image;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;

/**
 *
 * @author schroeder
 */
public class FaceDetector {

    private static final String CASCADE_XML = "haarcascade_frontalface_alt.xml";

    private final CascadeClassifier classifier;

    public FaceDetector() {
        String path = getClass().getResource(CASCADE_XML).getPath();
        this.classifier = new CascadeClassifier(path);
    }

    public boolean isFace(ImageProvideable provider) {
        final Rect rect = findFaces(provider.provide());
        return rect.capacity() > 0;
    }

    public Rect findFaces(Mat image) {
        Rect rect = new Rect();
        classifier.detectMultiScale(image, rect);
        return rect;
    }
}
