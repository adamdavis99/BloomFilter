import java.util.*;

public class Main {
    public static void main(String[] args) {
        BloomFilter filter = new BloomFilter(1000);
        String[] keys = {"A", "B", "C", "D", "E", "F", "G"};
        for(String key : keys) {
            filter.add(key);
        }

        for(String key : keys) {
            System.out.println(filter.exists(key));
        }
        System.out.println(filter.exists("Food"));
        System.out.println(filter.exists("key"));
        System.out.println(filter.exists("J")); // false positive


        Map<String, Boolean> data_exists = new HashMap<>();
        Map<String, Boolean> data_does_not_exists = new HashMap<>();
        List<String> dataset = new ArrayList<>();

        for(int j = 1000; j <= 20000; j += 32) {
            dataset.clear();
            data_exists.clear();
            data_does_not_exists.clear();

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

            BloomFilter bigFilter = new BloomFilter(j);
            for(Map.Entry<String, Boolean> entry : data_exists.entrySet()) {
                bigFilter.add(entry.getKey());
            }

            int falsePositives = 0;
            for(String key : dataset) {
                boolean exists = bigFilter.exists(key);
                if(exists && data_does_not_exists.containsKey(key)) {
                    falsePositives ++;
                }
            }

            System.out.println(falsePositives * 100.0/ dataset.size());
        }


    }
}
