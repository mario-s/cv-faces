package org.javacv.fusion;

import org.javacv.glue.Launcher;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import static java.lang.String.format;

/**
 * power plant ;-)
 */
public class FusionLauncher implements Launcher {

  private static final String FOLDER_CONTAINS_NO_IMAGE_FILES = "The folder contains no source image files.";
  private static final String REQUIRES_FOLDER_WITH_IMAGE_FILES = "It requires at least a folder with image files: %s.";

  @Override
  public void launch(String... args) {
    if (args == null || args[0].isEmpty()) {
      throw new IllegalArgumentException(REQUIRES_FOLDER_WITH_IMAGE_FILES);
    }
    var sourceFolder = args[0];

    try {
      var collector = new ImageFilesCollector(sourceFolder);
      Collection<String> pics = collector.listFiles();
      if (pics.isEmpty()) {
        var supportedSuffixes = (Object[])collector.getSupportedSuffixes();
        throw new IllegalArgumentException(format(FOLDER_CONTAINS_NO_IMAGE_FILES, supportedSuffixes));
      }
      process(collector, pics);
    } catch (IOException exc) {
      throw new IllegalStateException(exc);
    }
  }

  private void process(ImageFilesCollector collector, Collection<String> pics) {
    var target = collector.getTarget().orElseGet(() -> "out.jpg");
    var targetFile = new File(target);
    var processor = new FusionProcessor();
    processor.process(pics, targetFile);
  }


}
