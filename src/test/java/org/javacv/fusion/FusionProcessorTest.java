package org.javacv.fusion;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.javacv.common.ImageUtility;
import org.junit.jupiter.api.BeforeEach;
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
    void create_Empty() {
        assertFalse(classUnderTest.process(images, out));
    }
    
     /**
     * Test of process method, of class FusionProcessor.
     */
    @Test
    void process_memorial() {
        images.add(resource.apply("memorial0061.png"));
        images.add(resource.apply("memorial0064.png"));
        images.add(resource.apply("memorial0067.png"));
        
        assertTrue(classUnderTest.process(images, out));
        assertTrue(out.exists());
    }
    
    @Test
    void process_merge() {
        images.add(resource.apply("Picture_201508010708_0.jpg"));
        images.add(resource.apply("Picture_201508010708_1.jpg"));
        images.add(resource.apply("Picture_201508010708_2.jpg"));
        
        assertTrue(classUnderTest.process(images, out));
        assertTrue(out.exists());
    }
    
    @Test
    void process_with_align() {
        //source images are not matching when put on stack, needs alignment
        images.add(resource.apply("stat_1.jpg"));
        images.add(resource.apply("stat_2.jpg"));
        images.add(resource.apply("stat_3.jpg"));
        
        assertTrue(classUnderTest.process(images, out));
        assertTrue(out.exists());
    }
    
    @Test
    void multiply() {
        Mat src = ImageUtility.Instance.readAsGray(resource.apply("Picture_201508010708_0.jpg"));
        Scalar s = new Scalar(255.0,0.0,255.0,0.0);
        Mat filter = new Mat(src.rows(), src.cols(), src.type(), s);
        Mat dest = src.mul(filter).a();
        assertFalse(dest.empty());
    }
    
}
