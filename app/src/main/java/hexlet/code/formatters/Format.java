package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Map;

public abstract class Format {
    abstract public String outputFormatting(List<Map<String, Object>> differenceTree) throws JsonProcessingException;
}
