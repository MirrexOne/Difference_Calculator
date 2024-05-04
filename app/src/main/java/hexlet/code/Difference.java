package hexlet.code;

import java.util.*;

public class Difference {


    static void generateDifference(Map<String, Object> fileData1, Map<String, Object> fileData2) {
        Map<String, List<Map<String , List<Map<String, Object>>>>> differenceStore = new LinkedHashMap<>();

        fileData1.forEach((key, value1) -> {
            if ((fileData1.containsKey(key) && fileData2.containsKey(key))
                    && (Objects.deepEquals(value1, fileData2.get(key)))) {



            } else if ((fileData1.containsKey(key) && fileData2.containsKey(key))
                    && (!Objects.deepEquals(value1, fileData2.get(key)))) {


            } else if (!(fileData1.containsKey(key) && fileData2.containsKey(key))) {


            }
        });

        fileData2.forEach((key, value) -> {
            if (!fileData1.containsKey(key)) {

            }
        });

    }
}
