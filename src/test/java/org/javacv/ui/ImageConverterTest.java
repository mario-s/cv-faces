package org.javacv.ui;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author spindizzy
 */
class ImageConverterTest {

    private Mat matrix;

    @BeforeEach
    void setUp() {
        matrix = imread(getClass().getResource("horses.jpg").getFile());
    }

    /**
     * Test of toBufferedImage method, of class ImageConverter.
     */
    @Test
    @Tag("convert")
    @DisplayName("It should convert a Mat to a BufferedImage.")
    void testToBufferedImage() {
        BufferedImage image = ImageConverter.toBufferedImage(matrix);
        Raster raster = image.getRaster();
        //take a sample, it should not be 0
        int sample = raster.getSample(1,1,1);
        assertTrue(sample > 0);
    }
}
