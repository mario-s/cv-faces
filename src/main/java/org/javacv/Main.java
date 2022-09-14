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

    @Option(names = "-s",
    description = "Source path of images with different exposures which should be merged into one HDR")
    private String sourcePath;

    @Option(names = "-t",
    description = "Target file of the image which is the result of the merge process")
    private String targetFile;

    @Option(names = "-u",
        description = "User interface for face detector. Possible Values:\n" +
        "[c] canvas from OpenCV (default)\n"+
        "[s] Swing")
    private String ui;

    @Option(names = "-d", description = "The face detector type. Possible Values:\n" +
    "[dnn] Deep Neural Network (default),\n" +
    "[haar] Haar Classifier (outdated)")
    private String detector;

    public static void main(String[] args) {
        Main main = new Main();
        CommandLine cmd = new CommandLine(main);
        int exitCode = cmd.execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        LOG.debug("argument: {}", ui);
        if (ui != null) {
            LOG.debug("starting ui");
            var launcher = LauncherFactory.create(ui);
            launcher.launch(detector);
            join();
        } else {
            var launcher = LauncherFactory.create(LauncherFactory.MERGER);
            LOG.debug("executing merge process for {}", sourcePath);
            launcher.launch(sourcePath, targetFile);
        }
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
