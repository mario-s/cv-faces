package org.javacv.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Unit test for {@link FileUtil}.
 */
class FileUtilTest {

    private String startDir;

    @BeforeEach
    void setup() {
        startDir = ofNullable(getClass().getResource("1.txt"))
            .map(res -> Paths.get(res.getFile()))
            .map(p -> p.getParent().toString())
            .orElse(null);
        assumeTrue(startDir != null, "expected a start directory.");
    }

    @Test
    @Tag("files")
    @DisplayName("It return empty list for current list and no suffix.")
    void filterFiles_NotNull() {
        Collection<Path> result = FileUtil.filterFiles(startDir, new String[]{});
        assertTrue(result.isEmpty());
    }

    @Test
    @Tag("files")
    @DisplayName("It should return files from this and directory below.")
    void filterFiles() {
        Collection<Path> result = FileUtil.filterFiles(startDir, new String[]{".txt"});
        assertEquals(2, result.size());
    }
}