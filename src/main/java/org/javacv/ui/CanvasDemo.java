package org.javacv.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JFrame;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.javacv.detect.DetectorFactory;
import org.javacv.detect.DetectorFactory.DetectorType;
import org.javacv.detect.DetectorService;

import org.javacv.glue.ImageShowable;

/**
 * A demo which uses the provided {@link CanvasFrame}.
 *
 * @author spindizzy
 */
public class CanvasDemo {

    private final CanvasFrame canvas;
    
    private final ExecutorService executorService;
    
    private final DetectorService detectorService;

    public CanvasDemo() {
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

        var detector = DetectorFactory.create(DetectorType.HAAR);
        detectorService = new DetectorService(new CanvasProxy(canvas), detector);
        executorService = Executors.newFixedThreadPool(3);
    }

    public void run() {
        executorService.execute(detectorService);
    }

    public static void launch() {
        new CanvasDemo().run();
    }

    private static class CanvasProxy implements ImageShowable {
        private final CanvasFrame canvas;

        CanvasProxy(CanvasFrame canvas) {
            this.canvas = canvas;
        }

        @Override
        public void setSize(int width, int height) {
            canvas.setCanvasSize(width, height);
        }

        @Override
        public void showImage(Frame image) {
            canvas.showImage(image);
        }
    }
}
