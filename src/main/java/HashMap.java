import java.io.Serializable;
import java.util.Random;

public class HashMap implements IDict, Serializable {

    /**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private int size, max_size;
    private Entry[] table;

    /*
        djb2 by Dan Bernstein
     */
    public static long hashFun(String str) {
        long hash = 5381;
        for (char ch : str.toCharArray()) {
            hash = ((hash << 5) + hash) + ch;
        }
        return hash;
    }

    public HashMap() {
        this.size = 0;
        this.max_size = 8;
        this.table = new Entry[8];
    }

    private int linearProbing(int key, String str) {
        Entry mapped = table[key];
        if (mapped == null) return key;
        while (mapped != null) {
            if (mapped.value.equals(str)){
                table[key].frequency++;
                return -1;
            }
            key = (key + 1) % max_size;
            mapped = table[key];
        }
        return key;
    }

    /*
        Function tomada de la implementacion de diccionarios de python:
        dictobject.c
     */
    private int randomProbing(int key, String str) {
        Entry mapped = table[key];
        int perturb = key;
        while (mapped != null) {
            if (mapped.value.equals(str)) {
                table[key].frequency++;
                return -1;
            }
            perturb >>= 5;
            key = max_size & ((key << 2) + key + perturb + 1);
            mapped = table[key];
        }
        return key;
    }

    private void extendTable() {
        max_size *= 2;
        Entry[] newTable = new Entry[max_size];
        for (Entry e : table) {
            if (e != null) {
                int newKey = (int) (e.hash & (max_size - 1));
                e.key = newKey;
                if (newTable[newKey] != null) {
                    while (newTable[newKey] != null) {
                        newKey = (newKey + 1) % max_size;
                    }
                }
                newTable[newKey] = e;
            }
        }
        table = newTable;
    }

    public int insert(String str) {
        if (size >= max_size) {
            extendTable();
        }
        long hash = hashFun(str);
        int key = (int) (hash & (max_size - 1)); // El operador binario AND es como módulo pero más bacán.
        if ((key = linearProbing(key, str)) == -1) {
            return key;
        }
        Entry entry = new Entry(hash, key, str);
        table[key] = entry;
        size++;
        return key;

    }

    public int search(String str) {
        long hash = hashFun(str);
        int key = (int) (hash & (max_size - 1));

        Entry mapped = table[key];
        int i = 0;
        while (mapped != null) {
            if (mapped.value.equals(str)) return key;
            key = (key + 1) % max_size;
            mapped = table[key];
            if (++i >= max_size) break;
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

class Entry implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
