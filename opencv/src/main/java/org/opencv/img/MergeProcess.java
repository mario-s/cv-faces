package org.opencv.img;

import java.io.File;
import java.util.Collection;
import java.util.List;
import org.opencv.core.Mat;
import org.opencv.photo.MergeMertens;
import org.opencv.photo.Photo;

/**
 *
 * Process to merge images.
 */
public class MergeProcess {

    public Mat merge(List<Mat> images) {

        Mat fusion = new Mat();
        MergeMertens mergeMertens = Photo.createMergeMertens();
        mergeMertens.process(images, fusion);

        return fusion;
    }
}
