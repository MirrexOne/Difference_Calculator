package hexlet.code;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Difference {

    private Difference() {
    }

    static List<Map<String, Object>> generateDifference(Map<String, Object> fileData1, Map<String, Object> fileData2) {
        List<Map<String, Object>> differenceStore = new ArrayList<>();

        fileData1.forEach((key, value1) -> {
            if ((fileData1.containsKey(key) && fileData2.containsKey(key))
                    && (Objects.deepEquals(value1, fileData2.get(key)))) {

                Map<String, Object> unchangedKey = new HashMap<>();
                unchangedKey.put(key, "Unchanged key");
                unchangedKey.put("unchanged value", value1);
                differenceStore.add(unchangedKey);


            } else if ((fileData1.containsKey(key) && fileData2.containsKey(key))
                    && (!Objects.deepEquals(value1, fileData2.get(key)))) {

                Map<String, Object> modifiedKey = new HashMap<>();
                modifiedKey.put(key,"Modified key");
                modifiedKey.put("value before", value1);
                modifiedKey.put("value after", fileData2.get(key));
                differenceStore.add(modifiedKey);

            } else if (!(fileData1.containsKey(key) && fileData2.containsKey(key))) {

                Map<String, Object> deletedKey = new HashMap<>();
                deletedKey.put(key, "Deleted key");
                deletedKey.put("deleted value", value1);
                differenceStore.add(deletedKey);
            }
        });

        fileData2.forEach((key, value) -> {
            if (!fileData1.containsKey(key)) {

                Map<String, Object> addedKey = new HashMap<>();
                addedKey.put(key, "Added key");
                addedKey.put("added value", value);
                differenceStore.add(addedKey);
            }
        });

        return differenceStore;
    }

}
