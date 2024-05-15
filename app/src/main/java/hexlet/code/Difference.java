package hexlet.code;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

final class Difference {

    private Difference() {
    }

    static List<Map<String, Object>> generateDifference(Map<String, Object> fileData1, Map<String, Object> fileData2) {
        List<Map<String, Object>> differenceStore = new ArrayList<>();
        String keyOfValue = "value";

        Map<String, Object> firstFileSortedData = Difference.sortMap(fileData1);
        Map<String, Object> secondFileSortedData = Difference.sortMap(fileData2);

        firstFileSortedData.forEach((key, value1) -> {
            if ((firstFileSortedData.containsKey(key) && secondFileSortedData.containsKey(key))
                    && (isObjectsEqual(value1, secondFileSortedData.get(key)))) {

                Map<String, Object> unchangedKey = new LinkedHashMap<>();
                unchangedKey.put(key, "unchanged");
                unchangedKey.put(keyOfValue, value1);
                differenceStore.add(unchangedKey);

            } else if ((firstFileSortedData.containsKey(key) && secondFileSortedData.containsKey(key))
                    && (!isObjectsEqual(value1, secondFileSortedData.get(key)))) {

                Map<String, Object> modifiedKey = new LinkedHashMap<>();
                modifiedKey.put(key, "modified");
                modifiedKey.put("value1", value1);
                modifiedKey.put("value2", secondFileSortedData.get(key));
                differenceStore.add(modifiedKey);

            } else if (!(firstFileSortedData.containsKey(key) && secondFileSortedData.containsKey(key))) {

                Map<String, Object> deletedKey = new LinkedHashMap<>();
                deletedKey.put(key, "deleted");
                deletedKey.put(keyOfValue, value1);
                differenceStore.add(deletedKey);
            }
        });

        secondFileSortedData.forEach((key, value) -> {
            if (!firstFileSortedData.containsKey(key)) {

                Map<String, Object> addedKey = new LinkedHashMap<>();
                addedKey.put(key, "added");
                addedKey.put(keyOfValue, value);
                differenceStore.add(addedKey);
            }
        });

        return sortMapsByKey(differenceStore);
    }

    private static boolean isObjectsEqual(Object objectOne, Object objectTwo) {
        if (objectOne == null || objectTwo == null) {
            return objectOne == objectTwo;
        }

        return objectOne.equals(objectTwo);
    }

    private static List<Map<String, Object>> sortMapsByKey(List<Map<String, Object>> listMaps) {
        List<Map<String, Object>> sortedList = new ArrayList<>(listMaps);

        sortedList.sort(Comparator.comparing(map -> map.keySet().toString()));

        return sortedList;
    }

    private static Map<String, Object> sortMap(Map<String, Object> unsortedMap) {
        Map<String, Object> sortedParsedMap = new LinkedHashMap<>();
        unsortedMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(el -> sortedParsedMap.put(el.getKey(), el.getValue()));
        return sortedParsedMap;
    }
}
