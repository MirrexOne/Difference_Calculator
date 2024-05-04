package hexlet.code.parsers;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface Parser {
    Map<String, Object> parse(File retrievedFileData) throws IOException;
}
