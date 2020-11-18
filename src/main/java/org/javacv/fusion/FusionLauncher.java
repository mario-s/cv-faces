package org.javacv.fusion;

import org.javacv.glue.Launcher;

/**
 * power plant ;-)
 */
public class FusionLauncher implements Launcher {

  @Override
  public void launch(String... args) {
    if (args == null || args[0].isEmpty()) {
      throw new IllegalArgumentException("Requires at least a text file with source images");
    }
    var source = args[0];
    var target = (args.length > 1) ? args[1] : "out";
    //TODO read from file and pass to processor
  }
}
