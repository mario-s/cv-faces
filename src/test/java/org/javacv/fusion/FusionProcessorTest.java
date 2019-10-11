package org.javacv.fusion;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
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
public class FusionProcessorTest {
    
    private FusionProcessor classUnderTest;
    
    private Collection<String> images;
    
    private File out;
    
    @BeforeEach
    public void setUp() {
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
    public void create_Empty() {
        assertFalse(classUnderTest.process(images, out));
    }
    
     /**
     * Test of process method, of class FusionProcessor.
     */
    @Test
    public void process_memorial() {
        images.add(getClass().getResource("memorial0061.png").getFile());
        images.add(getClass().getResource("memorial0064.png").getFile());
        images.add(getClass().getResource("memorial0067.png").getFile());
        
        assertTrue(classUnderTest.process(images, out));
        assertTrue(out.exists());
    }
    
    @Test
    public void process_merge() {
        images.add(getClass().getResource("Picture_201508010708_0.jpg").getFile());
        images.add(getClass().getResource("Picture_201508010708_1.jpg").getFile());
        images.add(getClass().getResource("Picture_201508010708_2.jpg").getFile());
        
        assertTrue(classUnderTest.process(images, out));
        assertTrue(out.exists());
    }
    
    @Test
    public void process_with_align() {
        //source images are not matching when put on stack, needs alignment
        images.add(getClass().getResource("stat_1.jpg").getFile());
        images.add(getClass().getResource("stat_2.jpg").getFile());
        images.add(getClass().getResource("stat_3.jpg").getFile());
        
        assertTrue(classUnderTest.process(images, out));
        assertTrue(out.exists());
    }
    
    @Test
    public void multiply() {
        Mat src = ImageUtility.Instance.readAsGray(getClass().getResource("Picture_201508010708_0.jpg").getFile());
        Scalar s = new Scalar(255.0,0.0,255.0,0.0);
        Mat filter = new Mat(src.rows(), src.cols(), src.type(), s);
        Mat dest = src.mul(filter).a();
        assertFalse(dest.empty());
    }
    
}
