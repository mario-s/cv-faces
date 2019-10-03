package org.javacv.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author spindizzy
 */
public class VideoWindow extends JFrame {

    private final VideoPanel videoPanel;

    private SwingWorker worker;
    
    public static void launch() {
        SwingUtilities.invokeLater(() -> {
             new VideoWindow();
         });
    }

    public VideoWindow() {
        super("Face Detection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        videoPanel = new VideoPanel();
        getContentPane().add(videoPanel, BorderLayout.CENTER);
        worker = new CameraWorker(this, videoPanel);
        
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
