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
                """
                        Property 'chars2' was updated. From [complex value] to false
                        Property 'checked' was updated. From false to true
                        Property 'default' was updated. From null to [complex value]
                        Property 'id' was updated. From 45 to null
                        Property 'key1' was removed
                        Property 'key2' was added with value: 'value2'
                        Property 'numbers2' was updated. From [complex value] to [complex value]
                        Property 'numbers3' was removed
                        Property 'numbers4' was added with value: [complex value]
                        Property 'obj1' was added with value: [complex value]
                        Property 'setting1' was updated. From 'Some value' to 'Another value'
                        Property 'setting2' was updated. From 200 to 300
                        Property 'setting3' was updated. From true to 'none'\

                        """;

        String actual = Differ.generate(pathToFirstJsonFile, pathToSecondJsonFile, "plain");
        assertEquals(expected, actual);
    }

    @Test
    void testPlainGenerateDifferenceYaml() throws IOException {
        String expected =
                """
                        Property 'doe' was updated. From 'a deer, a female deer' to 'cat'
                        Property 'partridges' was removed
                        Property 'ray' was updated. From 'a drop of golden sun' to 'Crucial moment of the world'
                        Property 'xmas' was updated. From true to false
                        Property 'xmas-fifth-day' was updated. From [complex value] to [complex value]\

                        """;

        String actual = Differ.generate(pathToFirstYamlFile, pathToSecondYamlFile, "plain");
        assertEquals(expected, actual);
    }

}
