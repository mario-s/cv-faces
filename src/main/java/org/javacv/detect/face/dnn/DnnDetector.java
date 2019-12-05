package org.javacv.detect.face.dnn;

import org.bytedeco.javacpp.indexer.FloatIndexer;
import org.bytedeco.javacpp.opencv_imgproc;
import org.javacv.detect.AbstractDetector;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_dnn.Net;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static org.bytedeco.javacpp.opencv_imgproc.rectangle;
import static org.javacv.common.ImageUtil.resize;
import static org.bytedeco.javacpp.opencv_core.CV_32F;
import static org.bytedeco.javacpp.opencv_dnn.readNetFromCaffe;
import static org.bytedeco.javacpp.opencv_dnn.blobFromImage;

/**
 * Detector which uses a pretrained model to detect faces.
 */
public class DnnDetector extends AbstractDetector {

    private static final Logger LOG = LoggerFactory.getLogger(DnnDetector.class);

    private static final double THRESHOLD = 0.8;
    private static final double SCALE_FACTOR = 1.0;

    private static final String PROTO_FILE = "deploy.proto.txt";
    private static final String CAFFE_MODEL_FILE = "res10_300x300_ssd_iter_140000.caffemodel";

    private static final Size TARGET_SIZE = new Size(300, 300);
    private static final Scalar MEAN = new Scalar(104.0, 177.0, 123.0, 0);

    private final Net net;

    public DnnDetector() {
        this(PROTO_FILE, CAFFE_MODEL_FILE);
    }

    public DnnDetector(String protoFile, String model) {
        this.net = readNetFromCaffe(getResourcePath(protoFile), getResourcePath(model));
    }

    private String getResourcePath(String name) {
        return getClass().getResource(name).getFile();
    }

    @Override
    public long markObjects(Mat img) {
        Mat resized = resize(img, TARGET_SIZE);
        Mat output = forward(resized);
        FloatIndexer indexer = createIndexer(output);

        long objects = 0;
        for (int i = 0, m = output.size(3); i < m; i++) {
            float confidence = indexer.get(i, 2);
            if (confidence > THRESHOLD) {
                objects++;
                var pos = getPos(indexer, i);
                rectangle(img, pos[0], pos[1], color);
            }
        }

        return objects;
    }

    //forward image to network
    private Mat forward(Mat img) {
        //create a 4-dimensional blob from image
        //for more details @see https://docs.opencv.org/trunk/d6/d0f/group__dnn.html#gabd0e76da3c6ad15c08b01ef21ad55dd8
        Mat input = blobFromImage(img, SCALE_FACTOR, TARGET_SIZE, MEAN, false, false, CV_32F);
        //set the input to network model
        net.setInput(input);

        return net.forward();
    }

    private FloatIndexer createIndexer(Mat output) {
        //extract a 2d matrix
        Mat mat = new Mat(new Size(output.size(3), output.size(2)), CV_32F, output.ptr(0, 0));
        return mat.createIndexer();
    }

    private Point[] getPos(FloatIndexer indexer, int index) {
        int tx = scale(indexer.get(index, 3));
        int ty = scale(indexer.get(index, 4));
        int bx = scale(indexer.get(index, 5));
        int by = scale(indexer.get(index, 6));

        Point pt1 = new Point(tx, ty);
        Point pt2 = new Point(bx, by);
        LOG.debug("({},{})x({},{})", tx, ty, bx, by);

        return new Point[] {pt1, pt2};
    }

    private int scale(float in) {
        return (int) (in * 1280);
    }
}
