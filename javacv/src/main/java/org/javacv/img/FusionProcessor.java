package org.javacv.img;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.javacv.ImageUtility;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Scalar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class loads images with different EL creates a HDR image and saves it as a LDR image.
 */
public class FusionProcessor {
    
    private static final Logger LOG = LoggerFactory.getLogger(FusionProcessor.class);

    private final ImageUtility imageUtility = ImageUtility.Instance;

    private final Scalar scalar = new Scalar(255);

    public boolean process(Collection<String> pics, File out) {
        boolean success = false;

        if (!pics.isEmpty()) {
            LOG.debug("number of images to merge: {}", pics.size());
            
            List<Mat> images = imageUtility.loadImages(pics);
            
            List<Mat> aligned = new AlignProcess().align(images);
            Mat merge = new MergeProcess().merge(aligned);
            Mat result = multipy(merge);

            imageUtility.write(result, out);
            
            LOG.debug("merged images into: {}", out);

            success = true;
        }

        return success;
    }

    private Mat multipy(Mat src) {
        Mat filter = new Mat(src.size(), src.type(), scalar);
        return src.mul(filter).a();
    }

}
