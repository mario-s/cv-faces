package org.javacv.detect;

import org.javacv.detect.face.dnn.DnnDetector;
import org.javacv.detect.face.haar.HaarDetector;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@link DetectorFactory}.
 */
class DetectorFactoryTest {

    @Test
    @Tag("detector")
    @DisplayName("It should create an instance of a HaarDetector")
    void create_Haar() {
        Detectable result = DetectorFactory.create("HAAR");
        assertTrue(result instanceof HaarDetector);
    }

    @Disabled("FIXME")
    @Test
    @Tag("detector")
    @DisplayName("It should create an instance of a DnnDetector")
    void create_Dnn() {
        Detectable result = DetectorFactory.create("DNN");
        assertTrue(result instanceof DnnDetector);
    }

    @Test
    @Tag("detector")
    @DisplayName("It should throw an exception if type is unknown.")
    void create_Unknow() {
        assertThrows(UnsupportedOperationException.class, () -> DetectorFactory.create("foo"));
    }
}