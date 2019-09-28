package org.javacv.ui;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.javacv.common.ImageConverter;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author spindizzy
 */
public class VideoPanel extends Canvas {
    
    private BufferedImage image;

    public VideoPanel() {
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
    public void paint(Graphics g) {
        if (image == null) {
            g.drawString("Camera ...", 10, 10);
            return;
        }
        g.drawImage(this.image, 10, 10, this.image.getWidth(), this.image.getHeight(), null);
    }

    @Override
    public void update(Graphics g) {
        Rectangle box = g.getClipBounds();
        // create the offscreenImage buffer and associated Graphics
        Image offscreenImage = createImage(box.width, box.height);
        Graphics offscreenGraphics = offscreenImage.getGraphics();
        offscreenGraphics.setColor(getBackground());
        offscreenGraphics.fillRect(0, 0, box.width, box.height);
        offscreenGraphics.setColor(getForeground());
        offscreenGraphics.translate(-box.x, -box.y);
        // do normal redraw
        paint(offscreenGraphics);
        // transfer offscreenImage to window
        g.drawImage(offscreenImage, box.x, box.y, this);
    }

}
