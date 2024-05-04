package hexlet.code;


import hexlet.code.formatters.Formatter;
import hexlet.code.formatters.Stylish;
import hexlet.code.parsers.Parser;
import hexlet.code.parsers.ParserFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;


final class Differ {

    private Differ() {
    }

    public static String generate(String pathToFile1, String pathToFile2) throws IOException {
        Path normalizedPath1 = normalizePath(pathToFile1);
        Path normalizedPath2 = normalizePath(pathToFile2);

        Optional<String> file1Extension = getFileExtension(pathToFile1);
        Optional<String> file2Extension = getFileExtension(pathToFile2);

        String extension1 = file1Extension.map(String::toString).orElse(" ");
        String extension2 = file2Extension.map(String::toString).orElse(" ");

        Parser parser1 = ParserFactory.getParser(extension1);
        Parser parser2 = ParserFactory.getParser(extension2);

        File fileData1 = retrieveFileData(normalizedPath1);
        File fileData2 = retrieveFileData(normalizedPath2);

        Map<String, Object> parsedData1 = parser1.parse(fileData1);
        Map<String, Object> parsedData2 = parser2.parse(fileData2);

        Map<String, Object> sortedData1 = sortMap(parsedData1);
        Map<String, Object> sortedData2 = sortMap(parsedData2);

        List<Map<String, Object>> maps = Difference.generateDifference(sortedData1, sortedData2);

        Stylish stylish = new Stylish();
        String s = stylish.outputFormatting(maps);
        System.out.println(s);
        return "";



    }

    private static File retrieveFileData(Path pathToFile) {
        return pathToFile.toFile();
    }

    private static Path normalizePath(String path) throws IOException {
        Path normalizeAbsolutePath = Paths.get(path).toAbsolutePath().normalize();

        if (!Files.exists(normalizeAbsolutePath)) {
            throw new IOException("File '" + normalizeAbsolutePath + "' does not exist");
        }

        return normalizeAbsolutePath;
    }

    private static Optional<String> getFileExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    private static Map<String, Object> sortMap(Map<String, Object> unsortedMap) {
        Map<String, Object> sortedParsedMap = new LinkedHashMap<>();
        unsortedMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(el -> sortedParsedMap.put(el.getKey(), el.getValue()));
        return sortedParsedMap;
    }
}
