package org.javacv.fusion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FusionLauncherTest {
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
  @DisplayName("It should read from a provided txt file")
  void launch_Source() {
    classUnderTest.launch("test.txt");
  }
}