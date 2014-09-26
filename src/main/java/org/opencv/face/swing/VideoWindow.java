package org.opencv.face.swing;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingWorker;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author spindizzy
 */
public class VideoWindow extends JFrame {

    private static final Logger LOG = LoggerFactory.getLogger(VideoWindow.class);

    private final VideoPanel videoPanel;

    private SwingWorker worker;

    public VideoWindow() {
        super("Face Detection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        videoPanel = new VideoPanel();
        getContentPane().add(videoPanel, BorderLayout.CENTER);
        worker = createWorker();
        
        pack();
        setSize(400, 400);
        setVisible(true);

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                worker.cancel(true);
                dispose();
                System.exit(0);
            }

        });

        worker.execute();
    }

    private SwingWorker<Void, Mat> createWorker() {
        return new CamWorker(this, videoPanel);
    }

}
