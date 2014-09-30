package org.opencv.face.video.javafx;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import org.opencv.core.Mat;
import org.opencv.face.video.MatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author spindizzy
 */
public class VideoView extends ImageView {

    private static final Logger LOG = LoggerFactory.getLogger(VideoView.class);

    public VideoView() {
        setPreserveRatio(true);
    }

    public void updateImage(Mat matrix) {
        try{
            Image img = toJavaFXImage(MatUtil.Instance.readPixel(matrix));
            setImage(img);
        }catch(IOException e){
            LOG.warn(e.getMessage(), e);
        }
    }

    private Image toJavaFXImage(byte[] raw) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(raw);
        BufferedImage image = ImageIO.read(bis);
        return SwingFXUtils.toFXImage(image, null);
    }
}
