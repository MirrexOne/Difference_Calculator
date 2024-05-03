package hexlet.code;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.util.*;

import static hexlet.code.Parser.parseJson;
import static hexlet.code.Parser.parseYaml;

public class Differ {

    public static String generate(String pathToFile1, String pathToFile2) throws IOException {
        Optional<String> fileExtension1 = getFileExtension(pathToFile1);
        Optional<String> fileExtension2 = getFileExtension(pathToFile2);

        Path normalizePath1 = normalizePath(pathToFile1);
        Path normalizePath2 = normalizePath(pathToFile2);

        Map<String, Object> typeDataParsed1 = "json".equalsIgnoreCase(fileExtension1.orElseThrow())
                ? parseJson(normalizePath1) : parseYaml(normalizePath1);

        Map<String, Object> typeDataParsed2 = "json".equalsIgnoreCase(fileExtension2.orElseThrow())
                ? parseJson(normalizePath2) : parseYaml(normalizePath2);

        System.out.println("Parsed file 1: " + typeDataParsed1);
        System.out.println("Parsed file 2: " + typeDataParsed2);
        System.out.println();

        Map<String, Object> sortParsedData1 = sortMap(typeDataParsed1);
        Map<String, Object> sortParsedData2 = sortMap(typeDataParsed2);
        System.out.println("Sorted file 1: " + sortParsedData1);
        System.out.println("Sorted file 2: " + sortParsedData2);


        generateDifference(sortParsedData1, sortParsedData2);
        return "";
    }

    private static void generateDifference(Map<String, Object> fileData1, Map<String, Object> fileData2) {
        System.out.println();
        fileData1.forEach((el1, el2) -> {
            System.out.print("Key: " + el1);
            System.out.println(" Value: " + el2);
        });
        System.out.println();
        fileData2.forEach((el1, el2) -> {
            System.out.print("Key: " + el1);
            System.out.println(" Value: " + el2);
        });

        List<Map<String, Object>> differenceStore = new ArrayList<>();
        int  keysCollection1 = fileData1.keySet().size();
        int  keysCollection2 = fileData2.keySet().size();
        Set<String> keysFile1 = fileData1.keySet();
        Set<String> keysFile2 = fileData2.keySet();



        fileData1.forEach((key, value1) -> {
            if ((fileData1.containsKey(key) && fileData2.containsKey(key))
                    && (Objects.deepEquals(value1, fileData2.get(key)))) {

            Map<String, Object> unchangedKey = new LinkedHashMap<>();
            unchangedKey.put("Unchanged key", key);
            differenceStore.add(unchangedKey);


            } else if ((fileData1.containsKey(key) && fileData2.containsKey(key))
                    && (!Objects.deepEquals(value1, fileData2.get(key)))) {

                Map<String, Object> modifiedKey = new LinkedHashMap<>();
                modifiedKey.put("Modified key", key);
                differenceStore.add(modifiedKey);

            } else if (!(fileData1.containsKey(key) && fileData2.containsKey(key))) {

                Map<String, Object> deletedKey = new LinkedHashMap<>();
                deletedKey.put("Deleted key", key);
                differenceStore.add(deletedKey);
            }
        });

        fileData2.forEach((key, value) -> {
            if (!fileData1.containsKey(key)) {

                Map<String, Object> addedKey = new LinkedHashMap<>();
                addedKey.put("Added key", key);
                differenceStore.add(addedKey);
            }
        });

        differenceStore.forEach((el) -> {
            System.out.println(el);
            System.out.println(el.get(el));
        });
    }


    protected static Map<String, Object> sortMap(Map<String, Object> unsortedMap) {
        Map<String, Object> sortedParsedMap = new LinkedHashMap<>();
        unsortedMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(el -> sortedParsedMap.put(el.getKey(), el.getValue()));
        return sortedParsedMap;

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
