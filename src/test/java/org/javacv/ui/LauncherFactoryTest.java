package org.javacv.ui;

import org.javacv.LauncherFactory;
import org.javacv.fusion.FusionLauncher;
import org.javacv.ui.opencv.CanvasLauncher;
import org.javacv.ui.swing.VideoWindowLauncher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LauncherFactoryTest {

  @Test
  @Tag("UI")
  @DisplayName("It should create a default launcher")
  void create_Default() {
    var res = LauncherFactory.create(null);
    assertTrue(res instanceof VideoWindowLauncher);
  }

  @Test
  @Tag("UI")
  @DisplayName("It should create a launcher which uses OpenCV")
  void create_OpenCv() {
    var res = LauncherFactory.create("o");
    assertTrue(res instanceof CanvasLauncher);
  }

  @Test
  @Tag("UI")
  @DisplayName("It should create a launcher which merges pictures")
  void create_Merger() {
    var res = LauncherFactory.create("m");
    assertTrue(res instanceof FusionLauncher);
  }
}