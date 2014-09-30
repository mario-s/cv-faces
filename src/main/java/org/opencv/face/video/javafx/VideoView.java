package org.opencv.face.video.javafx;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import org.opencv.core.Mat;
import org.opencv.face.video.MatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Optional.empty;
import static java.util.Optional.of;

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
        Optional<Image> opt = toJavaFXImage(MatUtil.Instance.readPixel(matrix));
        if(opt.isPresent()){
            Image img = opt.get();
            setImage(img);
        }else{
            LOG.warn("No image present!");
        }
    }

    private Optional<Image> toJavaFXImage(byte[] raw) {
        Optional<Image> image = empty();
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(raw);
            BufferedImage read = ImageIO.read(bis);
            image = of(SwingFXUtils.toFXImage(read, null));
        } catch (IOException ex) {
        }
        return image;
    }
}
