package org.opencv.face.image;

import org.opencv.core.*;
import org.opencv.highgui.*;
import org.opencv.imgproc.*;
import org.opencv.contrib.*;

import java.io.File;
import java.io.FilenameFilter;
/**
 *
 * @author schroeder
 */
public class SimpleFaceRecognation {
    
    private int [] labels;

    public SimpleFaceRecognation(String trainingDir) {
        train(trainingDir);
    }

    private void train(String trainingDir) {
        File root = new File(trainingDir);

        FilenameFilter pngFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".jpg");
            }
        };

        File[] imageFiles = root.listFiles(pngFilter);

//        MatVector images = new MatVector(imageFiles.length);
//
//        int[] labels = new int[imageFiles.length];
    }
    
    public int predict(String imgName) {
        return 0;
    }
   
}
