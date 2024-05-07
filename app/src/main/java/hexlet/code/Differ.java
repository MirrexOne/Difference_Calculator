package hexlet.code;

import hexlet.code.formatters.Format;
import hexlet.code.parsers.Parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class Differ {

    private Differ() {
    }

    public static String generate(String pathToFile1, String pathToFile2, String outputFormat) throws IOException {
        Path normalizedFirstFilePath = normalizePath(pathToFile1);
        Path normalizedSecondFilePath = normalizePath(pathToFile2);

        Optional<String> firstExtractedExtension = getFileExtension(pathToFile1);
        Optional<String> secondExtractedExtension = getFileExtension(pathToFile2);

        String firstFileExtension = firstExtractedExtension.map(String::toString).orElse(" ");
        String secondFileExtension = secondExtractedExtension.map(String::toString).orElse(" ");

        Parser firstFileParser = Parser.getParser(firstFileExtension);
        Parser secondFileParser = Parser.getParser(secondFileExtension);

        File firstFileRetrievedData = retrieveFileData(normalizedFirstFilePath);
        File secondFileRetrievedData = retrieveFileData(normalizedSecondFilePath);

        Map<String, Object> firstFileParsedData = firstFileParser.parse(firstFileRetrievedData);
        Map<String, Object> secondFileParsedData = secondFileParser.parse(secondFileRetrievedData);

        Map<String, Object> firstFileSortedData = sortMap(firstFileParsedData);
        Map<String, Object> secondFileSortedData = sortMap(secondFileParsedData);

        List<Map<String, Object>> tree = Difference.generateDifference(firstFileSortedData, secondFileSortedData);

        Format requiredRenderingFormat = Format.getFormat(outputFormat);

        return requiredRenderingFormat.outputFormatting(tree);
    }

    public static String generate(String pathToFile1, String pathToFile2) throws IOException {
        return generate(pathToFile1, pathToFile2, "stylish");
    }

    private static File retrieveFileData(Path pathToFile) {
        return pathToFile.toFile();
    }

    private static Path normalizePath(String path) throws IOException {
        Path normalizeAbsolutePath = Paths.get(path).toAbsolutePath().normalize();

        if (!Files.exists(normalizeAbsolutePath)) {
            throw new IOException("File '" + normalizeAbsolutePath + "' does not found");
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
