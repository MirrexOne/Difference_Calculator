package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Differ {

    public static String generate(String pathToFile1, String pathToFile2) throws IOException {
        Path normalizePath1 = normalizePath(pathToFile1);
        Path normalizePath2 = normalizePath(pathToFile2);

        Map<String, String> parsedJson1 = parse(normalizePath1);
        Map<String, String> parsedJson2 = parse(normalizePath2);

        Map<String, String> sortedMap1 = sortMap(parsedJson1);
        Map<String, String> sortedMap2 = sortMap(parsedJson2);

        return generateJsonDifference(sortedMap1, sortedMap2);

    }

    private static String generateJsonDifference(Map<String, String> jsonData, Map<String, String> jsonData1) {

        StringBuilder difference = new StringBuilder();
        difference.append("{").append("\n");

        for (Map.Entry<String, String> entry : jsonData.entrySet()) {
            String key1 = entry.getKey();
            String value1 = entry.getValue();

            if ((jsonData.containsKey(key1) && jsonData1.containsKey(key1))
                    && (jsonData.containsValue(value1) && jsonData1.containsValue(value1))) {
                difference.append("    ").append(key1).append(": ").append(value1).append("\n");
            } else if ((jsonData.containsKey(key1) && jsonData1.containsKey(key1))
                    && !(jsonData.get(key1).equals(jsonData1.get(key1)))) {
                difference.append("  - ").append(key1).append(": ").append(value1).append("\n");
                difference.append("  + ").append(key1).append(": ").append(jsonData1.get(key1)).append("\n");
            } else if (!(jsonData.containsKey(key1) && jsonData1.containsKey(key1))) {
                difference.append("  - ").append(key1).append(": ").append(value1).append("\n");
            }
        }

        for (Map.Entry<String, String> entry1 : jsonData1.entrySet()) {
            String key2 = entry1.getKey();
            String value2 = entry1.getKey();

            if (!jsonData.containsKey(key2)) {
                difference.append("  + ").append(key2).append(": ").append(value2).append("\n");
            }

        }

        difference.append("}");

        return difference.toString();
    }


    protected static Map<String, String> sortMap(Map<String, String> unsortedMap) {
        return unsortedMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    protected static Map<String, String> parse(Path pathToFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, String>> specifiedType = new TypeReference<>() {
        };

        File createdFile = pathToFile.toFile();

        return mapper.readValue(createdFile, specifiedType);
    }

    protected static Path normalizePath(String path) throws IOException {
        Path normalizeAbsolutePath = Paths.get(path).toAbsolutePath().normalize();

        if (!Files.exists(normalizeAbsolutePath)) {
            throw new IOException("File '" + normalizeAbsolutePath + "' does not exist");
        }

        return normalizeAbsolutePath;
    }

}
