package org.javacv.ui.swing;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.bytedeco.javacpp.opencv_core.CV_8UC3;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VideoCanvasTest {

    private static final int ROWS = 320;
    private static final int COLS = 240;
    @Mock
    private Graphics graphics;
    private VideoCanvas classUnderTest;
    private Mat image;

    @BeforeEach
    void setUp() {
        classUnderTest = spy(new VideoCanvas());
        image = new Mat(ROWS, COLS, CV_8UC3);
    }

    @Test
    @DisplayName("It should accept a matrix an store it as a buffered image.")
    void accept() {
        classUnderTest.accept(image);
        assertNotNull(classUnderTest.getImage());
    }

    @Test
    @DisplayName("It should just write a string when image is null.")
    void paint_image_null() {
        classUnderTest.paint(graphics);
        verify(graphics).drawString(VideoCanvas.STARTING, VideoCanvas.PAD, VideoCanvas.PAD);
    }

    @Test
    @DisplayName("It should draw the image when it is present.")
    void paint_image() {
        classUnderTest.accept(image);
        classUnderTest.paint(graphics);
        verify(graphics).drawImage(classUnderTest.getImage(), VideoCanvas.PAD, VideoCanvas.PAD, COLS, ROWS, null);
    }

    @Test
    @DisplayName("It should draw on an off screen image to enhance performance.")
    void update() {
        classUnderTest.accept(image);
        BufferedImage bufferedImg = classUnderTest.getImage();
        var rectangle = new Rectangle(COLS, ROWS);

        given(graphics.getClipBounds()).willReturn(rectangle);
        doReturn(bufferedImg).when(classUnderTest).createImage(COLS, ROWS);

        classUnderTest.update(graphics);
        verify(graphics).drawImage(bufferedImg, 0, 0, classUnderTest);
    }
}