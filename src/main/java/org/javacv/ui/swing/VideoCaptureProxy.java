package org.javacv.ui.swing;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_videoio.VideoCapture;

class VideoCaptureProxy {
    private final VideoCapture capture;

    VideoCaptureProxy() {
        capture = new VideoCapture(0);
    }

    boolean read(Mat image) {
        return capture.read(image);
    }

    void release() {
        capture.release();
    }
}
