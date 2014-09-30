package org.opencv.face.video;

import org.opencv.core.Mat;

/**
 *
 * @author spindizzy
 */
public enum MatUtil {
    
    Instance;
    
    public byte[] readPixel(Mat matrix) {
        byte[] bytes = new byte[matrix.channels() * matrix.cols() * matrix.rows()];
        matrix.get(0, 0, bytes); // get all the pixels
        return bytes;
    }
    
}
