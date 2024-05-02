package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class Parser {

     static Map<String, Object> parseJson(Path pathToFile) throws IOException {
//        ObjectMapper mapperJson = new ObjectMapper();
//        TypeReference<HashMap<String, String>> specifiedType = new TypeReference<>() { };

        File createdFile = pathToFile.toFile();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModules(new JavaTimeModule());
        ObjectReader objectReader = objectMapper.reader();
        Map<String, Object> parsedJson = objectReader.forType(Map.class).readValue(createdFile);
        return parsedJson;
//        return mapperJson.readValue(createdFile, specifiedType);
    }

    static Map<String, Object> parseYaml(Path pathToFile) throws IOException {
        ObjectMapper mapperYaml = new YAMLMapper();
        TypeReference<Map<String, Object>> specifiedType = new TypeReference<>() { };

        mapperYaml.findAndRegisterModules();

        File createdFile = pathToFile.toFile();

        return mapperYaml.readValue(createdFile, specifiedType);
    }
}
