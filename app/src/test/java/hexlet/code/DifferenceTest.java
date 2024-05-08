package hexlet.code;

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
    public void beforeEach() throws IOException {
        pathToFirstJsonFile = PATH_TO_FIXTURES + "file5JsonNested.json";
        pathToSecondJsonFile = PATH_TO_FIXTURES + "file6JsonNested.json";
        pathToFirstYamlFile = PATH_TO_FIXTURES + "file7YamlNested.yaml";
        pathToSecondYamlFile = PATH_TO_FIXTURES + "file8YamlNested.yaml";
    }

    @Test
    void testDefaultGenerateDifferenceJson() throws IOException {
        String expected =
                "{\n"
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

        String actual = Differ.generate(pathToFirstJsonFile, pathToSecondJsonFile, "stylish");
        assertEquals(expected, actual);
    }

    @Test
    void testDefaultGenerateDifferenceYaml() throws IOException {
        String expected =
                "{\n"
                +
                "    calling-birds: [huey, dewey, louie, fred]\n"
                +
                "  - doe: a deer, a female deer\n"
                +
                "  + doe: cat\n"
                +
                "    french-hens: 3\n"
                +
                "  - partridges: {count=1, location=a pear tree, turtle-doves=two}\n"
                +
                "    pi: 3.14159\n"
                +
                "  - ray: a drop of golden sun\n"
                +
                "  + ray: Crucial moment of the world\n"
                +
                "  - xmas: true\n"
                +
                "  + xmas: false\n"
                +
                "  - xmas-fifth-day: {calling-birds=four, french-hens=3, golden-rings=5}\n"
                +
                "  + xmas-fifth-day: {calling-birds=four, french-hens=3, golden-rings=1, "
                +
                "partridges={count=1, location=a pear tree}, turtle-doves=two}\n"
                +
                "}";

        String actual = Differ.generate(pathToFirstYamlFile, pathToSecondYamlFile, "stylish");
        assertEquals(expected, actual);
    }

    @Test
    void testPlainGenerateDifferenceJson() throws IOException {
        String expected =
                "Property 'chars2' was updated. From [complex value] to false\n"
                +
                "Property 'checked' was updated. From false to true\n"
                +
                "Property 'default' was updated. From null to [complex value]\n"
                +
                "Property 'id' was updated. From 45 to null\n"
                +
                "Property 'key1' was removed\n"
                +
                "Property 'key2' was added with value: 'value2'\n"
                +
                "Property 'numbers2' was updated. From [complex value] to [complex value]\n"
                +
                "Property 'numbers3' was removed\n"
                +
                "Property 'numbers4' was added with value: [complex value]\n"
                +
                "Property 'obj1' was added with value: [complex value]\n"
                +
                "Property 'setting1' was updated. From 'Some value' to 'Another value'\n"
                +
                "Property 'setting2' was updated. From 200 to 300\n"
                +
                "Property 'setting3' was updated. From true to 'none'";

        String actual = Differ.generate(pathToFirstJsonFile, pathToSecondJsonFile, "plain");
        assertEquals(expected, actual);
    }

    @Test
    void testPlainGenerateDifferenceYaml() throws IOException {
        String expected =
                "Property 'doe' was updated. From 'a deer, a female deer' to 'cat'\n"
                +
                "Property 'partridges' was removed\n"
                +
                "Property 'ray' was updated. From 'a drop of golden sun' to 'Crucial moment of the world'\n"
                +
                "Property 'xmas' was updated. From true to false\n"
                +
                "Property 'xmas-fifth-day' was updated. From [complex value] to [complex value]";

        String actual = Differ.generate(pathToFirstYamlFile, pathToSecondYamlFile, "plain");
        assertEquals(expected, actual);
    }

    @Test
    void testJsonFormatGenerateDifferenceJson() throws IOException {
        String expected =
                        "[{\"chars1\":\"unchanged\",\"value\":[\"a\",\"b\",\"c\"]},"
                        +
                        "{\"chars2\":\"modified\",\"value1\":[\"d\",\"e\",\"f\"],\"value2\":false},"
                        +
                        "{\"checked\":\"modified\",\"value1\":false,\"value2\":true},"
                        +
                        "{\"default\":\"modified\",\"value1\":null,\"value2\":[\"value1\",\"value2\"]},"
                        +
                        "{\"id\":\"modified\",\"value1\":45,\"value2\":null},"
                        +
                        "{\"key1\":\"deleted\",\"value\":\"value1\"},"
                        +
                        "{\"key2\":\"added\",\"value\":\"value2\"},"
                        +
                        "{\"numbers1\":\"unchanged\",\"value\":[1,2,3,4]},"
                        +
                        "{\"numbers2\":\"modified\",\"value1\":[2,3,4,5],\"value2\":[22,33,44,55]},"
                        +
                        "{\"numbers3\":\"deleted\",\"value\":[3,4,5]},"
                        +
                        "{\"numbers4\":\"added\",\"value\":[4,5,6]},"
                        +
                        "{\"obj1\":\"added\",\"value\":{\"nestedKey\":\"value\",\"isNested\":true}},"
                        +
                        "{\"setting1\":\"modified\",\"value1\":\"Some value\",\"value2\":\"Another value\"},"
                        +
                        "{\"setting2\":\"modified\",\"value1\":200,\"value2\":300},"
                        +
                        "{\"setting3\":\"modified\",\"value1\":true,\"value2\":\"none\"}]";

        String actual = Differ.generate(pathToFirstJsonFile, pathToSecondJsonFile, "json");
        assertEquals(expected, actual);
    }

    @Test
    void testJsonFormatGenerateDifferenceYaml() throws IOException {
        String expected =
                        "[{\"calling-birds\":\"unchanged\",\"value\":[\"huey\",\"dewey\",\"louie\",\"fred\"]},"
                                +
                        "{\"doe\":\"modified\",\"value1\":\"a deer, a female deer\",\"value2\":\"cat\"},"
                                +
                        "{\"french-hens\":\"unchanged\",\"value\":3},"
                                +
                        "{\"partridges\":\"deleted\",\"value\":{\"count\":1,\"location\":\"a pear tree\""
                                +
                        ",\"turtle-doves\":\"two\"}},"
                                +
                        "{\"pi\":\"unchanged\",\"value\":3.14159},"
                                +
                        "{\"ray\":\"modified\",\"value1\":\"a drop of golden sun\","
                                +
                        "\"value2\":\"Crucial moment of the world\"},"
                                +
                        "{\"xmas\":\"modified\",\"value1\":true,\"value2\":false},"
                                +
                        "{\"xmas-fifth-day\":\"modified\",\"value1\":{\"calling-birds\":\"four\",\"french-hens\":3,"
                                +
                        "\"golden-rings\":5},\"value2\":{\"calling-birds\":\"four\",\"french-hens\":3,"
                                +
                        "\"golden-rings\":1,\"partridges\":{\"count\":1,\"location\":\"a pear tree\"},"
                                +
                        "\"turtle-doves\":\"two\"}}]";

        String actual = Differ.generate(pathToFirstYamlFile, pathToSecondYamlFile, "json");
        assertEquals(expected, actual);
    }
}
