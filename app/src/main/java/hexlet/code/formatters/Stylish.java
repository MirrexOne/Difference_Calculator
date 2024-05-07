package hexlet.code.formatters;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Stylish extends Format {

    @Override
    public String outputFormatting(List<Map<String, Object>> differenceTree) {
        String indents = " ".repeat(4);
        final String KEY_OF_VALUE = "value";

        StringBuilder stylish = new StringBuilder("\n");
        stylish.append("{\n");

        for (Map<String, Object> entry : differenceTree) {
            Set<Map.Entry<String, Object>> entries = entry.entrySet();

            for (Map.Entry<String, Object> pairs : entries) {
                String key = pairs.getKey();
                Object value = pairs.getValue();
                if ("unchanged".equals(value)) {
                    stylish.append(indents).append("  ").append(key).append(": ")
                            .append(entry.get(KEY_OF_VALUE)).append("\n");
                } else if ("modified".equals(value)) {
                    stylish.append(indents).append("- ").append(key).append(": ")
                            .append(entry.get("value1")).append("\n");
                    stylish.append(indents).append("+ ").append(key).append(": ")
                            .append(entry.get("value2")).append("\n");

                } else if ("added".equals(value)) {
                    stylish.append(indents).append("+ ").append(key).append(": ")
                            .append(entry.get(KEY_OF_VALUE)).append("\n");

                } else if ("deleted".equals(value)) {
                    stylish.append(indents).append("- ").append(key).append(": ")
                            .append(entry.get(KEY_OF_VALUE)).append("\n");

                }
            }
        }

        stylish.append("}\n");
        return stylish.toString();
    }
}
