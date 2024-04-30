package hexlet.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DifferTest {

    private String pathToJsonFile1;
    private String pathToJsonFile2;
    private String pathToYamlFile1;
    private String pathToYamlFile2;

    @BeforeEach
    public void beforeEach() {
        pathToJsonFile1 = "./src/test/resources/file1.json";
        pathToJsonFile2 = "./src/test/resources/file2.json";
        pathToYamlFile1 = "./src/test/resources/file3.yaml";
        pathToYamlFile2 = "./src/test/resources/file4.yaml";

    }

    @Test
    void testDefaultGenerateJson() {
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
                "  + verbose: true\n"
                +
                "}";

        try {
            String actual = Differ.generate(pathToJsonFile1, pathToJsonFile2);
            assertEquals(expected, actual);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testDefaultGenerateYaml() {
        String expected = "{\n"
                +
                "  - anyway: -1360869290\n"
                +
                "  - managed: -763042806.5871592\n"
                +
                "  - mighty: send\n"
                +
                "    percent: true\n"
                +
                "  - railroad: arrow\n"
                +
                "  + railroad: crucial\n"
                +
                "  - rhythm: discuss\n"
                +
                "  + age: account\n"
                +
                "  + caught: directly\n"
                +
                "  + crowd: -894811462\n"
                +
                "  + him: false\n"
                +
                "  + jungle: true\n"
                +
                "  + keep: thy\n"
                +
                "}";

        try {
            String actual = Differ.generate(pathToYamlFile1, pathToYamlFile2);
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
