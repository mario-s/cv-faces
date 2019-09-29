package org.opencv.face.video;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import org.opencv.core.Mat;

/**
 *
 * @author spindizzy
 */
public final class ImageConverter {

    private ImageConverter() {
    }

    public static BufferedImage toBufferedImage(final Mat matrix) {
        final BufferedImage image = new BufferedImage(matrix.cols(), matrix.rows(), getType(matrix));
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        byte[] bytes = readPixel(matrix);
        System.arraycopy(bytes, 0, targetPixels, 0, bytes.length);
        return image;
    }

    private static int getType(final Mat matrix) {
        if (matrix.channels() > 1) {
            return BufferedImage.TYPE_3BYTE_BGR;
        }
        return BufferedImage.TYPE_BYTE_GRAY;
    }

    private static byte[] readPixel(final Mat matrix) {
        byte[] bytes = new byte[matrix.channels() * matrix.cols() * matrix.rows()];
        matrix.get(0, 0, bytes); // get all the pixels
        return bytes;
    }
}
