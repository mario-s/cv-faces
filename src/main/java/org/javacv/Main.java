package org.javacv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.IVersionProvider;
import picocli.CommandLine.Option;

/**
 * Main Entry point for the demo application.
 */
@Command(name = "video", mixinStandardHelpOptions = true,
        description = "Java application which uses OpenCV",
        versionProvider = Main.class)
public class Main implements Runnable, IVersionProvider {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    @Option(names = "-u",
        description = "User interface. Possible Values:\n" +
        "[s] Swing face detector (default),\n" +
        "[o] canvas face detector from OpenCV,\n" +
        "[m] merge pictures to produce HDR")
    private String ui;

    @Option(names = "-d", description = "The face detector type. Possible Values:\n" +
    "[dnn] Deep Neural Network (default),\n" +
    "[haar] Haar Classifier (outdated)")
    private String detector;

    public Main() {
        this.ui = LauncherFactory.SWING;
        this.detector = "DNN";
    }

    public static void main(String[] args) {
        Main main = new Main();
        CommandLine cmd = new CommandLine(main);
        int exitCode = cmd.execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        var launcher = LauncherFactory.create(ui);
        launcher.launch(detector);
        join();
    }

    private void join() {
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            LOG.warn(e.getMessage(), e);
        }
    }

    @Override
    public String[] getVersion() throws Exception {
        return new String[] {"202011"};
    }
}
