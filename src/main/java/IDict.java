import com.sun.tools.javac.util.Pair;

public interface IDict {
    int insert(String str);
    int search(String str);
    int frequency(String word);
}
