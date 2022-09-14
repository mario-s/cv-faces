package org.javacv;

import org.javacv.fusion.FusionLauncher;
import org.javacv.glue.Launcher;
import org.javacv.ui.opencv.CanvasLauncher;
import org.javacv.ui.swing.VideoWindowLauncher;

public interface LauncherFactory {
  String SWING = "s";
  String OPEN_CV = "o";
  String MERGER = "m";

  static Launcher create(String type) {
    var t = (type != null) ? type.toLowerCase() : SWING;
    return switch(t) {
        case MERGER -> new FusionLauncher();
        case OPEN_CV -> new CanvasLauncher();
        default -> new VideoWindowLauncher();
    };
  }
}
