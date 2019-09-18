package org.javacv.img;

import java.util.ArrayList;
import java.util.List;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.javacv.ImageUtility;

import static org.bytedeco.javacpp.opencv_core.CV_32F;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.warpPerspective;
import static org.bytedeco.javacpp.opencv_video.findTransformECC;
import static org.bytedeco.javacpp.opencv_imgproc.COLOR_BGR2GRAY;

/**
 *
 * Aligns images.
 */
public class AlignProcess {
    
    private final ImageUtility imageUtility;
    
    private final Mat warpMatrix;
    
    public AlignProcess() {
        imageUtility = ImageUtility.Instance;
        warpMatrix = Mat.eye(3, 3, CV_32F).a();
    }

    public List<Mat> align(List<Mat> images) {
        final int size = images.size();
        if (size > 1) {
            List<Mat> result = new ArrayList<>(size);
            Mat head = images.get(0);
            result.add(head);
            List<Mat> tail = images.subList(1, size);
            
            //convert first image to gray
            Mat headGray = toGray(head);
            //transform
            tail.forEach(img -> {
                Mat dest = warp(headGray, img);
                result.add(dest);
            });
            
            return result;
        }

        return images;
    }

    private Mat warp(Mat first, Mat img) {
        findTransformECC(toGray(img), first, warpMatrix);
        Mat dest = new Mat();
        warpPerspective(img, dest, warpMatrix, first.size());
        return dest;
    }

    private Mat toGray(Mat bgr) {
        return imageUtility.toGrayscale(bgr);
    }
}
