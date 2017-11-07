import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Carga el archivo 'alice.txt' en memoria y separa las palabras para insertarlas en un diccionario.
 */
public class TextReader {
    public static String[] open() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("sherlock.txt"));
        ArrayList<String> words = new ArrayList<String>();
        String line;
        while ((line = reader.readLine()) != null) {
            words.addAll(Arrays.asList(line.split(" ")));
        }
        return words.toArray(new String[0]);
    }

    public static void fillDict(IDict dict, String[] words) {
        for (String word : words) {
            dict.insert(word);
        }
    }

    public static void main(String[] args) throws IOException {
        HashMap map = new HashMap();
        String[] words = open();
        Instant time1 = Instant.now();
        fillDict(map, words);
        System.out.format(
                "Filled dict with %d words in %d milliseconds",
                words.length,
                Duration.between(time1, Instant.now()).toMillis());
    }
}
