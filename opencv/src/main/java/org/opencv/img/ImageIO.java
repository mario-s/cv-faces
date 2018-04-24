package org.opencv.img;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import static org.opencv.imgcodecs.Imgcodecs.imread;

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
        Imgcodecs.imwrite(out.getPath(), img);
    }
}
