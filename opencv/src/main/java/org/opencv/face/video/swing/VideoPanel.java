package org.opencv.face.video.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import org.opencv.core.Mat;
import org.opencv.face.video.ImageConverter;

/**
 *
 * @author spindizzy
 */
public class VideoPanel extends JPanel {

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
        image = ImageConverter.toBufferedImage(matrix);
        repaint();
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
