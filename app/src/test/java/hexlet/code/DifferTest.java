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
        pathToJsonFile1 = "./src/test/resources/file5JsonNested.json";
        pathToJsonFile2 = "./src/test/resources/file6JsonNested.json";
        pathToYamlFile1 = "./src/test/resources/file3.yaml";
        pathToYamlFile2 = "./src/test/resources/file4.yaml";

    }

    @Test
    void testDefaultGenerateJson() {
        String expected = "{\n"
                +
                "    chars1: [a, b, c]\n"
                +
                "  - chars2: [d, e, f]\n"
                +
                "  + chars2: false\n"
                +
                "  - checked: false\n"
                +
                "  + checked: true\n"
                +
                "  - default: null\n"
                +
                "  + default: [value1, value2]\n"
                +
                "  - id: 45\n"
                +
                "  + id: null\n"
                +
                "  - key1: value1\n"
                +
                "  + key2: value2\n"
                +
                "    numbers1: [1, 2, 3, 4]\n"
                +
                "  - numbers2: [2, 3, 4, 5]\n"
                +
                "  + numbers2: [22, 33, 44, 55]\n"
                +
                "  - numbers3: [3, 4, 5]\n"
                +
                "  + numbers4: [4, 5, 6]\n"
                +
                "  + obj1: {nestedKey=value, isNested=true}\n"
                +
                "  - setting1: Some value\n"
                +
                "  + setting1: Another value\n"
                +
                "  - setting2: 200\n"
                +
                "  + setting2: 300\n"
                +
                "  - setting3: true\n"
                +
                "  + setting3: none\n"
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
