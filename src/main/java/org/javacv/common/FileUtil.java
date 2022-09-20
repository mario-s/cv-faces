package org.javacv.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Optional.ofNullable;

/**
 * Utility for file operations.
 */
public final class FileUtil {
    private static final Logger LOG = LoggerFactory.getLogger(FileUtil.class);

    private FileUtil() {
        //nothing to see here
    }

    /**
     * Filters files recursive starting with the given start directory.
     * @param startDir directory to start.
     * @param suffixes suffixes for the files which should be returned
     * @return a collection of {@link Path} to the images files
     */
    public static Collection<Path> filterFiles(String startDir, String[] suffixes) {
        Path start = Paths.get(startDir);
        try (Stream<Path> stream = Files.walk(start)) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(path -> hasMatchingSuffix(suffixes, path))
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private static boolean hasMatchingSuffix(String[] suffixes, Path path) {
        var name = ofNullable(path)
            .map(p -> p.getFileName())
            .map(n -> n.toString().toLowerCase()).orElse("");
        for (String suffix : suffixes) {
            if (name.endsWith(suffix)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Delete the file silent if present, success will be written to log
     */
    public static void deleteIfPresent(File file) {
        if(file.exists()){
            boolean success = file.delete();
            LOG.debug("delete file {} successful: {}", file, success);
        }
    }
}
