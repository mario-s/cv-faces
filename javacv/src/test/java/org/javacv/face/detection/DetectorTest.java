package org.javacv.face.detection;

import java.io.File;
import static org.javacv.common.ImageProvideable.read;

import org.javacv.common.ImageProvideable;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author spindizzy
 */
public class DetectorTest {

    private Detector classUnderTest;
    
    private File targetFile;
    
    private File targetFolder;

    @Before
    public void setUp() {
        classUnderTest = new Detector();
        
        targetFile = new File(getClass().getResource(".").getFile(), "out.png");
        if (targetFile.exists()) {
            targetFile.delete();
        }
        
        targetFolder = new File(targetFile.getParent(), "extracted");
        if(targetFolder.exists()){
            targetFolder.delete();
        }
        targetFolder.mkdir();
    }
    
    @Test
    public void oneFace_ExpectTrue() {
        assertTrue(classUnderTest.hasFace(() -> read(new File(getClass().getResource("face.jpg").getPath()))));
    }

    /**
     * Test of isFace method, of class FaceDetector.
     */
    @Test
    public void twentyEightFaces_ExpectTrue() {
        ImageProvideable provider = () -> read(new File(getClass().getResource("squad.jpg").getPath()));

        assertEquals(28, classUnderTest.countFaces(provider));
    }

    @Test
    public void noFace_ExpectFalse() {
        assertFalse(classUnderTest.hasFace(() -> read(new File(getClass().getResource("back.jpg").getPath()))));
    }
    
    @Test
    public void tree_ExpectFalse() {
        assertFalse(classUnderTest.hasFace(() -> read(new File(getClass().getResource("tree.jpg").getPath()))));
    }

    @Test
    public void testSaveMarkedFaces() {

        assertTrue(classUnderTest.saveMarkedFaces(() -> read(new File(getClass().getResource("squad.jpg").getPath())), targetFile));
        assertTrue(targetFile.exists());
    }
    
    @Test
    public void testExtractFaces() {

        assertTrue(classUnderTest.extractFaces(() -> read(new File(getClass().getResource("squad.jpg").getPath())), targetFolder));
        assertTrue(targetFolder.exists());
    }
    
    @Test
    public void testNoSaveMarkedFaces() {

        assertFalse(classUnderTest.saveMarkedFaces(() -> read(new File(getClass().getResource("tree.jpg").getPath())), targetFile));
        assertFalse(targetFile.exists());
    }

}
