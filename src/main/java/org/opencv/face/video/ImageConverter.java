package org.opencv.face.video;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;

/**
 *
 * @author spindizzy
 */
public final class ImageConverter {
    
    private ImageConverter(){
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
    
    public static Image toJavaFXImage(Mat matrix) {
        MatOfByte mem = new MatOfByte();
        Highgui.imencode(".png", matrix, mem);
        return new Image(new ByteArrayInputStream(mem.toArray()));
    }
    
    private static byte[] readPixel(Mat matrix) {
        byte[] bytes = new byte[matrix.channels() * matrix.cols() * matrix.rows()];
        matrix.get(0, 0, bytes); // get all the pixels
        return bytes;
    }
    
}
