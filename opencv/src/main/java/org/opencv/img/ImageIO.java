package org.opencv.img;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.opencv.core.Mat;
import static org.opencv.imgcodecs.Imgcodecs.*;

/**
 *
 * Reading and writing of images
 */
public final class ImageIO {
    
    private ImageIO() {}
    
    public static List<Mat> loadImages(Collection<String> pics) {
        List<Mat> imgs = new ArrayList<>();
        
        pics.forEach(e -> {
            File f = new File(e);
            Mat read = imread(f.getPath());
            imgs.add(read);
        });
        return imgs;
    }
    
    public static void write(Mat img, File out) {
        imwrite(out.getPath(), img);
    }
}
