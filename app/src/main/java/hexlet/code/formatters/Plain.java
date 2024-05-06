package hexlet.code.formatters;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Plain extends Format {
    @Override
    public String outputFormatting(List<Map<String, Object>> differenceTree) {
        StringBuilder plain = new StringBuilder("\n");

        for (Map<String, Object> entry : differenceTree) {
            Set<Map.Entry<String, Object>> entries = entry.entrySet();

            for (Map.Entry<String, Object> pairs : entries) {
                String key = pairs.getKey();
                Object value = pairs.getValue();

                if ("Modified key".equals(value)) {
                    Object valueBefore = changeRenderingValue(entry.get("value before"));
                    Object valueAfter = changeRenderingValue(entry.get("value after"));

                    plain.append("Property").append(" '").append(key).append("' ").append("was updated. ")
                            .append("From ").append(valueBefore).append(" to ").append(valueAfter).append("\n");

                } else if ("Added key".equals(value)) {
                    Object addedValue = changeRenderingValue(entry.get("added value"));

                    plain.append("Property").append(" '").append(key).append("' ").append("was added with value: ")
                            .append(addedValue).append("\n");

                } else if ("Deleted key".equals(value)) {

                    plain.append("Property").append(" '").append(key).append("' ").append("was removed").append("\n");
                }
            }

        }

        return plain.toString();
    }

    private String changeRenderingValue(Object value) {
        boolean isComplex = isComplexValue(value);
        String valueRendering;

        if (isComplex) {
            valueRendering = "[complex value]";
        } else if (value instanceof String) {
            valueRendering = "'" + value + "'";
        } else {
            valueRendering = String.valueOf(value);
        }

        return valueRendering;
    }

    private boolean isComplexValue(Object value) {
        return value instanceof List<?> || value instanceof Map<?,?>;
    }
}
