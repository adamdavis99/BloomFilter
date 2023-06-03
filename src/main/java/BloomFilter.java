import org.apache.commons.codec.digest.MurmurHash3;

import java.nio.charset.StandardCharsets;

public class BloomFilter {
    int size;
    byte[] filter;
    BloomFilter(int size) {
        filter = new byte[size / Byte.SIZE];
        this.size = size;
    }

    public void add(String key) {
        int hash = Math.abs(MurmurHash3.hash32x86(key.getBytes(StandardCharsets.UTF_8))) % size;
        filter[hash / 8] |= (byte)(1 << (hash % 8));
    }

    public boolean exists(String key) {
        int hash = Math.abs(MurmurHash3.hash32x86(key.getBytes(StandardCharsets.UTF_8))) % size;
       // System.out.println(key + " " + hash);
        return ((filter[hash / 8] & (byte)(1 << (hash % 8))) != 0);
    }

}
