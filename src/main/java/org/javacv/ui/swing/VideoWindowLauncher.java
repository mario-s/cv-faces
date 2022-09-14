package org.javacv.ui.swing;

import org.bytedeco.javacpp.opencv_core.Size;
import org.javacv.glue.Launcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Consumer;

/**
 *
 * @author spindizzy
 */
public class VideoWindowLauncher extends JFrame implements Consumer<Size>, Launcher {

    private static final Logger LOG = LoggerFactory.getLogger(VideoWindowLauncher.class);

    private VideoCanvas videoCanvas;

    private SwingWorker worker;

    @Override
    public void launch(String ... args) {
        LOG.info("arguments are not supported yet");
        SwingUtilities.invokeLater(() -> {
            run();
         });
    }

    public VideoWindowLauncher() {
        super("Face Detection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void run() {
        videoCanvas = new VideoCanvas();
        getContentPane().add(videoCanvas, BorderLayout.CENTER);
        pack();
        setSize(400, 400);

        worker = new CameraWorker(this, videoCanvas);
        setVisible(true);

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                worker.cancel(true);
                dispose();
            }

        });

        worker.execute();
    }

    @Override
    public void accept(Size size) {
        setSize(size.width() + 40, size.height() + 60);
    }

}
