package hexlet.code;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

final class Difference {

    private Difference() {
    }

    static List<Map<String, Object>> generateDifference(Map<String, Object> fileData1, Map<String, Object> fileData2) {
        List<Map<String, Object>> differenceStore = new ArrayList<>();

        fileData1.forEach((key, value1) -> {
            if ((fileData1.containsKey(key) && fileData2.containsKey(key))
                    && (Objects.deepEquals(value1, fileData2.get(key)))) {

                Map<String, Object> unchangedKey = new LinkedHashMap<>();
                unchangedKey.put("Unchanged key", key);
                differenceStore.add(unchangedKey);


            } else if ((fileData1.containsKey(key) && fileData2.containsKey(key))
                    && (!Objects.deepEquals(value1, fileData2.get(key)))) {

                Map<String, Object> modifiedKey = new LinkedHashMap<>();
                modifiedKey.put("Modified key", key);
                differenceStore.add(modifiedKey);

            } else if (!(fileData1.containsKey(key) && fileData2.containsKey(key))) {

                Map<String, Object> deletedKey = new LinkedHashMap<>();
                deletedKey.put("Deleted key", key);
                differenceStore.add(deletedKey);
            }
        });

        fileData2.forEach((key, value) -> {
            if (!fileData1.containsKey(key)) {

                Map<String, Object> addedKey = new LinkedHashMap<>();
                addedKey.put("Added key", key);
                differenceStore.add(addedKey);
            }
        });

        return differenceStore;
    }
}
