package hexlet.code.parsers;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public abstract class Parser {

    public abstract Map<String, Object> parse(File retrievedFileData) throws IOException;

    public static Parser getParser(String fileExtension) {
        return "json".equals(fileExtension) ? new Json() : new Yaml();
    }
}
