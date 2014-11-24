package org.javacv.face.image.video;

import javax.swing.JFrame;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author spindizzy
 */
public class CanvasDemo {
    
    private static final Logger LOG = LoggerFactory.getLogger(CanvasDemo.class);
    
    private void run() {
        //Create canvas frame for displaying video.
        CanvasFrame canvas = new CanvasFrame("VideoCanvas");        
        
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FrameGrabber grabber = new OpenCVFrameGrabber(0);        
        
        try {

            //Start grabber to capture video
            grabber.start();

            while (true) {

                //inser grabed video fram to IplImage img
                IplImage img = grabber.grab();

                //Set canvas size as per dimentions of video frame.
                canvas.setCanvasSize(grabber.getImageWidth(), grabber.getImageHeight());                
                
                if (img != null) {
                    //Show video frame in canvas
                    canvas.showImage(img);                    
                }
            }
        } catch (Exception e) {            
            LOG.warn(e.getMessage(), e);
        }
    }
    
    public static void main(String[] args) {
        CanvasDemo demo = new CanvasDemo();
        demo.run();
    }
}
