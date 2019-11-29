package org.javacv.face.detection.haar;

import java.io.File;
import java.util.function.Function;

import static org.javacv.common.ImageSupplier.read;
import static org.junit.jupiter.api.Assertions.*;

import org.javacv.common.ImageSupplier;
import org.javacv.face.detection.haar.Detector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 *
 * @author spindizzy
 */
class DetectorTest {

    private final Function<String, File> resource = f -> new File(getClass().getResource("../" + f).getPath());

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
        ImageSupplier provider = () -> read(resource.apply("squad.jpg"));

        assertEquals(28, classUnderTest.countFaces(provider));
    }

    @ParameterizedTest(name = "{index} It should return false for image {0}.")
    @CsvSource(value = {"back.jpg", "tree.jpg"})
    void noFace_ExpectFalse(String image) {
        assertFalse(classUnderTest.hasFace(() -> read(resource.apply(image))));
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
