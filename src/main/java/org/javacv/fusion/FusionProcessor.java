package org.javacv.fusion;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.bytedeco.javacpp.opencv_core.Mat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.javacv.common.ImageUtil.*;

/**
 * This class loads images with different EL creates a HDR image and saves it as a LDR image.
 */
public class FusionProcessor {
    
    private static final Logger LOG = LoggerFactory.getLogger(FusionProcessor.class);

    public boolean process(Collection<String> pics, File out) {
        boolean success = false;

        if (!pics.isEmpty()) {
            LOG.debug("number of images to process: {}", pics.size());
            
            List<Mat> images = read(pics);
            
            List<Mat> aligned = new AffineAlignmentProcess().align(images);
            Mat merge = new MergeProcess().merge(aligned);

            write(merge, out);
            
            LOG.debug("processed images into: {}", out);

            success = true;
        }

        return success;
    }
}
