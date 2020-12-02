package org.javacv.fusion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class FusionLauncherTest {
  private final Function<String, String> resource = f -> getClass().getResource(f).getFile();

  private FusionLauncher classUnderTest;

  @BeforeEach
  void setUp() {
    classUnderTest = new FusionLauncher();
  }

  @Test
  @Tag("merge")
  @DisplayName("It should throw illegal argument exception when parameter is invalid")
  void launch_Illegal() {
    assertThrows(IllegalArgumentException.class, () -> classUnderTest.launch(""));
  }

  @Test
  @Tag("merge")
  @DisplayName("It should read from a provided source directory")
  void launch_Source() {
    classUnderTest.launch(resource.apply("collect"));
  }
}