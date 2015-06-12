package org.javacv.face.image;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Size;
import static org.bytedeco.javacpp.opencv_highgui.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_highgui.imread;
import org.bytedeco.javacpp.opencv_imgproc;

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
}
