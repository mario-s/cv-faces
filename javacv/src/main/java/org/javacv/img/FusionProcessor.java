package org.javacv.img;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.javacv.common.ImageUtility;
import org.bytedeco.javacpp.opencv_core.Mat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class loads images with different EL creates a HDR image and saves it as a LDR image.
 */
public class FusionProcessor {
    
    private static final Logger LOG = LoggerFactory.getLogger(FusionProcessor.class);

    private final ImageUtility imageUtility = ImageUtility.Instance;

    public boolean process(Collection<String> pics, File out) {
        boolean success = false;

        if (!pics.isEmpty()) {
            LOG.debug("number of images to process: {}", pics.size());
            
            List<Mat> images = imageUtility.loadImages(pics);
            
            List<Mat> aligned = new AffineAlignmentProcess().align(images);
            Mat merge = new MergeProcess().merge(aligned);

            imageUtility.write(merge, out);
            
            LOG.debug("processed images into: {}", out);

            success = true;
        }

        return success;
    }



}
