package org.opencv.face.swing;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.SwingWorker;

/**
 *
 * @author spindizzy
 */
public class VideoWindow extends JFrame {

    private final VideoPanel videoPanel;

    private SwingWorker worker;

    public VideoWindow() {
        super("Face Detection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        videoPanel = new VideoPanel();
        getContentPane().add(videoPanel, BorderLayout.CENTER);
        worker = new CamWorker(this, videoPanel);
        
        pack();
        setSize(400, 400);
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

}
