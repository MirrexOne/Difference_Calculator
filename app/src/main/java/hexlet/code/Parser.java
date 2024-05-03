package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

final class Parser {

    private Parser() {
        throw new IllegalStateException("Utility class");
    }

    static Map<String, Object> parseJson(Path pathToFile) throws IOException {
        File createdFile = pathToFile.toFile();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModules(new JavaTimeModule());
        ObjectReader objectReader = objectMapper.reader();

        return objectReader.forType(Map.class).readValue(createdFile);
    }

    static Map<String, Object> parseYaml(Path pathToFile) throws IOException {
        ObjectMapper mapperYaml = new YAMLMapper();
        TypeReference<Map<String, Object>> specifiedType = new TypeReference<>() {
        };

        mapperYaml.findAndRegisterModules();

        File createdFile = pathToFile.toFile();

        return mapperYaml.readValue(createdFile, specifiedType);
    }

    static Path normalizePath(String path) throws IOException {
        Path normalizeAbsolutePath = Paths.get(path).toAbsolutePath().normalize();

        if (!Files.exists(normalizeAbsolutePath)) {
            throw new IOException("File '" + normalizeAbsolutePath + "' does not exist");
        }

        return normalizeAbsolutePath;
    }

    static Map<String, Object> sortMap(Map<String, Object> unsortedMap) {
        Map<String, Object> sortedParsedMap = new LinkedHashMap<>();
        unsortedMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(el -> sortedParsedMap.put(el.getKey(), el.getValue()));
        return sortedParsedMap;

    }

}
