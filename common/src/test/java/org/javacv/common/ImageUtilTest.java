package org.javacv.common;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.bytedeco.javacpp.opencv_core.Size;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

class ImageUtilTest {

  private final Function<String, String> resource = f -> getClass().getResource(f).getFile();

  private String source;

  @BeforeEach
  void setup() {
    source = resource.apply("witch.png");
  }

  private void multiply(Mat src) {
    var s = new Scalar(255.0,0.0,255.0,0.0);
    var filter = new Mat(src.rows(), src.cols(), src.type(), s);
    var dest = src.mul(filter).a();
    assertFalse(dest.empty());
  }

  @Test
  @DisplayName("It should read an image which can be multiplied.")
  void read() {
    var res = ImageUtil.read(singletonList(source));
    multiply(res.get(0));
  }

  @Test
  @DisplayName("It should read an image as gray which can be multiplied.")
  void readAsGrey() {
    Mat img = ImageUtil.readAsGray(source);
    multiply(img);
  }

  @Test
  @DisplayName("It should resize an image")
  void resize() {
    var src = new Mat(200, 200, 2);
    var size = new Size(100,100);
    var res = ImageUtil.resize(src, size);
    assertAll(
      () -> assertEquals(size.width(), res.size().width()),
      () -> assertEquals(size.height(), res.size().height()));
  }
}