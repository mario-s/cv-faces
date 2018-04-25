package org.opencv.img;

import java.util.ArrayList;
import java.util.List;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.Video;

/**
 *
 * Aligns images.
 */
public class AlignProcess {
    
    private final Mat warpMatrix;
    
    public AlignProcess() {
        warpMatrix = Mat.eye(3, 3, CvType.CV_32F);
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
        Video.findTransformECC(first, toGray(img), warpMatrix, Video.MOTION_HOMOGRAPHY);
        Size imgSize = img.size();
        Mat dest = new Mat();
        Imgproc.warpPerspective(img, dest, warpMatrix, imgSize);
        return dest;
    }

    private Mat toGray(Mat bgr) {
        Mat gray = new Mat();
        Imgproc.cvtColor(bgr, gray, Imgproc.COLOR_BGR2GRAY);
        return gray;
    }
}
