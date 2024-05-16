package hexlet.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    private static String readFileData(String fileName) throws IOException {
        final String pathToExpectedResult = "./src/test/resources/expected_results/";
        Path path = Paths.get(pathToExpectedResult + fileName);

        StringBuilder sb = new StringBuilder();
        BufferedReader reader = Files.newBufferedReader(path);
        try (reader) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                sb.append(currentLine);
                sb.append("\n");
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

        reader.close();
        return sb.substring(0, sb.length() - 1);
    }

    @ParameterizedTest
    @CsvSource({
            "stylish, result_stylish_json.txt",
            "plain, result_plain_json.txt",
            "json, result_jsonFormat_json.txt",
            "'', result_stylish_json.txt"
    })
    void testStylishGenerateDifferenceJson(String renderingFormat, String fileName) throws IOException {
        String expected = readFileData("json_files/" + fileName);
        String actual = Differ.generate(pathToFirstJsonFile, pathToSecondJsonFile, renderingFormat);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({
            "stylish, result_stylish_yaml.txt",
            "plain, result_plain_yaml.txt",
            "json, result_jsonFormat_yaml.txt",
            "'', result_stylish_yaml.txt"
    })
    void testStylishGenerateDifferenceYaml(String renderingFormat, String fileName) throws IOException {
        String expected = readFileData("yaml_files/" + fileName);
        String actual = Differ.generate(pathToFirstYamlFile, pathToSecondYamlFile, renderingFormat);
        assertEquals(expected, actual);
    }
}
