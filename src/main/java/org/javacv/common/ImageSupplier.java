package org.javacv.common;

import java.io.File;
import java.util.function.Supplier;

import org.bytedeco.javacpp.opencv_core.Mat;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_COLOR;

/**
 * Supplier for an image with a helper method to read an image.
 *
 * @author spindizzy
 */
@FunctionalInterface
public interface ImageSupplier extends Supplier<Mat> {

    static Mat read(File file) {
        return imread(file.getAbsolutePath(), CV_LOAD_IMAGE_COLOR);
    }
}
