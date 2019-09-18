package org.javacv.img;

import java.util.List;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.bytedeco.javacpp.opencv_photo.MergeMertens;

import static org.bytedeco.javacpp.opencv_photo.createMergeMertens;

/**
 *
 * Process to merge images.
 */
public class MergeProcess {

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

        return fusion;
    }
}
