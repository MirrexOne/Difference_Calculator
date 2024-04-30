package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.stream.Collectors;

import static hexlet.code.Parser.parseJson;
import static hexlet.code.Parser.parseYaml;

public class Differ {

    public static String generate(String pathToFile1, String pathToFile2) throws IOException {
        Optional<String> fileExtension1 = getFileExtension(pathToFile1);
        Optional<String> fileExtension2 = getFileExtension(pathToFile2);

        Path normalizePath1 = normalizePath(pathToFile1);
        Path normalizePath2 = normalizePath(pathToFile2);

        Map<String, String> typeDataParsed1 = "json".equalsIgnoreCase(fileExtension1.orElseThrow())
                ? parseJson(normalizePath1) : parseYaml(normalizePath1);

        Map<String, String> typeDataParsed2 = "json".equalsIgnoreCase(fileExtension2.orElseThrow())
                ? parseJson(normalizePath2) : parseYaml(normalizePath2);

        System.out.println("Parsed yaml 1: " + typeDataParsed1);
        System.out.println("Parsed yaml 2: " + typeDataParsed2);

        Map<String, String> sortParsedData1 = sortMap(typeDataParsed1);
        Map<String, String> sortParsedData2 = sortMap(typeDataParsed2);
        System.out.println("Sorted yaml 1: " + sortParsedData1);
        System.out.println("Sorted yaml 2: " + sortParsedData2);

        return generateDifference(sortParsedData1, sortParsedData2);

    }

    private static String generateDifference(Map<String, String> fileData1, Map<String, String> fileData2) {

        StringBuilder difference = new StringBuilder();
        difference.append("{").append("\n");

        for (Map.Entry<String, String> entry : fileData1.entrySet()) {
            String key1 = entry.getKey();
            String value1 = entry.getValue();

            if ((fileData1.containsKey(key1) && fileData2.containsKey(key1))
                    && (fileData1.containsValue(value1) && fileData2.containsValue(value1))) {
                difference.append("    ").append(key1).append(": ").append(value1).append("\n");
            } else if ((fileData1.containsKey(key1) && fileData2.containsKey(key1))
                    && !(fileData1.get(key1).equals(fileData2.get(key1)))) {
                difference.append("  - ").append(key1).append(": ").append(value1).append("\n");
                difference.append("  + ").append(key1).append(": ").append(fileData2.get(key1)).append("\n");
            } else if (!(fileData1.containsKey(key1) && fileData2.containsKey(key1))) {
                difference.append("  - ").append(key1).append(": ").append(value1).append("\n");
            }
        }

        for (Map.Entry<String, String> entry1 : fileData2.entrySet()) {
            String key2 = entry1.getKey();
            String value2 = entry1.getValue();

            if (!fileData1.containsKey(key2)) {
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

    protected static Path normalizePath(String path) throws IOException {
        Path normalizeAbsolutePath = Paths.get(path).toAbsolutePath().normalize();

        if (!Files.exists(normalizeAbsolutePath)) {
            throw new IOException("File '" + normalizeAbsolutePath + "' does not exist");
        }

        return normalizeAbsolutePath;
    }

    public static Optional<String> getFileExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }


}
