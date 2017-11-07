import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SimilarityTest {

    private IDict dict;

    int similarity(String text1, String text2, Class<IDict> Dict) throws IllegalAccessException, InstantiationException {
        String[] words1 = text1.split(" ");
        String[] words2 = text2.split(" ");
        IDict d1 = Dict.newInstance();
        IDict d2 = Dict.newInstance();
        Set<String> wordSet = new HashSet<>(Arrays.asList(words1));
        wordSet.addAll(Arrays.asList(words2));
        for (String word : words1) {
            d1.insert(word);
        }
        for (String word : words2) {
            d2.insert(word);
        }

        int sum = 0;
        for (String word : wordSet) {
            int freq1 = d1.frequency(word);
            int freq2 = d2.frequency(word);
            sum += Math.abs(freq1 - freq2);
        }
        return 1 - (sum / wordSet.size());
    }

    void main(String[] args) {
    }
}
