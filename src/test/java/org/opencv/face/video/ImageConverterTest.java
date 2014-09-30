package org.opencv.face.video;

import java.awt.image.BufferedImage;
import java.io.File;
import javafx.scene.image.Image;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import static org.junit.Assert.*;

/**
 *
 * @author spindizzy
 */
public class ImageConverterTest {

    private Mat matrix;
    
    @BeforeClass
    public static void init() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    @Before
    public void setUp() {
        File file = new File(getClass().getResource("../horses.jpg").getFile());
        String path = file.getPath();
        matrix = Highgui.imread(path);
    }

    /**
     * Test of toBufferedImage method, of class ImageConverter.
     */
    @Test
    public void testToBufferedImage() {
        BufferedImage result = ImageConverter.toBufferedImage(matrix);
        assertNotNull(result);
    }

    /**
     * Test of toJavaFXImage method, of class ImageConverter.
     */
    @Test
    @Ignore
    public void testToJavaFXImage() throws Exception {
        Image result = ImageConverter.toJavaFXImage(matrix);
        assertNotNull(result);
    }

}
