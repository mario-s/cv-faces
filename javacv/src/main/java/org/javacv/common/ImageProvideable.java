package org.javacv.common;

import java.io.File;
import org.bytedeco.javacpp.opencv_core.Mat;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_COLOR;

/**
 *
 * @author spindizzy
 */
@FunctionalInterface
public interface ImageProvideable {
    
    Mat provide();
    
    static Mat read(File file) {
        return imread(file.getAbsolutePath(), CV_LOAD_IMAGE_COLOR);
    }
}
