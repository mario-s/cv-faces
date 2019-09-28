package org.javacv.common;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.opencv_core.Mat;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 *
 * @author spindizzy
 */
public final class ImageConverter {

    private ImageConverter() {
    }

    public static BufferedImage toBufferedImage(Mat matrix) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (matrix.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        byte[] bytes = readPixel(matrix);
        BufferedImage image = new BufferedImage(matrix.cols(), matrix.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(bytes, 0, targetPixels, 0, bytes.length);
        return image;
    }

    private static byte[] readPixel(Mat matrix) {
        BytePointer pointer = matrix.data();
        return pointer.asBuffer().array();
    }

}
