package org.opencv.face;

import org.opencv.face.swing.VideoWindow;
import javax.swing.SwingUtilities;
import org.opencv.core.Core;

/**
 *
 * @author spindizzy
 */
public class MainCli {
    
    public static void main(String [] args){
        
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        
//        FileFaceDetector detector = new FileFaceDetector();
//        detector.containsFace(null);
        
         SwingUtilities.invokeLater(() -> {
             new VideoWindow();
         });
               
    }
}
