package org.javacv.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JFrame;

import org.bytedeco.javacv.CanvasFrame;
import org.javacv.face.recognition.FaceRecognition;
import org.javacv.face.detection.DetectorService;
import org.javacv.face.recognition.GenderTrainer;
import org.javacv.face.recognition.RecognizerType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author spindizzy
 */
public class CanvasDemo {

    private static final Logger LOG = LoggerFactory.getLogger(CanvasDemo.class);

    private final FaceRecognition recognition;
    
    private final CanvasFrame canvas;
    
    private final ExecutorService executorService;
    
    private final DetectorService detectorService;

    public CanvasDemo() {
        
        recognition = new FaceRecognition(RecognizerType.Fisher);

        String trainingPath = getClass().getResource("../train").getPath();
        LOG.debug("using images from {}", trainingPath);
        recognition.train(new GenderTrainer(trainingPath));
        
        //Create canvas frame for displaying video.
        canvas = new CanvasFrame("VideoCanvas");

        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setCanvasSize(200, 200);

        canvas.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                detectorService.stop();
                executorService.shutdown();
            }

        });
        
        detectorService = new DetectorService(canvas, recognition);
        executorService = Executors.newFixedThreadPool(3);
    }

    private void run() {
        executorService.execute(detectorService);
    }

    public static void main(String[] args) {
        CanvasDemo demo = new CanvasDemo();
        demo.run();
    }
}
