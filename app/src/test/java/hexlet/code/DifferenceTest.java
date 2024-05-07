package hexlet.code;

import hexlet.code.parsers.Parser;
import hexlet.code.parsers.ParserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DifferenceTest {
    
    
    private Parser firstJsonParser;
    private Parser secondJsonParser;
    private Parser firstYamlParser;
    private Parser secondYamlParser;

    private String extensionFirstJson;
    private String extensionSecondJson;
    private String extensionFirstYaml;
    private String extensionSecondYaml;

    private String pathToFirstJsonFile;
    private String pathToSecondJsonFile;
    private String pathToFirstYamlFile;
    private String pathToSecondYamlFile;

    private Path firstJsonNormalizedPath;
    private Path secondJsonNormalizedPath;
    private Path firstYamlNormalizedPath;
    private Path secondYamlNormalizedPath;

    private File firstJsonRetrievedData;
    private File secondJsonRetrievedData;
    private File firstYamlRetrievedData;
    private File secondYamlRetrievedData;

    private Map<String, Object> firstJsonParsedData;
    private Map<String, Object> secondJsonParsedData;
    private Map<String, Object> firstYamlParsedData;
    private Map<String, Object> secondYamlParsedData;

    @BeforeEach
    public void beforeAll() throws IOException {
        pathToFirstJsonFile = "./src/test/resources/fixtures/file5JsonNested.json";
        pathToSecondJsonFile = "./src/test/resources/fixtures/file6JsonNested.json";
        pathToFirstYamlFile = "./src/test/resources/fixtures/file7YamlNested.yaml";
        pathToSecondYamlFile = "./src/test/resources/fixtures/file8YamlNested.yaml";
        
        extensionFirstJson = FileSystem.getFileExtension(pathToFirstJsonFile).map(String::toString).orElse(" ");
        extensionSecondJson = FileSystem.getFileExtension(pathToSecondJsonFile).map(String::toString).orElse(" ");
        extensionFirstYaml = FileSystem.getFileExtension(pathToFirstYamlFile).map(String::toString).orElse(" ");
        extensionSecondYaml = FileSystem.getFileExtension(pathToSecondYamlFile).map(String::toString).orElse(" ");

        firstJsonParser = ParserFactory.getParser(extensionFirstJson);
        secondJsonParser = ParserFactory.getParser(extensionSecondJson);
        firstYamlParser = ParserFactory.getParser(extensionFirstYaml);
        secondYamlParser = ParserFactory.getParser(extensionSecondYaml);

        firstJsonNormalizedPath = FileSystem.normalizePath(pathToFirstJsonFile);
        secondJsonNormalizedPath = FileSystem.normalizePath(pathToSecondJsonFile);
        firstYamlNormalizedPath = FileSystem.normalizePath(pathToFirstYamlFile);
        secondYamlNormalizedPath = FileSystem.normalizePath(pathToSecondYamlFile);

        firstJsonRetrievedData = FileSystem.retrieveFileData(firstJsonNormalizedPath);
        secondJsonRetrievedData = FileSystem.retrieveFileData(secondJsonNormalizedPath);
        firstYamlRetrievedData = FileSystem.retrieveFileData(firstYamlNormalizedPath);
        secondYamlRetrievedData = FileSystem.retrieveFileData(secondYamlNormalizedPath);

        firstJsonParsedData = firstJsonParser.parse(firstJsonRetrievedData);
        secondJsonParsedData = secondJsonParser.parse(secondJsonRetrievedData);
        firstYamlParsedData = firstYamlParser.parse(firstYamlRetrievedData);
        secondYamlParsedData = secondYamlParser.parse(secondYamlRetrievedData);

        
        

    }

    @Test
    void testGenerateTreeDifferenceJson() {
        String expected = "[{setting1=Modified key, value before=Some value, value after=Another value},"
                +
                " {setting2=Modified key, value before=200, value after=300},"
                +
                " {setting3=Modified key, value before=true, value after=none},"
                +
                " {key1=Deleted key, deleted value=value1},"
                +
                " {numbers1=Unchanged key, unchanged value=[1, 2, 3, 4]},"
                +
                " {numbers2=Modified key, value before=[2, 3, 4, 5], value after=[22, 33, 44, 55]},"
                +
                " {id=Modified key, value before=45, value after=null},"
                +
                " {default=Modified key, value before=null, value after=[value1, value2]},"
                +
                " {checked=Modified key, value before=false, value after=true},"
                +
                " {numbers3=Deleted key, deleted value=[3, 4, 5]},"
                +
                " {chars1=Unchanged key, unchanged value=[a, b, c]},"
                +
                " {chars2=Modified key, value before=[d, e, f], value after=false},"
                +
                " {key2=Added key, added value=value2},"
                +
                " {numbers4=Added key, added value=[4, 5, 6]},"
                +
                " {obj1=Added key, added value={nestedKey=value, isNested=true}}]";

        List<Map<String, Object>> actual = Difference.generateDifference(firstJsonParsedData, secondJsonParsedData);
        assertEquals(expected, actual.toString());
    }

    @Test
    void testGenerateTreeDifferenceYaml() {
        String expected = "[{doe=Modified key, value before=a deer, a female deer, value after=cat},"
                +
                " {ray=Modified key, value before=a drop of golden sun, value after=Crucial moment of the world},"
                +
                " {pi=Unchanged key, unchanged value=3.14159},"
                +
                " {xmas=Modified key, value before=true, value after=false},"
                +
                " {french-hens=Unchanged key, unchanged value=3},"
                +
                " {calling-birds=Unchanged key, unchanged value=[huey, dewey, louie, fred]},"
                +
                " {xmas-fifth-day=Modified key, value before={calling-birds=four, french-hens=3, golden-rings=5},"
                +
                " value after={calling-birds=four, french-hens=3, golden-rings=1,"
                +
                " partridges={count=1, location=a pear tree}, turtle-doves=two}},"
                +
                " {partridges=Deleted key, deleted value={count=1, location=a pear tree, turtle-doves=two}}]";

        List<Map<String, Object>> actual = Difference.generateDifference(firstYamlParsedData, secondYamlParsedData);
        assertEquals(expected, actual.toString());
    }

    @Test
    void testGetFileExtensionJson() {
        String expected1 = "json";
        String expected2 = "json";

        String actual1 = extensionFirstJson;
        String actual2 = extensionSecondJson;

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }
}
