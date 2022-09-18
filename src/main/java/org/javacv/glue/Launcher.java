package org.javacv.glue;

import java.util.function.Function;

public interface Launcher {

  void launch(String ... args);

  Function<String[], String> detectorType = args -> (args != null && args.length > 0) ? args[0] : "dnn";
}
