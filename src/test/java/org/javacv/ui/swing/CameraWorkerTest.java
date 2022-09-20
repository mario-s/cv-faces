package org.javacv.ui.swing;

import org.javacv.detect.Detectable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.bytedeco.javacpp.opencv_core.CV_8UC3;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
        classUnderTest = spy(new CameraWorker(s -> s.area(), m -> m.rows(),
            detector, capture));
    }

    @Test
    @DisplayName("It should read an image from capture in background")
    void testDoInBackground() throws Exception {
        doReturn(true).doReturn(false).when(classUnderTest).isRunning();
        classUnderTest.doInBackground();
        verify(capture).read(any(Mat.class));
        verify(detector, never()).markObjects(any(Mat.class));
    }

    @Test
    @DisplayName("It should read an image from capture in background and proceed if image is not empty.")
    void testDoInBackground_Image() throws Exception {
        given(capture.read(any(Mat.class))).will(a -> {
           Mat img = a.getArgument(0);
           img.put(new Mat(10, 10, CV_8UC3));
           return true;
        });
        doReturn(true).doReturn(false).when(classUnderTest).isRunning();
        classUnderTest.doInBackground();

        verify(capture).read(any(Mat.class));
        verify(detector).markObjects(any(Mat.class));
    }

    @Test
    @DisplayName("It should release capture on done.")
    void testDone() {
        classUnderTest.done();
        verify(capture).release();;
    }
}
