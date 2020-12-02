package org.javacv.fusion;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.javacv.common.ImageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 *
 * @author spindizzy
 */
class FusionProcessorTest {

    private final Function<String, String> resource = f -> getClass().getResource(f).getFile();
    
    private FusionProcessor classUnderTest;
    
    private Collection<String> images;
    
    private File out;
    
    @BeforeEach
    void setUp() {
        classUnderTest = new FusionProcessor();
        images = new ArrayList<>();
        out = new File(getClass().getResource(".").getFile(), "ldr.jpg");
        if(out.exists()){
            out.delete();
        }
    }

    /**
     * Test of process method, of class FusionProcessor.
     */
    @Test
    @Tag("merge")
    @DisplayName("It should create an empty output when there are no images.")
    void create_Empty() {
        assertFalse(classUnderTest.process(images, out));
    }


    @Test
    @Tag("merge")
    @DisplayName("It should merge images into one with different exposures.")
    void process_memorial() {
        images.add(resource.apply("memorial0061.png"));
        images.add(resource.apply("memorial0064.png"));
        images.add(resource.apply("memorial0067.png"));
        
        assertTrue(classUnderTest.process(images, out));
        assertTrue(out.exists());
    }
    
    @Test
    @Tag("merge")
    @DisplayName("It should align and merge images.")
    void process_with_align() {
        //source images are not matching when put on stack, needs alignment
        images.add(resource.apply("stat_1.jpg"));
        images.add(resource.apply("stat_2.jpg"));
        images.add(resource.apply("stat_3.jpg"));
        
        assertTrue(classUnderTest.process(images, out));
        assertTrue(out.exists());
    }
    
    @Test
    @DisplayName("Test to verify that multiply a source image works.")
    void multiply() {
        Mat src = ImageUtil.readAsGray(resource.apply("stat_1.jpg"));
        Scalar s = new Scalar(255.0,0.0,255.0,0.0);
        Mat filter = new Mat(src.rows(), src.cols(), src.type(), s);
        Mat dest = src.mul(filter).a();
        assertFalse(dest.empty());
    }
    
}
