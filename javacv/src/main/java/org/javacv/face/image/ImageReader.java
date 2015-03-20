/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javacv.face.image;

import org.bytedeco.javacpp.opencv_core.Mat;
import static org.bytedeco.javacpp.opencv_highgui.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_highgui.imread;

/**
 *
 * @author schroeder
 */
enum ImageReader {
    Instance;
    
    Mat read(String path){
        return imread(path, CV_LOAD_IMAGE_GRAYSCALE);
    }
}
