package hexlet.code.parsers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Json implements Parser {

    @Override
    public Map<String, Object> parse(File retrievedFileData) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModules(new JavaTimeModule());
        ObjectReader objectReader = objectMapper.reader();

        return objectReader.forType(Map.class).readValue(retrievedFileData);
    }
}
