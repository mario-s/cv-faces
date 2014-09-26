package org.opencv.face;

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

    private final FaceDetector faceDetector;

    private final VideoPanel videoPanel;

    private SwingWorker worker;

    public VideoWindow() {
        super("Face Detection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        faceDetector = new FaceDetector();
        videoPanel = new VideoPanel();
        worker = createWorker();
        getContentPane().add(videoPanel, BorderLayout.CENTER);
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
        return new SwingWorker<Void, Mat>() {

            @Override
            protected Void doInBackground() throws Exception {
                final VideoCapture capture = new VideoCapture(0);
                Thread.sleep(1000);

                while (!isCancelled()) {
                    Mat webcamImage = new Mat();
                    capture.read(webcamImage);
                    if (!webcamImage.empty()) {
                        publish(webcamImage);
                    } else {
                        LOG.warn(" --(!) No captured frame -- Break!");
                        break;
                    }
                }

                return null;
            }

            @Override
            protected void process(List<Mat> chunks) {
                Mat img = chunks.get(0);
                setSize(img.width() + 40, img.height() + 60);
                //-- 3. Apply the classifier to the captured image  
                faceDetector.markFaces(img);
                //-- 4. Display the image  
                videoPanel.updateImage(img);
            }

        };
    }

}
