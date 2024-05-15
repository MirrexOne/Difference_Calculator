package hexlet.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @Test
    void testStylishGenerateDifferenceJson() throws IOException {
        String expected = readFileData("result_stylish_json.txt");
        String actual = Differ.generate(pathToFirstJsonFile, pathToSecondJsonFile, "stylish");
        assertEquals(expected, actual);
    }

    @Test
    void testPlaintGenerateDifferenceJson() throws IOException {
        String expected = readFileData("result_plain_json.txt");
        String actual = Differ.generate(pathToFirstJsonFile, pathToSecondJsonFile, "plain");
        assertEquals(expected, actual);
    }

    @Test
    void testJsonFormatGenerateDifferenceJson() throws IOException {
        String expected = readFileData("result_jsonFormat_json.txt");
        String actual = Differ.generate(pathToFirstJsonFile, pathToSecondJsonFile, "json");
        assertEquals(expected, actual);
    }

    @Test
    void testStylishGenerateDifferenceYaml() throws IOException {
        String expected = readFileData("result_stylish_yaml.txt");
        String actual = Differ.generate(pathToFirstYamlFile, pathToSecondYamlFile, "stylish");
        assertEquals(expected, actual);
    }

    @Test
    void testPlainGenerateDifferenceYaml() throws IOException {
        String expected = readFileData("result_plain_yaml.txt");
        String actual = Differ.generate(pathToFirstYamlFile, pathToSecondYamlFile, "plain");
        assertEquals(expected, actual);
    }

    @Test
    void testJsonFormatGenerateDifferenceYaml() throws IOException {
        String expected = readFileData("result_jsonFormat_yaml.txt");
        String actual = Differ.generate(pathToFirstYamlFile, pathToSecondYamlFile, "json");
        assertEquals(expected, actual);
    }

    @Test
    void testDefaultGenerateDifferenceJson() throws IOException {
        String expected = readFileData("result_stylish_json.txt");
        String actual = Differ.generate(pathToFirstJsonFile, pathToSecondJsonFile);
        assertEquals(expected, actual);
    }

    @Test
    void testDefaultGenerateDifferenceYaml() throws IOException {
        String expected = readFileData("result_stylish_yaml.txt");
        String actual = Differ.generate(pathToFirstYamlFile, pathToSecondYamlFile);
        assertEquals(expected, actual);
    }
}
