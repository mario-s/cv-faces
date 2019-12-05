package org.javacv.detect.face.dnn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;
import java.util.function.Function;

import static org.javacv.common.ImageSupplier.read;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@link DnnDetector}.
 */
class DnnDetectorTest {

    private final Function<String, File> resource = f -> new File(getClass().getResource("../" + f).getPath());

    private DnnDetector classUnderTest;

    @BeforeEach
    void setup() {
        classUnderTest = new DnnDetector();
    }

    @ParameterizedTest(name = "{index} It should return 0 for image {0}.")
    @CsvSource(value = {"back.jpg", "tree.jpg"})
    void markObjects_Zero(String image) {
        var res = read(resource.apply(image));
        assertEquals(0, classUnderTest.markObjects(res));
    }
}