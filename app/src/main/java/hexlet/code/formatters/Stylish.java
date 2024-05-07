package hexlet.code.formatters;


import java.util.List;
import java.util.Map;
import java.util.Set;

public class Stylish extends Format {

    @Override
    public String outputFormatting(List<Map<String, Object>> differenceTree) {
        String indents = " ".repeat(4);

        StringBuilder stylish = new StringBuilder("\n");
        stylish.append("{" + "\n");


        for (Map<String, Object> entry : differenceTree) {
            Set<Map.Entry<String, Object>> entries = entry.entrySet();

            for (Map.Entry<String, Object> pairs : entries) {
                String key = pairs.getKey();
                Object value = pairs.getValue();
                if ("Unchanged key".equals(value)) {
                    stylish.append(indents).append("  ").append(key).append(": ")
                            .append(entry.get("unchanged value")).append("\n");
                } else if ("Modified key".equals(value)) {
                    stylish.append(indents).append("- ").append(key)
                            .append(": ").append(entry.get("value before")).append("\n");
                    stylish.append(indents).append("+ ").append(key)
                            .append(": ").append(entry.get("value after")).append("\n");

                } else if ("Added key".equals(value)) {
                    stylish.append(indents)
                            .append("+ ").append(key).append(": ").append(entry.get("added value")).append("\n");

                } else if ("Deleted key".equals(value)) {
                    stylish.append(indents).append("- ").append(key)
                            .append(": ").append(entry.get("deleted value")).append("\n");

                }
            }
        }

        stylish.append("}").append("\n");
        return stylish.toString();
    }
}
