package org.javacv.face.detection;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.javacv.face.recognition.Predictable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

/**
 * Unit test for {@link DetectorService}.
 */
@ExtendWith(MockitoExtension.class)
class DetectorServiceTest {

    @Mock
    private CanvasFrame canvas;

    @Mock
    private Detectable detectable;

    @Mock
    private FrameGrabber grabber;

    @InjectMocks
    private DetectorService classUnderTest;

    private CountDownLatch latch;

    private Runnable testProxy;

    private ExecutorService executorService;

    @BeforeEach
    void setup() {
        classUnderTest.setDelay(0);
        latch = new CountDownLatch(1);
        testProxy = () -> {
                classUnderTest.run();
                latch.countDown();
            };

        executorService = Executors.newSingleThreadExecutor();
    }

    @AfterEach
    void shutdown() {
        executorService.shutdown();
    }

    @Test
    @DisplayName("It should show the image in the canvas.")
    void run() throws FrameGrabber.Exception, InterruptedException {
        given(grabber.grab()).willReturn(new Frame());

        executorService.submit(classUnderTest);
        latch.await(50, TimeUnit.MILLISECONDS);

        verify(canvas, atLeastOnce()).showImage(any(Frame.class));
    }
}