package hexlet.code.parsers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Yaml extends Parser {

    @Override
    public Map<String, Object> parse(File retrievedFileData) throws IOException {
        ObjectMapper mapperYaml = new YAMLMapper();
        TypeReference<Map<String, Object>> specifiedType = new TypeReference<>() { };

        mapperYaml.findAndRegisterModules();

        return mapperYaml.readValue(retrievedFileData, specifiedType);
    }
}
