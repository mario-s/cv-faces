package org.opencv.img;

import java.util.Arrays;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.opencv.core.Core;
import org.opencv.core.Mat;

public class AlignProcessTest {

    private AlignProcess classUnderTest;

    @BeforeClass
    public static void init() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    @Before
    public void setUp() {
        classUnderTest = new AlignProcess();
    }

    private List<Mat> loadImage() {
        List<String> names = Arrays.asList(
                getClass().getResource("Picture_201508010708_0.jpg").getFile(),
                getClass().getResource("Picture_201508010708_1.jpg").getFile(),
                getClass().getResource("Picture_201508010708_2.jpg").getFile());
        return ImageIO.loadImages(names);
    }

    /**
     * Test of align method, of class AlignProcess.
     */
    @Test
    public void align_SizeAsLoaded() {
        final List<Mat> loaded = loadImage();
        List<Mat> result = classUnderTest.align(loaded);
        assertThat(result.size(), equalTo(loaded.size()));
    }

}
