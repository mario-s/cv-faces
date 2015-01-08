package org.javacv.face.image;

import java.io.File;
import java.io.IOException;
import static org.javacv.face.image.ImageProvideable.read;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author spindizzy
 */
public class FaceDetectorTest {

    private FaceDetector classUnderTest;
    
    private File targetFile;
    
    private File targetFolder;

    @Before
    public void setUp() {
        classUnderTest = new FaceDetector();
        
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

    /**
     * Test of isFace method, of class FaceDetector.
     */
    @Test
    public void testIsFace() {

        assertTrue(classUnderTest.hasFace(() -> {
            return read(new File(getClass().getResource("face.jpg").getPath()));
        }));

        ImageProvideable provider = () -> {
            return read(new File(getClass().getResource("squad.jpg").getPath()));
        };

        assertTrue(classUnderTest.hasFace(provider));
        assertEquals(28, classUnderTest.countFaces(provider));

        assertFalse(classUnderTest.hasFace(() -> {
            return read(new File(getClass().getResource("tree.jpg").getPath()));
        }));
    }

    @Test
    public void testSaveMarkedFaces() {

        assertTrue(classUnderTest.saveMarkedFaces(() -> {
            return read(new File(getClass().getResource("squad.jpg").getPath()));
        }, targetFile));
        assertTrue(targetFile.exists());
    }
    
    @Test
    public void testExtractFaces() {

        assertTrue(classUnderTest.extractFaces(() -> {
            return read(new File(getClass().getResource("squad.jpg").getPath()));
        }, targetFolder));
        assertTrue(targetFolder.exists());
    }
    
    @Test
    public void testNoSaveMarkedFaces() {

        assertFalse(classUnderTest.saveMarkedFaces(() -> {
            return read(new File(getClass().getResource("tree.jpg").getPath()));
        }, targetFile));
        assertFalse(targetFile.exists());
    }

}
