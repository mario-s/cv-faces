package org.javacv.ui.opencv;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JFrame;

import org.bytedeco.javacv.CanvasFrame;
import org.javacv.detect.DetectorFactory;
import org.javacv.detect.DetectorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.javacv.glue.Launcher;

/**
 * A demo which uses the provided {@link CanvasFrame}.
 *
 * @author spindizzy
 */
public class CanvasLauncher implements Launcher {

    private static final Logger LOG = LoggerFactory.getLogger(CanvasLauncher.class);
    private static final String TITLE = "Video Canvas";
    public static final int MIN = 300;

    private CanvasFrame canvas;

    private final ExecutorService executorService;

    private DetectorService detectorService;

    public CanvasLauncher() {
        this(Executors.newFixedThreadPool(3));
    }

    CanvasLauncher(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void launch(String ... args) {
        LOG.info("arguments: {}", (Object[])args);

        //Create canvas frame for displaying video.
        canvas = createFrame(TITLE);

        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setCanvasSize(MIN, MIN);

        canvas.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                detectorService.stop();
                executorService.shutdown();
            }

        });

        var det = DetectorFactory.create(detectorType.apply(args));
        detectorService = new DetectorService(new CanvasProxy(canvas), det);
        executorService.execute(detectorService);
    }

    CanvasFrame createFrame(String title) {
        return new CanvasFrame(title);
    }
}
