package hexlet.code;

import hexlet.code.formatters.Format;
import hexlet.code.formatters.Formatter;
import hexlet.code.parsers.Parser;
import hexlet.code.parsers.ParserFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static hexlet.code.FileSystem.getFileExtension;
import static hexlet.code.FileSystem.normalizePath;
import static hexlet.code.FileSystem.retrieveFileData;

class Differ {

    private Differ() {
    }

    public static String generate(String pathToFile1, String pathToFile2, String outputFormat) throws IOException {
        Path normalizedFirstFilePath = normalizePath(pathToFile1);
        Path normalizedSecondFilePath = normalizePath(pathToFile2);

        Optional<String> firstExtractedExtension = getFileExtension(pathToFile1);
        Optional<String> secondExtractedExtension = getFileExtension(pathToFile2);

        String firstFileExtension = firstExtractedExtension.map(String::toString).orElse(" ");
        String secondFileExtension = secondExtractedExtension.map(String::toString).orElse(" ");

        Parser firstFileParser = ParserFactory.getParser(firstFileExtension);
        Parser secondFileParser = ParserFactory.getParser(secondFileExtension);

        File firstFileRetrievedData = retrieveFileData(normalizedFirstFilePath);
        File secondFileRetrievedData = retrieveFileData(normalizedSecondFilePath);

        Map<String, Object> firstFileParsedData = firstFileParser.parse(firstFileRetrievedData);
        Map<String, Object> secondFileParsedData = secondFileParser.parse(secondFileRetrievedData);

        Map<String, Object> firstFileSortedData = Maps.sortMap(firstFileParsedData);
        Map<String, Object> secondFileSortedData = Maps.sortMap(secondFileParsedData);

        List<Map<String, Object>> differenceTree = Difference.generateDifference(firstFileSortedData, secondFileSortedData);

        List<Map<String, Object>> sortedDifferenceTree = Maps.sortMapsByKey(differenceTree);

        Format requiredRenderingFormat = Formatter.getFormat(outputFormat);

        return requiredRenderingFormat.outputFormatting(sortedDifferenceTree);
    }
}
