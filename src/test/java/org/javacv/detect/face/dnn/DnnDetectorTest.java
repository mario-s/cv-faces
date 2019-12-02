package org.javacv.detect.face.dnn;

import org.bytedeco.javacv.Frame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@link DnnDetector}.
 */
class DnnDetectorTest {

    private DnnDetector classUnderTest;

    @BeforeEach
    void setup() {
        classUnderTest = new DnnDetector();
    }

    @Test
    @Tag("detection")
    @DisplayName("It should return 0 for an empty frame")
    void markObjects() {
        long result = classUnderTest.markObjects(new Frame());
        assertEquals(0, result);
    }
}