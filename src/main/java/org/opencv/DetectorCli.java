package org.opencv;

import org.opencv.core.Core;

/**
 *
 * @author spindizzy
 */
public class DetectorCli {
    
    public static void main(String [] args){
        
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
        FaceDetector detector = new FaceDetector();
        detector.containsFace(null);
               
    }
}
