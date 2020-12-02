package org.javacv.ui;

import org.javacv.LauncherFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class LauncherFactoryTest {

  @Test
  @Tag("UI")
  @DisplayName("It should create a default launcher")
  void create_Default() {
    var res = LauncherFactory.create(null);
    assertTrue(res instanceof JFrame);
  }

  @Test
  @Tag("UI")
  @DisplayName("It should create a launcher which uses OpenCV")
  void create_OpenCv() {
    var res = LauncherFactory.create("o");
    assertFalse(res instanceof JFrame);
  }

  @Test
  @Tag("UI")
  @DisplayName("It should create a launcher which merges pictures")
  void create_Merger() {
    var res = LauncherFactory.create("m");
    assertFalse(res instanceof JFrame);
  }
}