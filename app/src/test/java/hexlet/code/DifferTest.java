package hexlet.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DifferTest {

    private String pathToJsonFile1;
    private String pathToJsonFile2;
    private String pathToYamlFile1;
    private String pathToYamlFile2;
    private Map<String, Object> jsonParsedData1;
    private Map<String, Object> jsonParsedData2;
    private Map<String, Object> yamlParsedData1;
    private Map<String, Object> yamlParsedData2;

    @BeforeEach
    public void beforeAll() throws IOException {
        pathToJsonFile1 = "./src/test/resources/fixtures/file5JsonNested.json";
        pathToJsonFile2 = "./src/test/resources/fixtures/file6JsonNested.json";
        pathToYamlFile1 = "./src/test/resources/fixtures/file7YamlNested.yaml";
        pathToYamlFile2 = "./src/test/resources/fixtures/file8YamlNested.yaml";
        yamlParsedData1 = Parser.parseYaml(Path.of(pathToYamlFile1));
        yamlParsedData2 = Parser.parseYaml(Path.of(pathToYamlFile2));

        jsonParsedData1 = Parser.parseJson(Path.of(pathToJsonFile1));
        jsonParsedData2 = Parser.parseJson(Path.of(pathToJsonFile2));

    }

    @Test
    void testGenerateTreeDifferenceJson() {
        String expected = "[{Modified key=setting1}, "
                +
                "{Modified key=setting2}, "
                +
                "{Modified key=setting3}, "
                +
                "{Deleted key=key1},"
                +
                " {Unchanged key=numbers1}, "
                +
                "{Modified key=numbers2},"
                +
                " {Modified key=id},"
                +
                " {Modified key=default}, "
                +
                "{Modified key=checked},"
                +
                " {Deleted key=numbers3}, "
                +
                "{Unchanged key=chars1}, "
                +
                "{Modified key=chars2}, "
                +
                "{Added key=key2}, "
                +
                "{Added key=numbers4}, "
                +
                "{Added key=obj1}]";

        List<Map<String, Object>> actual = Difference.generateDifference(jsonParsedData1, jsonParsedData2);
        assertEquals(expected, actual.toString());
    }

    @Test
    void testGenerateTreeDifferenceYaml() {
        String expected = "[{Modified key=doe},"
                +
                " {Modified key=ray},"
                +
                " {Unchanged key=pi},"
                +
                " {Modified key=xmas},"
                +
                " {Unchanged key=french-hens},"
                +
                " {Unchanged key=calling-birds},"
                +
                " {Modified key=xmas-fifth-day},"
                +
                " {Deleted key=partridges}]";

        List<Map<String, Object>> actual = Difference.generateDifference(yamlParsedData1, yamlParsedData2);
        assertEquals(expected, actual.toString());
    }

//    @Test
//    void testDefaultGenerateJson() throws IOException {
//        String expected = "{\n"
//                +
//                "    chars1: [a, b, c]\n"
//                +
//                "  - chars2: [d, e, f]\n"
//                +
//                "  + chars2: false\n"
//                +
//                "  - checked: false\n"
//                +
//                "  + checked: true\n"
//                +
//                "  - default: null\n"
//                +
//                "  + default: [value1, value2]\n"
//                +
//                "  - id: 45\n"
//                +
//                "  + id: null\n"
//                +
//                "  - key1: value1\n"
//                +
//                "  + key2: value2\n"
//                +
//                "    numbers1: [1, 2, 3, 4]\n"
//                +
//                "  - numbers2: [2, 3, 4, 5]\n"
//                +
//                "  + numbers2: [22, 33, 44, 55]\n"
//                +
//                "  - numbers3: [3, 4, 5]\n"
//                +
//                "  + numbers4: [4, 5, 6]\n"
//                +
//                "  + obj1: {nestedKey=value, isNested=true}\n"
//                +
//                "  - setting1: Some value\n"
//                +
//                "  + setting1: Another value\n"
//                +
//                "  - setting2: 200\n"
//                +
//                "  + setting2: 300\n"
//                +
//                "  - setting3: true\n"
//                +
//                "  + setting3: none\n"
//                +
//                "}";
//
//        String actual = Differ.generate(pathToJsonFile1, pathToJsonFile2);
//        assertEquals(expected, actual);
//    }

//    @Test
//    void testDefaultGenerateYaml() throws IOException {
//        String expected = "{\n"
//                +
//                "  - anyway: -1360869290\n"
//                +
//                "  - managed: -763042806.5871592\n"
//                +
//                "  - mighty: send\n"
//                +
//                "    percent: true\n"
//                +
//                "  - railroad: arrow\n"
//                +
//                "  + railroad: crucial\n"
//                +
//                "  - rhythm: discuss\n"
//                +
//                "  + age: account\n"
//                +
//                "  + caught: directly\n"
//                +
//                "  + crowd: -894811462\n"
//                +
//                "  + him: false\n"
//                +
//                "  + jungle: true\n"
//                +
//                "  + keep: thy\n"
//                +
//                "}";
//
//        String actual = Differ.generate(pathToYamlFile1, pathToYamlFile2);
//        assertEquals(expected, actual);
//    }

    @Test
    void testNormalizePath() throws IOException {
        String expected = "src/test/resources/fixtures/file1.json";
        Path actual = Parser.normalizePath(expected);
        assertTrue(actual.endsWith(expected));

    }
}
