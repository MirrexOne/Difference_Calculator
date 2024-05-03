package hexlet.code;


import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import static hexlet.code.Difference.generateDifference;
import static hexlet.code.Parser.normalizePath;
import static hexlet.code.Parser.parseJson;
import static hexlet.code.Parser.parseYaml;

final class Differ {

    private Differ() {
    }

    public static String generate(String pathToFile1, String pathToFile2) throws IOException {
        Optional<String> fileExtension1 = getFileExtension(pathToFile1);
        Optional<String> fileExtension2 = getFileExtension(pathToFile2);

        Path normalizePath1 = normalizePath(pathToFile1);
        Path normalizePath2 = normalizePath(pathToFile2);

        Map<String, Object> typeDataParsed1 = "json".equalsIgnoreCase(fileExtension1.orElseThrow())
                ? parseJson(normalizePath1) : parseYaml(normalizePath1);

        Map<String, Object> typeDataParsed2 = "json".equalsIgnoreCase(fileExtension2.orElseThrow())
                ? parseJson(normalizePath2) : parseYaml(normalizePath2);

        List<Map<String, Object>> treeOfDifference = generateDifference(typeDataParsed1, typeDataParsed2);
        System.out.println(treeOfDifference);
        return "";
    }

    private static Optional<String> getFileExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
}
