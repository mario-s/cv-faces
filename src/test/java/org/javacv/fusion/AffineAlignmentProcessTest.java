package org.javacv.fusion;

import java.util.Arrays;
import java.util.List;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.javacv.common.ImageUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AffineAlignmentProcessTest {

    private AffineAlignmentProcess classUnderTest;


    @BeforeEach
    public void setUp() {
        classUnderTest = new AffineAlignmentProcess();
    }

    private List<Mat> load() {
        List<String> names = Arrays.asList(
                getClass().getResource("Picture_201508010708_0.jpg").getFile(),
                getClass().getResource("Picture_201508010708_1.jpg").getFile(),
                getClass().getResource("Picture_201508010708_2.jpg").getFile());
        return ImageUtility.Instance.read(names);
    }

    /**
     * Test of align method, of class AlignProcess.
     */
    @Test
    public void align_SizeAsLoaded() {
        final List<Mat> loaded = load();
        List<Mat> result = classUnderTest.align(loaded);
        assertEquals(loaded.size(), result.size());
    }

}
