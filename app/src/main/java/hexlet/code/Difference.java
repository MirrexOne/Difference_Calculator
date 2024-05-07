package hexlet.code;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

final class Difference {

    private Difference() {
    }

    static List<Map<String, Object>> generateDifference(Map<String, Object> fileData1, Map<String, Object> fileData2) {
        List<Map<String, Object>> differenceStore = new ArrayList<>();
        final String KEY_OF_VALUE = "value";

        fileData1.forEach((key, value1) -> {
            if ((fileData1.containsKey(key) && fileData2.containsKey(key))
                    && (Objects.deepEquals(value1, fileData2.get(key)))) {

                Map<String, Object> unchangedKey = new LinkedHashMap<>();
                unchangedKey.put(key, "unchanged");
                unchangedKey.put(KEY_OF_VALUE, value1);
                differenceStore.add(unchangedKey);

            } else if ((fileData1.containsKey(key) && fileData2.containsKey(key))
                    && (!Objects.deepEquals(value1, fileData2.get(key)))) {

                Map<String, Object> modifiedKey = new LinkedHashMap<>();
                modifiedKey.put(key, "modified");
                modifiedKey.put("value1", value1);
                modifiedKey.put("value2", fileData2.get(key));
                differenceStore.add(modifiedKey);

            } else if (!(fileData1.containsKey(key) && fileData2.containsKey(key))) {

                Map<String, Object> deletedKey = new LinkedHashMap<>();
                deletedKey.put(key, "deleted");
                deletedKey.put(KEY_OF_VALUE, value1);
                differenceStore.add(deletedKey);
            }
        });

        fileData2.forEach((key, value) -> {
            if (!fileData1.containsKey(key)) {

                Map<String, Object> addedKey = new LinkedHashMap<>();
                addedKey.put(key, "added");
                addedKey.put(KEY_OF_VALUE, value);
                differenceStore.add(addedKey);
            }
        });

        return sortMapsByKey(differenceStore);
    }

    private static List<Map<String, Object>> sortMapsByKey(List<Map<String, Object>> listMaps) {
        List<Map<String, Object>> sortedList = new ArrayList<>(listMaps);

        sortedList.sort(Comparator.comparing(map -> map.keySet().toString()));

        return sortedList;
    }
}
