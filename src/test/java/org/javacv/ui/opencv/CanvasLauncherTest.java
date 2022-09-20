package org.javacv.ui.opencv;

import org.bytedeco.javacv.CanvasFrame;
import org.javacv.detect.DetectorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.event.WindowListener;
import java.util.concurrent.ExecutorService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CanvasLauncherTest {

    @Mock
    private CanvasFrame canvasFrame;
    @Mock
    private ExecutorService executorService;

    private CanvasLauncher classUnderTest;

    @BeforeEach
    void setUp() {
        classUnderTest = new CanvasLauncher(executorService) {
            @Override
            CanvasFrame createFrame(String title) {
                return canvasFrame;
            }
        };
    }

    @Test
    @DisplayName("It should use the canvas frame and launch the detector.")
    void launch() {
        classUnderTest.launch((String[])null);

        verify(canvasFrame).addWindowListener(any(WindowListener.class));
        verify(executorService).execute(any(DetectorService.class));
    }
}