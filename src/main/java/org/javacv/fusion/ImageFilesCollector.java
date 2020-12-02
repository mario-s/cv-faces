package org.javacv.fusion;

import org.javacv.common.FileUtil;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;

class ImageFilesCollector {

  private static final String JPG = ".jpg";
  private static final String JPEG = ".jpeg";
  private static final String PNG = ".png";

  private final String sourcePath;
  private Optional<String> target;

  ImageFilesCollector(String sourcePath) {
    this.sourcePath = sourcePath;
  }

  String [] getSupportedSuffixes() {
    return new String [] {JPG, JPEG, PNG};
  }

  Optional<String> getTarget() {
    return target;
  }

  Collection<String> listFiles() {
    Collection<Path> files = FileUtil.filterFiles(sourcePath, getSupportedSuffixes());
    createTarget(files.stream().findFirst());
    return filterOutTarget(files);
  }

  private Set<String> filterOutTarget(Collection<Path> files) {
    return target.map(t -> {
      var stream = files.stream().map(Path::toString);
      return stream.filter(s -> !s.equals(t)).collect(Collectors.toSet());
    }).orElseGet(() -> Collections.emptySet());
  }


  private void createTarget(Optional<Path> first) {
    target = first.map(f -> {
      var path = f.getParent().toString();
      var suffix = getSuffix(f.getFileName().toString());
      return format("%s%sout%s", path, File.separator, suffix);
    });
  }

  private String getSuffix(String name) {
    int dot = name.lastIndexOf(".");
    return name.substring(dot);
  }
}
