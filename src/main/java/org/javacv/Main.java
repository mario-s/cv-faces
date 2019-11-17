package org.javacv;

import org.javacv.ui.CanvasDemo;
import org.javacv.ui.VideoWindow;

import java.util.List;

import com.sampullara.cli.*;

public class Main {

    @Argument(alias = "-c", description = "use canvas frame from opencv library")
    private static Boolean canvas = false;

    public static void main(String[] args) {
        List<String> unparsed = Args.parseOrExit(Main.class, args);
        Main command = new Main();

        command.run();
    }

    private void run() {
        if (canvas) {
            CanvasDemo demo = new CanvasDemo();
            demo.run();
        } else {
            VideoWindow.launch();
        }
    }
}
