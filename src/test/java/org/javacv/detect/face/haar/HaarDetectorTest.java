package org.javacv.detect.face.haar;

import java.io.File;
import java.util.function.Function;

import static org.javacv.common.ImageSupplier.read;
import static org.junit.jupiter.api.Assertions.*;

import org.javacv.common.FileUtil;
import org.javacv.common.ImageSupplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit test for {@link HaarDetector}.
 * @author spindizzy
 */
class HaarDetectorTest {
    private static final Logger LOG = LoggerFactory.getLogger(HaarDetectorTest.class);

    private final Function<String, File> resource = f -> new File(getClass().getResource("../" + f).getPath());

    private HaarDetector classUnderTest;

    private File targetFile;

    private File targetFolder;

    @BeforeEach
    void setUp() {
        classUnderTest = new HaarDetector();

        targetFile = new File(getClass().getResource(".").getFile(), "out.png");
        FileUtil.deleteIfPresent(targetFile);

        targetFolder = new File(targetFile.getParent(), "extracted");
        FileUtil.deleteIfPresent(targetFolder);

        boolean success = targetFolder.mkdir();
        LOG.debug("created target folder: {}", success);
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
