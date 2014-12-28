package org.javacv.face.image;

import java.io.File;
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

    @Before
    public void setUp() {
        classUnderTest = new FaceDetector();
    }

    /**
     * Test of isFace method, of class FaceDetector.
     */
    @Test
    public void testIsFace() {

        assertTrue(classUnderTest.isFace(() -> {
            return read(new File(getClass().getResource("face.jpg").getPath()));
        }));
        
//        assertFalse(classUnderTest.isFace(() -> {
//            return read(new File(getClass().getResource("back.jpg").getPath()));
//        }));
    }

}
