package org.opencv;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.opencv.core.Core;

/**
 *
 */
public class Gui extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = getParent();
        Scene scene = new Scene(root, 400, 200);

        stage.setTitle("OpenCV");
        stage.setScene(scene);
        stage.show();
    }

    Parent getParent() throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("org.opencv.bundles.bundle");
        return FXMLLoader.load(getClass().getResource("gui.fxml"), bundle);
    }

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        launch(args);
    }

}
