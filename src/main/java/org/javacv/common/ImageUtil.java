package org.javacv.common;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
public final class ImageUtil {

    private ImageUtil() {
        //nothing here
    }

    public static Mat readAsGray(String path) {
        return imread(path, CV_LOAD_IMAGE_GRAYSCALE);
    }

    private static Mat readAsRgb(String path) {
        return imread(path);
    }

    public static List<Mat> read(Collection<String> pics) {
        return pics.stream().map(ImageUtil::readAsRgb).collect(Collectors.toList());
    }

    public static void write(Mat img, File out) {
        imwrite(out.getPath(), img);
    }

    public static Mat resize(Mat src, Size size) {
        Mat target = new Mat();
        opencv_imgproc.resize(src, target, size);
        return target;
    }

    /**
     * Converts a RGB color image to a gray image.
     * @param src source image
     * @return image as gray
     */
    public static Mat toGray(Mat src) {
        Mat target = new Mat(src.size(), src.type());
        opencv_imgproc.cvtColor(src, target, CV_RGB2GRAY);
        return target;
    }
}
