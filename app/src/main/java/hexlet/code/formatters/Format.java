package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Map;

public abstract class Format {

    public abstract String outputFormatting(List<Map<String, Object>> differenceTree) throws JsonProcessingException;

    public static Format getFormat(String format) {
        return switch (format) {
            case "plain" -> new Plain();
            case "json" -> new JsonFormat();
            default -> new Stylish();
        };
    }
}
