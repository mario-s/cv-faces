package org.opencv.hdr;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import org.opencv.photo.MergeMertens;

import org.opencv.photo.Photo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class loads images with different EL creates a HDR image and saves it as a LDR image.
 */
public class MertensProcessor {
    
    private static final Logger LOG = LoggerFactory.getLogger(MertensProcessor.class);

    public boolean create(Collection<String> pics, File out) {
        boolean val = false;

        if (!pics.isEmpty()) {
            
            List<Mat> images = loadImages(pics);
            
            LOG.debug("number of images to merge: {}", images.size());
            
            Mat fusion = new Mat();
            MergeMertens mergeMertens = Photo.createMergeMertens();
            mergeMertens.process(images, fusion);

            write(fusion, out);
            
            LOG.debug("merged images into: {}", out);

            val = true;
        }

        return val;
    }

    private void write(Mat fusion, File out) {
        Mat result = new Mat();
        Scalar scalar = new Scalar(255, 255, 255);
        Core.multiply(fusion, scalar, result);
        Imgcodecs.imwrite(out.getPath(), result);
    }

    private List<Mat> loadImages(Collection<String> pics) {
        List<Mat> imgs = new ArrayList<>();
        
        pics.forEach(e -> {
            File f = new File(e);
            Mat read = imread(f.getPath());
            imgs.add(read);
        });
        return imgs;
    }

}
