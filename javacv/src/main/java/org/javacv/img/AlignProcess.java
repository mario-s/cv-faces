package org.javacv.img;

import java.util.ArrayList;
import java.util.List;

import org.bytedeco.javacpp.opencv_core.TermCriteria;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_imgproc;
import org.javacv.ImageUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.CV_RGB2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.warpPerspective;
import static org.bytedeco.javacpp.opencv_imgproc.warpAffine;
import static org.bytedeco.javacpp.opencv_video.findTransformECC;
import static org.bytedeco.javacpp.opencv_video.MOTION_HOMOGRAPHY;

/**
 *
 * Aligns images.
 */
public class AlignProcess {

    private static final Logger LOG = LoggerFactory.getLogger(AlignProcess.class);
    
    private final ImageUtility imageUtility;
    
    private final Mat warpMatrix;

    private final TermCriteria termCriteria;
    
    public AlignProcess() {
        imageUtility = ImageUtility.Instance;
        warpMatrix = Mat.eye(2, 3, CV_32F).asMat();
        termCriteria = new TermCriteria(CV_TERMCRIT_EPS + CV_TERMCRIT_ITER, 50, 1e-10);
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

    private Mat warp(Mat template, Mat img) {
        LOG.debug("========> transform...");
        Mat gray = toGray(img);

        findTransformECC(template, gray, warpMatrix);

        LOG.debug("========> warp speed...");
        Mat dest = new Mat();
        warpAffine(img, dest, warpMatrix, template.size());

        LOG.debug("========> landing...");
        return dest;
    }

    private Mat toGray(Mat src) {
        Mat target = new Mat(src.size(), CV_32F);
        opencv_imgproc.cvtColor(src, target, CV_RGB2GRAY);
        return target;
    }
}
