/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javacv.face.image;

import java.io.File;
import java.util.Optional;

import static java.util.Optional.ofNullable;

import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.RectVector;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;

import static org.bytedeco.javacpp.opencv_highgui.CV_FONT_NORMAL;
import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;
import static org.bytedeco.javacpp.opencv_imgproc.putText;
import static org.bytedeco.javacpp.opencv_imgproc.rectangle;

/**
 *
 * @author schroeder
 */
public class FaceDetector {

    private static final String CASCADE_XML = "haarcascade_frontalface_alt_tree.xml";

    private final Scalar color;

    private final CascadeClassifier classifier;
    
    private Optional<FaceRecognitionable> faceRecognator;

    private OpenCVFrameConverter.ToMat converterToMat;
    
    public FaceDetector() {
        this(null);
    }

    public FaceDetector(FaceRecognitionable recognitionable) {
        this.faceRecognator = ofNullable(recognitionable);
        this.color = new Scalar(CvScalar.GREEN);
        this.converterToMat = new OpenCVFrameConverter.ToMat();

        File file = new File(getClass().getResource(CASCADE_XML).getPath());
        this.classifier = new CascadeClassifier(file.getAbsolutePath());
    }

    public boolean hasFace(ImageProvideable provider) {
        return countFaces(provider) > 0;
    }

    public long countFaces(ImageProvideable provider) {
        final RectVector rect = findFaces(provider.provide());
        return rect.size();
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
        RectVector rect = findFaces(image);
        long limit = rect.limit();
        if (limit > 0) {
            for (int i = 0; i < limit; i++) {
                Mat face = extractFace(rect, i, image);
                saveFace(targetFolder, face, i);
            }
        }
        return limit > 0;
    }

    private Mat extractFace(RectVector rect, int position, Mat image) {
        Rect pos = rect.get(position);
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
        long limit = markFaces(image);
        if (limit > 0) {
            imwrite(targetFile.getPath(), image);
        }
        return limit > 0;
    }
    
    public long markFaces(Frame img){
        return markFaces(converterToMat.convert(img));
    }

    /**
     * Marks the faces on the image.
     *
     * @param image
     * @return number of detected faces.
     */
    public long markFaces(Mat image) {
        RectVector rect = findFaces(image);
        long size = rect.size();
        for (int i = 0; i < size; i++) {
            Rect pos = rect.get(i);
            rectangle(image, pos, color);
            predict(image, pos);
        }
        return size;
    }

    private void predict(Mat image, Rect pos) {
        if(faceRecognator.isPresent()){
            Mat face = image.apply(pos);
            face = ImageUtility.Instance.toGrayscale(face);
            String lbl = getLabel(face);
            Point point = new Point(pos.x(), pos.y() - 3);
            putText(image, lbl, point, CV_FONT_NORMAL, 0.5, color);
        }
    }

    private String getLabel(Mat face) {
        String val = "female";
        int lbl = faceRecognator.get().predict(face);
        if(lbl == 1){
            val = "male";
        }
        return val;
    }

    private RectVector findFaces(Mat image) {
        RectVector rect = new RectVector();
        classifier.detectMultiScale(image, rect);
        return rect;
    }
}
