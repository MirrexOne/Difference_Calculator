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

//        System.out.println("Unsorted map 1: " + parsedJson1);
        Map<String, String> sortedMap1 = sortMap(parsedJson1);
//        System.out.println("Unsorted map 2: " + parsedJson2);
        Map<String, String> sortedMap2 = sortMap(parsedJson2);

//        System.out.println("Sorted map 1: " +  sortedMap1);
//        System.out.println("Sorted map 2: " +  sortedMap2);

        StringBuilder difference = new StringBuilder();
        difference.append("{").append("\n");

        for (Map.Entry<String, String> entry : sortedMap1.entrySet()) {
            String key1 = entry.getKey();
            String value1 = entry.getValue();

            if ((sortedMap1.containsKey(key1) && sortedMap2.containsKey(key1))
                    && (sortedMap1.containsValue(value1) && sortedMap2.containsValue(value1))) {
                difference.append("    ").append(key1).append(": ").append(value1).append("\n");
            } else if ((sortedMap1.containsKey(key1) && sortedMap2.containsKey(key1))
                    && !(sortedMap1.get(key1).equals(sortedMap2.get(key1)))) {
                difference.append("  - ").append(key1).append(": ").append(value1).append("\n");
                difference.append("  + ").append(key1).append(": ").append(sortedMap2.get(key1)).append("\n");
            } else if (!(sortedMap1.containsKey(key1) && sortedMap2.containsKey(key1))) {
                difference.append("  - ").append(key1).append(": ").append(value1).append("\n");
            }

        }

        for (Map.Entry<String, String> entry1 : sortedMap2.entrySet()) {
            String key2 = entry1.getKey();
            String value2 = entry1.getKey();

            if (!sortedMap1.containsKey(key2)) {
                difference.append("  + ").append(key2).append(": ").append(value2).append("\n");
            }

        }

        difference.append("}");

//        System.out.println(difference);
        return difference.toString();

    }

    private static Map<String, String> sortMap(Map<String, String> unsortedMap) {
        return unsortedMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    private static Map<String, String> parse(Path pathToFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, String>> specifiedType = new TypeReference<>() {
        };

        File createdFile = pathToFile.toFile();

        return mapper.readValue(createdFile, specifiedType);
    }

    private static Path normalizePath(String path) throws IOException {
        Path normalizeAbsolutePath = Paths.get(path).toAbsolutePath().normalize();

        if (!Files.exists(normalizeAbsolutePath)) {
            throw new IOException("File '" + normalizeAbsolutePath + "' does not exist");
        }

        return normalizeAbsolutePath;
    }

}
