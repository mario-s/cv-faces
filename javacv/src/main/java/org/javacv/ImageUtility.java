package org.javacv;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_imgproc;

import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;
import static org.bytedeco.javacpp.opencv_imgproc.CV_RGB2GRAY;

/**
 * Provides methods useful, when dealing with images.
 *
 * @author spindizzy
 */
public enum ImageUtility {

    Instance;

    public Mat read(String path) {
        return imread(path, CV_LOAD_IMAGE_GRAYSCALE);
    }

    public List<Mat> loadImages(Collection<String> pics) {
        List<Mat> imgs = new ArrayList<>();

        pics.forEach(e -> {
            File f = new File(e);
            Mat read = imread(f.getPath());
            imgs.add(read);
        });
        return imgs;
    }

    public void write(Mat img, File out) {
        imwrite(out.getPath(), img);
    }

    public Mat resize(Mat src, Size size) {
        Mat target = new Mat();
        opencv_imgproc.resize(src, target, size);
        return target;
    }

    /**
     * Converts a RGB color image to a gray image.
     * @param src source image
     * @return image as gray
     */
    public Mat toGray(Mat src) {
        Mat target = new Mat(src.size(), src.type());
        opencv_imgproc.cvtColor(src, target, CV_RGB2GRAY);
        return target;
    }
}
