package org.opencv.face.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
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
        MatOfByte matOfByte = new MatOfByte();
        
        Highgui.imencode(".jpg", matrix, matOfByte);
        
        try {
            byte[] byteArray = matOfByte.toArray();
            InputStream in = new ByteArrayInputStream(byteArray);
            image = ImageIO.read(in);
            
        } catch (IOException e) {
            LOG.warn(e.getMessage());
        }finally {
            repaint();
            
        }
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
