package org.javacv.detect.face.dnn;

import org.javacv.detect.AbstractDetector;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_dnn.Net;

import static org.bytedeco.javacpp.opencv_dnn.readNetFromCaffe;

/**
 * Detector which uses a pretrained model to detect faces.
 */
public class DnnDetector extends AbstractDetector {

    private static final String PROTO_FILE = "deploy.proto.txt";
    private static final String CAFFE_MODEL_FILE = "res10_300x300_ssd_iter_140000.caffemodel";

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
        return 0;
    }
}
