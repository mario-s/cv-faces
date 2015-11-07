package org.opencv.hdr;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.opencv.core.Core;

/**
 *
 * @author schroeder
 */
public class HdrProcessorTest {
    
    private HdrProcessor classUnderTest;
    
    private Map<String, Double> images;
    
    private File out;
    
    @BeforeClass
    public static void init() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    
    @Before
    public void setUp() {
        classUnderTest = new HdrProcessor();
        images = new TreeMap<>();
        out = new File(getClass().getResource(".").getFile(), "ldr.jpg");
        if(out.exists()){
            out.delete();
        }
    }

    /**
     * Test of create method, of class HdrProcessor.
     */
    @Test
    @Ignore
    public void testCreate_Empty() {
        assertFalse(classUnderTest.create(images, out));
    }
    
     /**
     * Test of create method, of class HdrProcessor.
     */
    @Test
    @Ignore
    public void testCreate_Memorial() {
        images.put(getClass().getResource("memorial0061.png").getFile(), .03125);
        images.put(getClass().getResource("memorial0064.png").getFile(), .25);
        images.put(getClass().getResource("memorial0067.png").getFile(), 2.0);
        
        assertTrue(classUnderTest.create(images, out));
        assertTrue(out.exists());
    }
    
    @Test
    public void testCreate_Pic() {
        images.put(getClass().getResource("Picture_201508010708_0.jpg").getFile(), .0083);
        images.put(getClass().getResource("Picture_201508010708_1.jpg").getFile(), .000488);
        images.put(getClass().getResource("Picture_201508010708_2.jpg").getFile(), .067);
        
        assertTrue(classUnderTest.create(images, out));
        assertTrue(out.exists());
    }
    
}
