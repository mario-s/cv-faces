package org.javacv.fusion;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

class ImageFilesCollector {

  private static final String JPG = ".jpg";
  private static final String JPEG = ".jpeg";
  private static final String PNG = ".png";

  private final String sourcePath;
  private String target;

  ImageFilesCollector(String sourcePath) {
    this.sourcePath = sourcePath;
  }

  String [] getSupportedSuffixes() {
    return new String [] {JPG, JPEG, PNG};
  }

  Optional<String> getTarget() {
    return ofNullable(target);
  }

  Collection<String> listFiles() throws IOException {
    Set<String> fileList = new HashSet<>();
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(sourcePath))) {
      stream.forEach(path -> {
        if (isImageFile(path)) {
          fileList.add(path.toString());
        }
      });
    }

    createTarget(fileList);

    return fileList;
  }

  private void createTarget(Set<String> fileList) {
    var iterator = fileList.iterator();
    if (iterator.hasNext()) {
      var first = iterator.next();
      var path = getPath(first);
      var suffix = getSuffix(first);
      target = format("%s%sout%s", path, File.separator, suffix);
    }
  }

  private String getPath(String name) {
    var sep = name.lastIndexOf(File.separator);
    return name.substring(0, sep);
  }


  private String getSuffix(String name) {
    int dot = name.lastIndexOf(".");
    return name.substring(dot);
  }

  private boolean isImageFile(Path path) {
    boolean isImage = false;
    if (!Files.isDirectory(path)) {
      var name = path.getFileName().toString().toLowerCase(Locale.ENGLISH);
      isImage =  name.endsWith(JPG) || name.endsWith(JPEG) || name.endsWith(PNG);
    }
    return isImage;
  }
}
