import org.apache.commons.codec.digest.MurmurHash3;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        BloomFilter filter = new BloomFilter(1000, 1);
        String[] keys = {"A", "B", "C", "D", "E", "F", "G"};
        for(String key : keys) {
            filter.add(key, 1);
        }

        for(String key : keys) {
            System.out.println(filter.exists(key, 1));
        }
        System.out.println(filter.exists("Food", 1));
        System.out.println(filter.exists("key", 1));
        System.out.println(filter.exists("J", 1)); // false positive


        Map<String, Boolean> data_exists = new HashMap<>();
        Map<String, Boolean> data_does_not_exists = new HashMap<>();
        List<String> dataset = new ArrayList<>();

        for(int i = 0; i < 500; i++) {
            UUID uuid = UUID.randomUUID();
            dataset.add(uuid.toString());
            data_exists.put(uuid.toString(), true);
        }

        for(int i = 0; i < 500; i++) {
            UUID uuid = UUID.randomUUID();
            dataset.add(uuid.toString());
            data_does_not_exists.put(uuid.toString(), true);
        }


        for(int j = 1; j <= 100; j++) {

            BloomFilter bigFilter = new BloomFilter(10000, 100);
            for(Map.Entry<String, Boolean> entry : data_exists.entrySet()) {
                bigFilter.add(entry.getKey(), j);
            }

            int falsePositives = 0;
            for(String key : dataset) {
                boolean exists = bigFilter.exists(key, j);
                if(exists && data_does_not_exists.containsKey(key)) {
                    falsePositives ++;
                }
            }

            System.out.println(falsePositives * 100.0/ dataset.size());
        }


    }
}
