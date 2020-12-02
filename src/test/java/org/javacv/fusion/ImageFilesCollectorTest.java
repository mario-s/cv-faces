package org.javacv.fusion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class ImageFilesCollectorTest {
  private final Function<String, String> resource = f -> getClass().getResource(f).getFile();

  private ImageFilesCollector classUnderTest;

  @BeforeEach
  void setUp() {
    classUnderTest = new ImageFilesCollector(resource.apply("collect"));
  }

  @Test
  @DisplayName("It should return array of supported suffixes")
  void supportedSuffixes() {
    var result = classUnderTest.getSupportedSuffixes();
    assertEquals(3, result.length);
  }

  @Test
  @DisplayName("It should list only image files")
  void listFiles() {
    var result = classUnderTest.listFiles();
    assertEquals(3, result.size());
  }

  @Test
  @DisplayName("It should return a target based on the given source files.")
  void getTarget() {
    classUnderTest.listFiles();
    var res = classUnderTest.getTarget().get();
    var expected = resource.apply("collect") + File.separator +  "out.jpg";
    assertEquals(expected, res);
  }

  @Test
  @DisplayName("It should return an empty list of source files for an empty directory.")
  void emptyList() {
    var instance = new ImageFilesCollector(resource.apply("../common"));
    var result = instance.listFiles();
    assertTrue(result.isEmpty());
  }
}