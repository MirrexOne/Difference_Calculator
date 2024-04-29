package hexlet.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DifferTest {

    private String pathToFile1;
    private String pathToFile2;

    @BeforeEach
    public void beforeEach() {
        pathToFile1 = "./src/test/resources/file1.json";
        pathToFile2 = "./src/test/resources/file2.json";

    }

    @Test
    void testDefaultGenerate() {
        String expected = "{\n"
                +
                "  - follow: false\n"
                +
                "    host: hexlet.io\n"
                +
                "  - proxy: 123.234.53.22\n"
                +
                "  - timeout: 50\n"
                +
                "  + timeout: 20\n"
                +
                "  + verbose: verbose\n"
                +
                "}";
        try {
            String actual = Differ.generate(pathToFile1, pathToFile2);
            assertEquals(expected, actual);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    void testNormalizePath() {
        String expected = "src/test/resources/file1.json";
        Path actual;
        try {
            actual = Differ.normalizePath(expected);
            assertTrue(actual.endsWith(expected));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertTrue(actual.endsWith("src/test/resources/file1.json"));
    }
}
