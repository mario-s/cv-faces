package org.javacv.fusion;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.javacv.common.ImageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AffineAlignmentProcessTest {

    private AffineAlignmentProcess classUnderTest;


    @BeforeEach
    void setUp() {
        classUnderTest = new AffineAlignmentProcess();
    }

    private List<Mat> load() {
        Function<String, String> resource = f -> getClass().getResource(f).getFile();
        List<String> names = Arrays.asList(resource.apply("Picture_201508010708_0.jpg"),
                resource.apply("Picture_201508010708_1.jpg"),
                resource.apply("Picture_201508010708_2.jpg"));
        return ImageUtil.read(names);
    }

    /**
     * Test of align method, of class AlignProcess.
     */
    @Test
    void align_SizeAsLoaded() {
        final List<Mat> loaded = load();
        List<Mat> result = classUnderTest.align(loaded);
        assertEquals(loaded.size(), result.size());
    }
}
