package org.javacv.ui;

import org.junit.Before;
import org.junit.Test;
import org.bytedeco.javacpp.opencv_core.Mat;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;

/**
 *
 * @author spindizzy
 */
public class ImageConverterTest {

    private Mat matrix;

    @Before
    public void setUp() {
        File file = new File(getClass().getResource("horses.jpg").getFile());
        String path = file.getPath();
        matrix = imread(path);
    }

    /**
     * Test of toBufferedImage method, of class ImageConverter.
     */
    @Test
    public void testToBufferedImage() throws IOException {
        BufferedImage image = ImageConverter.toBufferedImage(matrix);
        Raster raster = image.getRaster();
        //take a sample, it should not be 0
        int sample = raster.getSample(1,1,1);
        assertTrue(sample > 0);
    }

}
