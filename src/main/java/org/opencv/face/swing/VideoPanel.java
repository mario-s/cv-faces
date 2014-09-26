package org.opencv.face.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import javax.swing.JPanel;
import org.opencv.core.Mat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author spindizzy
 */
public class VideoPanel extends JPanel {

    private static final Logger LOG = LoggerFactory.getLogger(VideoPanel.class);

    private BufferedImage image;

    public VideoPanel() {
        setOpaque(true);
        setForeground(Color.white);
        setBackground(Color.black);
    }

    /**
     * Converts/writes a Mat into a BufferedImage.
     *
     * @param matrix Mat of type CV_8UC3 or CV_8UC1
     */
    public void updateImage(Mat matrix) {
        toBufferedImage(matrix);
        repaint();
    }

    private void toBufferedImage(Mat matrix) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (matrix.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        byte[] b = new byte[matrix.channels() * matrix.cols() * matrix.rows()];
        matrix.get(0, 0, b); // get all the pixels
        image = new BufferedImage(matrix.cols(), matrix.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image == null) {
            g.drawString("Camera ...", 10, 10);
            return;
        }
        g.drawImage(this.image, 10, 10, this.image.getWidth(), this.image.getHeight(), null);
    }

}
