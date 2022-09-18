package org.javacv.ui.swing;

import org.javacv.detect.Detectable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.bytedeco.javacpp.opencv_core.Mat;

@ExtendWith(MockitoExtension.class)
class CameraWorkerTest {

    @Mock
    private Detectable detector;
    @Mock
    private VideoCaptureProxy capture;

    private CameraWorker classUnderTest;

    @BeforeEach
    void setUp() {
        classUnderTest = new CameraWorker(s -> s.area(), m -> m.rows(),
            detector, capture);
    }

    @Test
    @DisplayName("It should read an image from capture in background")
    void testDoInBackground() throws Exception {
        classUnderTest.doInBackground();
        verify(capture).read(any(Mat.class));
    }

    @Test
    @DisplayName("It should release capture on done.")
    void testDone() {
        classUnderTest.done();
        verify(capture).release();;
    }
}
