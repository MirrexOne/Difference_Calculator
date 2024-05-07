package hexlet.code;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

class Maps {

    private Maps() {
    }

    public static List<Map<String, Object>> sortMapsByKey(List<Map<String, Object>> listMaps) {
        List<Map<String, Object>> sortedList = new ArrayList<>(listMaps);

        sortedList.sort(Comparator.comparing(map -> map.keySet().toString()));

        return sortedList;
    }

    public static Map<String, Object> sortMap(Map<String, Object> unsortedMap) {
        Map<String, Object> sortedParsedMap = new LinkedHashMap<>();
        unsortedMap.entrySet().stream()
                .sorted(Entry.comparingByKey())
                .forEachOrdered(el -> sortedParsedMap.put(el.getKey(), el.getValue()));
        return sortedParsedMap;
    }
}
