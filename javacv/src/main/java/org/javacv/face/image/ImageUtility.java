package org.javacv.face.image;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_imgproc;

import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgproc.CV_RGB2GRAY;

/**
 * Provides methods usefull, when dealing with images.
 *
 * @author spindizzy
 */
enum ImageUtility {

    Instance;

    Mat read(String path) {
        return imread(path, CV_LOAD_IMAGE_GRAYSCALE);
    }

    Mat resize(Mat src, Size size) {
        Mat target = new Mat();
        opencv_imgproc.resize(src, target, size);
        return target;
    }
    
    Mat toGrayscale(Mat src) {
        Mat target = new Mat();
        opencv_imgproc.cvtColor(src, target, CV_RGB2GRAY);
        return target;
    }
}
