package hexlet.code.formatters;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Plain extends Format {
    @Override
    public String outputFormatting(List<Map<String, Object>> differenceTree) {
        StringBuilder plain = new StringBuilder("\n");
        String definitionOfKey = "Property";

        for (Map<String, Object> entry : differenceTree) {
            Set<Map.Entry<String, Object>> entries = entry.entrySet();

            for (Map.Entry<String, Object> pairs : entries) {
                String key = pairs.getKey();
                Object value = pairs.getValue();

                if ("modified".equals(value)) {
                    Object valueBefore = changeRenderingValue(entry.get("value1"));
                    Object valueAfter = changeRenderingValue(entry.get("value2"));

                    plain.append(definitionOfKey).append(" '").append(key).append("' ").append("was updated. ")
                            .append("From ").append(valueBefore).append(" to ").append(valueAfter).append("\n");

                } else if ("added".equals(value)) {
                    Object addedValue = changeRenderingValue(entry.get("value"));

                    plain.append(definitionOfKey).append(" '").append(key).append("' ")
                            .append("was added with value: ").append(addedValue).append("\n");

                } else if ("deleted".equals(value)) {

                    plain.append(definitionOfKey).append(" '").append(key).append("' ")
                            .append("was removed").append("\n");
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
        return (Objects.nonNull(value)) && (value instanceof List<?> || value instanceof Map<?, ?>);
    }
}
