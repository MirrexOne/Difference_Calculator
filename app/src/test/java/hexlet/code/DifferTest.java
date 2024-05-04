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

//        List<Map<String, Object>> actual = Difference.generateDifference(jsonParsedData1, jsonParsedData2);
//        assertEquals(expected, actual.toString());
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

//        List<Map<String, Object>> actual = Difference.generateDifference(yamlParsedData1, yamlParsedData2);
//        assertEquals(expected, actual.toString());
    }
}
