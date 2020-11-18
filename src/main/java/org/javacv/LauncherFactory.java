package org.javacv;

import org.javacv.fusion.FusionLauncher;
import org.javacv.glue.Launcher;
import org.javacv.ui.CanvasDemo;
import org.javacv.ui.VideoWindow;

public interface LauncherFactory {
  String SWING = "s";
  String OPEN_CV = "o";
  String MERGER = "m";

  static Launcher create(String type) {
    var t = (type != null) ? type.toLowerCase() : SWING;
    if (OPEN_CV.equals(t)) {
      return new CanvasDemo();
    }
    if (MERGER.equals(t)) {
      return new FusionLauncher();
    }
    return new VideoWindow();
  }
}
