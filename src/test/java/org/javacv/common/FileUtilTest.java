package org.javacv.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Unit test for {@link FileUtil}.
 */
class FileUtilTest {

    private String startDir;

    @BeforeEach
    void setup() {
        Path p = Paths.get(getClass().getResource("1.txt").getFile());
        startDir = p.getParent().toString();
    }

    @Test
    @Tag("files")
    @DisplayName("It return empty list for current list and no suffix.")
    void filterFiles_NotNull() {
        List<Path> result = FileUtil.filterFiles(startDir, new String[]{});
        assertTrue(result.isEmpty());
    }

    @Test
    @Tag("files")
    @DisplayName("It should return files from this and directory below.")
    void filterFiles() {
        List<Path> result = FileUtil.filterFiles(startDir, new String[]{".txt"});
        assertEquals(2, result.size());
    }
}