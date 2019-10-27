package org.javacv.face.detection;

import java.io.File;
import java.util.function.Function;

import static org.javacv.common.ImageProvideable.read;
import static org.junit.jupiter.api.Assertions.*;

import org.javacv.common.ImageProvideable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author spindizzy
 */
class DetectorTest {

    private final Function<String, File> resource = f -> new File(getClass().getResource(f).getPath());

    private Detector classUnderTest;
    
    private File targetFile;
    
    private File targetFolder;

    @BeforeEach
    void setUp() {
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
    void oneFace_ExpectTrue() {
        assertTrue(classUnderTest.hasFace(() -> read(resource.apply("face.jpg"))));
    }

    /**
     * Test of isFace method, of class FaceDetector.
     */
    @Test
    void twentyEightFaces_ExpectTrue() {
        ImageProvideable provider = () -> read(resource.apply("squad.jpg"));

        assertEquals(28, classUnderTest.countFaces(provider));
    }

    @Test
    void noFace_ExpectFalse() {
        assertFalse(classUnderTest.hasFace(() -> read(resource.apply("back.jpg"))));
    }
    
    @Test
    void tree_ExpectFalse() {
        assertFalse(classUnderTest.hasFace(() -> read(resource.apply("tree.jpg"))));
    }

    @Test
    void testSaveMarkedFaces() {

        assertTrue(classUnderTest.saveMarkedFaces(() -> read(resource.apply("squad.jpg")), targetFile));
        assertTrue(targetFile.exists());
    }
    
    @Test
    void testExtractFaces() {

        assertTrue(classUnderTest.extractFaces(() -> read(resource.apply("squad.jpg")), targetFolder));
        assertTrue(targetFolder.exists());
    }
    
    @Test
    void testNoSaveMarkedFaces() {

        assertFalse(classUnderTest.saveMarkedFaces(() -> read(resource.apply("tree.jpg")), targetFile));
        assertFalse(targetFile.exists());
    }
}
