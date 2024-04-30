package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Parser {

    static Map<String, String> parseJson(Path pathToFile) throws IOException {
        ObjectMapper mapperJson = new ObjectMapper();
        TypeReference<HashMap<String, String>> specifiedType = new TypeReference<>() { };

        File createdFile = pathToFile.toFile();

        return mapperJson.readValue(createdFile, specifiedType);
    }

    static Map<String, String> parseYaml(Path pathToFile) throws IOException {
        ObjectMapper mapperYaml = new YAMLMapper();
        TypeReference<HashMap<String, String>> specifiedType = new TypeReference<>() { };

        mapperYaml.findAndRegisterModules();

        File createdFile = pathToFile.toFile();

        return mapperYaml.readValue(createdFile, specifiedType);
    }
}
