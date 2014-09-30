package org.opencv.face.image;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opencv.core.Core;

/**
 *
 * @author spindizzy
 */
public class ImageFaceDetectorTest {

    private ImageFaceDetector classUnderTest;

    private File targetFile;
    
    @BeforeClass
    public static void init() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    @Before
    public void setUp() {
        classUnderTest = new ImageFaceDetector();

        targetFile = new File(getClass().getResource(".").getFile(), "out.png");
        if (targetFile.exists()) {
            targetFile.delete();
        }
    }

    private File createFile(String name) {
        URL resource = getClass().getResource("../" + name);
        return new File(resource.getFile());
    }

    @Test
    public void testContainsFace() {
        assertFalse(classUnderTest.containsFace(null));
        assertFalse(classUnderTest.containsFace(new File(getClass().getName())));

        assertFalse("expected no human face for horses", classUnderTest.containsFace(createFile("horses.jpg")));
        assertFalse("expected no human face for koala", classUnderTest.containsFace(createFile("koala.jpg")));
        assertFalse("expected no human face for back", classUnderTest.containsFace(createFile("back.jpg")));

        assertTrue("expected human face", classUnderTest.containsFace(createFile("face.jpg")));
    }

    @Test
    public void testMarkKoalaFace() {
        File sourceFile = createFile("koala.jpg");
        classUnderTest.saveMarkedFaces(sourceFile, targetFile);
        assertFalse(targetFile.exists());
    }

    @Test
    public void testMarkHumanFace() {
        File sourceFile = createFile("face.jpg");
        classUnderTest.saveMarkedFaces(sourceFile, targetFile);
        assertTrue(targetFile.exists());
    }
}
