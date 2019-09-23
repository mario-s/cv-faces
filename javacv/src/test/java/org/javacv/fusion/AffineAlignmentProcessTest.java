package org.javacv.fusion;

import java.util.Arrays;
import java.util.List;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.javacv.common.ImageUtility;

import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AffineAlignmentProcessTest {

    private AffineAlignmentProcess classUnderTest;


    @Before
    public void setUp() {
        classUnderTest = new AffineAlignmentProcess();
    }

    private List<Mat> loadImage() {
        List<String> names = Arrays.asList(
                getClass().getResource("Picture_201508010708_0.jpg").getFile(),
                getClass().getResource("Picture_201508010708_1.jpg").getFile(),
                getClass().getResource("Picture_201508010708_2.jpg").getFile());
        return ImageUtility.Instance.loadImages(names);
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
