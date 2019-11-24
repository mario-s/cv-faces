package org.javacv.fusion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bytedeco.javacpp.opencv_core.Mat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.javacv.common.ImageUtil.toGray;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.warpAffine;
import static org.bytedeco.javacpp.opencv_video.findTransformECC;

/**
 * An affine transform is a combination of rotation, translation ( shift ), scale, and shear.
 * This transform has six parameters. When a square undergoes an Affine transformation,
 * parallel lines remain parallel, but lines meeting at right angles no longer remain orthogonal.
 */
public class AffineAlignmentProcess {

    private static final Logger LOG = LoggerFactory.getLogger(AffineAlignmentProcess.class);

    private final Mat warpMatrix;
    
    public AffineAlignmentProcess() {
        warpMatrix = Mat.eye(2, 3, CV_32F).asMat();
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
            //warp each other image
            result.addAll(tail.stream().map(i -> warp(headGray, i)).collect(Collectors.toList()));

            LOG.debug("aligned {} images", size);

            return result;
        }

        //just return images if there is only one
        return images;
    }

    private Mat warp(Mat template, Mat img) {
        findTransformECC(template, toGray(img), warpMatrix);

        Mat dest = new Mat();
        warpAffine(img, dest, warpMatrix, template.size());

        return dest;
    }

}
