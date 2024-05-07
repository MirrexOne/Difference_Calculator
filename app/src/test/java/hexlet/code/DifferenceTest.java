package hexlet.code;

import hexlet.code.parsers.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DifferenceTest {

    private static final String PATH_TO_FIXTURES = "./src/test/resources/fixtures/";
    private String pathToFirstJsonFile;
    private String pathToSecondJsonFile;
    private String pathToFirstYamlFile;
    private String pathToSecondYamlFile;

    @BeforeEach
    public void beforeAll() throws IOException {
        pathToFirstJsonFile = PATH_TO_FIXTURES + "file5JsonNested.json";
        pathToSecondJsonFile = PATH_TO_FIXTURES + "file6JsonNested.json";
        pathToFirstYamlFile = PATH_TO_FIXTURES + "file7YamlNested.yaml";
        pathToSecondYamlFile = PATH_TO_FIXTURES + "file8YamlNested.yaml";

    }

    @Test
    void testDefaultGenerateDifferenceJson() throws IOException {
        String expected = "\n"
                +
                "{\n"
                +
                "      chars1: [a, b, c]\n"
                +
                "    - chars2: [d, e, f]\n"
                +
                "    + chars2: false\n"
                +
                "    - checked: false\n"
                +
                "    + checked: true\n"
                +
                "    - default: null\n"
                +
                "    + default: [value1, value2]\n"
                +
                "    - id: 45\n"
                +
                "    + id: null\n"
                +
                "    - key1: value1\n"
                +
                "    + key2: value2\n"
                +
                "      numbers1: [1, 2, 3, 4]\n"
                +
                "    - numbers2: [2, 3, 4, 5]\n"
                +
                "    + numbers2: [22, 33, 44, 55]\n"
                +
                "    - numbers3: [3, 4, 5]\n"
                +
                "    + numbers4: [4, 5, 6]\n"
                +
                "    + obj1: {nestedKey=value, isNested=true}\n"
                +
                "    - setting1: Some value\n"
                +
                "    + setting1: Another value\n"
                +
                "    - setting2: 200\n"
                +
                "    + setting2: 300\n"
                +
                "    - setting3: true\n"
                +
                "    + setting3: none\n"
                +
                "}\n";

        String actual = Differ.generate(pathToFirstJsonFile, pathToSecondJsonFile, "stylish");
        assertEquals(expected, actual);

    }

    @Test
    void testDefaultGenerateDifferenceYaml() throws IOException {
        String expected = "\n"
                +
                "{\n"
                +
                "      calling-birds: [huey, dewey, louie, fred]\n"
                +
                "    - doe: a deer, a female deer\n"
                +
                "    + doe: cat\n"
                +
                "      french-hens: 3\n"
                +
                "    - partridges: {count=1, location=a pear tree, turtle-doves=two}\n"
                +
                "      pi: 3.14159\n"
                +
                "    - ray: a drop of golden sun\n"
                +
                "    + ray: Crucial moment of the world\n"
                +
                "    - xmas: true\n"
                +
                "    + xmas: false\n"
                +
                "    - xmas-fifth-day: {calling-birds=four, french-hens=3, golden-rings=5}\n"
                +
                "    + xmas-fifth-day: {calling-birds=four, french-hens=3, golden-rings=1, "
                +
                "partridges={count=1, location=a pear tree}, turtle-doves=two}\n"
                +
                "}\n";

        String actual = Differ.generate(pathToFirstYamlFile, pathToSecondYamlFile, "stylish");
        assertEquals(expected, actual);
    }

}
