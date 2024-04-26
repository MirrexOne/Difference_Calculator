package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Differ {


    public static String generate(String pathToFile1, String pathToFile2) throws IOException {
        Path normalizePath1 = normalizePath(pathToFile1);
        Path normalizePath2 = normalizePath(pathToFile2);
        Map<String, String> parsedJson1 = parse(normalizePath1);
        Map<String, String> parsedJson2 = parse(normalizePath2);

        System.out.println(parsedJson1);
        System.out.println(parsedJson2);
        return "";

    }

    public static Map<String, String> parse(Path pathToFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, String>> specifiedType = new TypeReference<>() {};

        File createdFile = pathToFile.toFile();

        return mapper.readValue(createdFile, specifiedType);
    }

    public static Path normalizePath(String path) throws IOException {
        Path normalizeAbsolutePath = Paths.get(path).toAbsolutePath().normalize();

        if (!Files.exists(normalizeAbsolutePath)) {
            throw new IOException("File '" + normalizeAbsolutePath + "' does not exist");
        }

        return normalizeAbsolutePath;
    }

}
