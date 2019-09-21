package org.javacv.img;

import java.util.List;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.bytedeco.javacpp.opencv_photo.MergeMertens;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.bytedeco.javacpp.opencv_core.multiply;
import static org.bytedeco.javacpp.opencv_photo.createMergeMertens;

/**
 *
 * Process to merge images.
 */
public class MergeProcess {

    private static final Logger LOG = LoggerFactory.getLogger(MergeProcess.class);

    private static final Scalar WHITE = new Scalar(255, 255, 255, 0);

    private final MergeMertens mergeMertens;

    public MergeProcess() {
        mergeMertens = createMergeMertens();
    }

    public Mat merge(List<Mat> images) {
        return merge(images.toArray(new Mat[images.size()]));
    }

    public Mat merge(Mat[] images) {

        MatVector vector = new MatVector(images);
        Mat fusion = new Mat();
        mergeMertens.process(vector, fusion);

        Mat filter = new Mat(fusion.size(), fusion.type(), WHITE);
        multiply(fusion, filter, fusion);

        LOG.debug("merged {} images", images.length);

        return fusion;
    }
}
