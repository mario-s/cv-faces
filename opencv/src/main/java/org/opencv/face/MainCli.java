package org.opencv.face;

import org.opencv.core.Core;
import org.opencv.face.video.javafx.VideoApplication;

/**
 *
 * @author spindizzy
 */
public class MainCli {
    
    public static void main(String [] args){
        
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        
//        FileFaceDetector detector = new FileFaceDetector();
//        detector.containsFace(null);
        
//         VideoWindow.launch();
        
        VideoApplication.main(args);
               
    }
}
