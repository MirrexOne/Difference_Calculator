package hexlet.code.formatters;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Stylish extends Format {
    public final String outputFormatting(List<Map<String, Object>> differenceTree) {
        String indents = " ".repeat(2);
        String keyOfValue = "value";

        StringBuilder stylish = new StringBuilder();
        stylish.append("{\n");

        for (Map<String, Object> entry : differenceTree) {
            Set<Map.Entry<String, Object>> entries = entry.entrySet();

            for (Map.Entry<String, Object> pairs : entries) {
                String key = pairs.getKey();
                Object value = pairs.getValue();

                if ("unchanged".equals(value)) {

                    stylish.append(indents).append("  ").append(key).append(": ")
                            .append(entry.get(keyOfValue)).append("\n");

                } else if ("modified".equals(value)) {

                    stylish.append(indents).append("- ").append(key).append(": ")
                            .append(entry.get("value1")).append("\n");
                    stylish.append(indents).append("+ ").append(key).append(": ")
                            .append(entry.get("value2")).append("\n");

                } else if ("added".equals(value)) {

                    stylish.append(indents).append("+ ").append(key).append(": ")
                            .append(entry.get(keyOfValue)).append("\n");

                } else if ("deleted".equals(value)) {

                    stylish.append(indents).append("- ").append(key).append(": ")
                            .append(entry.get(keyOfValue)).append("\n");
                }
            }
        }

        stylish.append("}");
        return stylish.toString();
    }
}
