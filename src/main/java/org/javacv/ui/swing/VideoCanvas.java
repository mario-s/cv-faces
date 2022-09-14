package org.javacv.ui.swing;

import org.bytedeco.javacpp.opencv_core.Mat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;

/**
 * A canvas which extends {@link Canvas} and paints the given {@link Mat}.
 * @author spindizzy
 */
public class VideoCanvas extends Canvas implements Consumer<Mat> {

    private static final Color FOREGROUND = Color.white;
    private static final Color BACKGROUND = Color.black;

    private BufferedImage image;

    public VideoCanvas() {
        setForeground(FOREGROUND);
        setBackground(BACKGROUND);
    }

    /**
     * Converts/writes a Mat into a BufferedImage.
     *
     * @param matrix Mat of type CV_8UC3 or CV_8UC1
     */
    @Override
    public void accept(Mat matrix) {
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
        offscreenGraphics.setColor(BACKGROUND);
        offscreenGraphics.fillRect(0, 0, box.width, box.height);
        offscreenGraphics.translate(-box.x, -box.y);
        // do normal redraw
        paint(offscreenGraphics);
        // transfer offscreenImage to window
        g.drawImage(offscreenImage, box.x, box.y, this);
    }

}
