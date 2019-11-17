package org.javacv.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JFrame;

import org.bytedeco.javacv.CanvasFrame;
import org.javacv.face.recognition.GenderPredictor;
import org.javacv.face.detection.DetectorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A demo which uses the provided {@link CanvasFrame}.
 *
 * @author spindizzy
 */
public class CanvasDemo {

    private static final Logger LOG = LoggerFactory.getLogger(CanvasDemo.class);

    private final CanvasFrame canvas;
    
    private final ExecutorService executorService;
    
    private final DetectorService detectorService;

    public CanvasDemo() {
        String trainingPath = getClass().getResource("../train").getPath();
        LOG.debug("using images from {}", trainingPath);

        //Create canvas frame for displaying video.
        canvas = new CanvasFrame("Video Canvas");

        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setCanvasSize(200, 200);

        canvas.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                detectorService.stop();
                executorService.shutdown();
            }

        });
        
        detectorService = new DetectorService(canvas, new GenderPredictor(trainingPath));
        executorService = Executors.newFixedThreadPool(3);
    }

    public void run() {
        executorService.execute(detectorService);
    }

}
