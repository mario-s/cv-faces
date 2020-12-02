package org.javacv.detect.face.dnn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;
import java.util.function.Function;

import static org.javacv.common.ImageSupplier.read;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@link DnnDetector}.
 */
@Disabled
class DnnDetectorTest {

    private final Function<String, File> resource = f -> new File(getClass().getResource("../" + f).getPath());

    private DnnDetector classUnderTest;

    @BeforeEach
    void setup() {
        classUnderTest = new DnnDetector();
    }

    @ParameterizedTest(name = "{index} It should return {0} for image {1}.")
    @CsvSource(value = {"0, back.jpg", "0, tree.jpg", "0, koala.jpg", "1, face.jpg"})
    void markObjects(int expected, String image) {
        var res = read(resource.apply(image));
        assertEquals(expected, classUnderTest.markObjects(res));
    }

}