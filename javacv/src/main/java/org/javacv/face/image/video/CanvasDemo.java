package org.javacv.face.image.video;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.javacv.face.image.FaceDetector;
import org.javacv.face.image.FaceRecognition;
import org.javacv.face.image.GenderTrainer;
import org.javacv.face.image.RecognizerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author spindizzy
 */
public class CanvasDemo {

    private static final Logger LOG = LoggerFactory.getLogger(CanvasDemo.class);

    private final FrameGrabber grabber;
    
    private final FaceDetector detector;
    
    private final FaceRecognition recognition;
    
    private final CanvasFrame canvas;

    private boolean run = true;
    
    public CanvasDemo() {
        grabber = new OpenCVFrameGrabber(0);
        
        recognition = new FaceRecognition(RecognizerType.Fisher);
        String trainingPath = getClass().getResource("../train").getPath();
        recognition.train(new GenderTrainer(trainingPath));
        
        detector = new FaceDetector();
        
        //Create canvas frame for displaying video.
        canvas = new CanvasFrame("VideoCanvas");

        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        canvas.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                run = false;
                try {
                    grabber.release();
                } catch (FrameGrabber.Exception ex) {
                    LOG.warn(ex.getMessage(), ex);
                }
            }

        });
    }

    private void run() {
        boolean sizeAdjusted = false;

        try {
            //Start grabber to capture video
            grabber.start();

            while (run) {

                if (!sizeAdjusted) {
                    //Set canvas size as per dimentions of video frame.
                    canvas.setCanvasSize(grabber.getImageWidth(), grabber.getImageHeight());
                }

                //insert grabed video frame to IplImage img
                IplImage img = grabber.grab();

                if (img != null) {
                    sizeAdjusted = true;
                    int gender = recognition.predict(img);
                    System.out.println(gender);
                    //Show video frame in canvas
                    detector.markFaces(img);
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
