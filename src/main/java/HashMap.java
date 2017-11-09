import java.io.Serializable;

public class HashMap implements IDict, Serializable {

    private int size, max_size;
    private Entry[] table;

    public static long hashFun(String str) {
        long hash = 7;
        for (char ch : str.toCharArray()) {
            hash = hash * 31 + ch;
        }
        return hash;
    }

    public HashMap() {
        this.size = 0;
        this.max_size = 8;
        this.table = new Entry[8];
    }

    private void extendTable() {
        max_size *= 2;
        Entry[] newTable = new Entry[max_size];
        System.arraycopy(table, 0, newTable, 0, size);
        table = newTable;
    }

    public int insert(String str) {
        if (size >= max_size) {
            extendTable();
        }
        int key;
        if ((key = search(str)) != -1) {
            table[key].frequency++;
            return key;
        }
        long hash = hashFun(str);
        key = (int) (hash & (max_size - 1)); // El operador binario AND es como módulo pero más bacán.
        Entry mapped = table[key];

        while (mapped != null) {
            key = ++key % max_size; // Linear Probing
            mapped = table[key];
        }
        Entry entry = new Entry(hash, key, str);
        table[key] = entry;
        size++;
        return key;

    }

    public int search(String str) {
        long hash = hashFun(str);
        int key = (int) (hash & (max_size - 1));

        Entry mapped;
        for (int i = 0; i < max_size; i++) {
            mapped = table[key];
            if (mapped != null && mapped.value.equals(str)) return key;
            key = ++key % max_size;
        }
        return -1;
    }

    @Override
    public int frequency(String word) {
        int key = search(word);
        if (key == -1) return 0;
        return table[key].frequency;
    }
}

class Entry {
    public long hash;
    public int key;
    public String value;
    public int frequency;

    public Entry(long hash, int key, String value) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        frequency = 1;
    }
}
