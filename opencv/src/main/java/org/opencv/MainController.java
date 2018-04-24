package org.opencv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.opencv.face.video.swing.VideoWindow;
import org.opencv.img.MertensProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class MainController {
    
    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);
    
    private Window getOwner(ActionEvent event) {
        Node node = (Node) event.getSource();
        return node.getScene().getWindow();
    }
    
    private List<String> readPaths(File src) throws IOException {
        List<String> paths = new ArrayList<>();
        InputStream in = new FileInputStream(src);
        new BufferedReader(new InputStreamReader(in)).lines().forEach(l -> paths.add(l));
        LOG.debug("paths: {}", paths);
        return paths;
    }
    
    @FXML
    protected void hdr(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File srcFile = fileChooser.showOpenDialog(getOwner(event));
        LOG.debug("got source: {}", srcFile);
        
        if(srcFile != null){
            MertensProcessor processor = new MertensProcessor();
            File out = new File(srcFile.getParent(), "result.jpg");
            try {
                processor.create(readPaths(srcFile), out);
            } catch (IOException ex) {
                LOG.warn(ex.getMessage(), ex);
            }
        }
    }
    
    @FXML
    protected void video(ActionEvent event) {
        VideoWindow.launch();
    }
    
}
