import org.apache.commons.codec.digest.MurmurHash3;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class BloomFilter {
    byte[] filter;
    int size;
    int[] seeds;
    int numberOfSeeds;
    BloomFilter(int size, int numberOfSeeds) {
        this.size = size;
        filter = new byte[size / Byte.SIZE];
        seeds = new int[numberOfSeeds];
        this.numberOfSeeds = numberOfSeeds;
        Random random = new Random();
        for(int i = 0; i < numberOfSeeds; i++)
            seeds[i] = random.nextInt();
    }

    private int hashKey(String key, int idx) {
        byte[] data = key.getBytes(StandardCharsets.UTF_8);
        return Math.abs(MurmurHash3.hash32x86(data, 0, data.length, seeds[idx % numberOfSeeds])) % size;
    }

    public void add(String key, int numberOfHashes) {
        for(int i = 0; i < numberOfHashes; i++) {
            int hash = hashKey(key, i);
            filter[hash / 8] |= (byte)(1 << (hash % 8));
        }
    }

    public boolean exists(String key, int numberOfHashes) {
        for (int i = 0; i < numberOfHashes; i++) {
            int hash = hashKey(key, i);
            if (((filter[hash / 8] & (byte)(1 << (hash % 8))) == 0)) {
                return false;
            }
        }
        return true;
    }

}
