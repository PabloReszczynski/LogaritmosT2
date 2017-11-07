import java.util.ArrayList;

public class PatriciaTree implements IDict {

    private ArrayList<PatriciaTree> children;
    private char[] label;

    private enum SearchReasons {
        Found,
        NoChildrenWithNextChar,
        PatternExhausted,
        LeafReached
    }

    public PatriciaTree() {
        children = new ArrayList<PatriciaTree>();
        label = "".toCharArray();
    }

    public PatriciaTree(String label) {
        children = new ArrayList<PatriciaTree>();
        this.label = label.toCharArray();
    }

    @Override
    public int insert(String str) {
        // TODO
        SearchReasons reason = search(str.toCharArray(), 0);
        switch (reason) {
            case NoChildrenWithNextChar:
                break;
            case PatternExhausted:
                break;
            case LeafReached:
                break;
        }
        return 1;
    }

    @Override
    public int search(String str) {
        return (search(str.toCharArray(), 0) == SearchReasons.Found) ? 1 : 0;
    }

    @Override
    public int frequency(String word) {
        return 0;
    }

    private SearchReasons search(char[] word, int pos) {
        // Si la etiqueta es mas larga de lo que queda del String, entonces no está aquí.
        if (word.length - pos < label.length) return SearchReasons.PatternExhausted;

        // Buscamos si hay un match con la etiqueta
        for (int i = 0; i < label.length; i++) {
            if (word[pos + i] != label[i]) return SearchReasons.NoChildrenWithNextChar;
        }

        // Actualizamos la posicion
        int newPos = pos + label.length - 1;

        // Si llegamos al final del String, lo encontramos
        if (newPos == word.length) return SearchReasons.Found;

        // Si no hay hijos
        if (children.isEmpty()) return SearchReasons.LeafReached;

        // Si no, buscamos en los hijos
        for (PatriciaTree child : children) {
            if (child.search(word, newPos) == SearchReasons.Found) return SearchReasons.Found;
        }

        // Si no lo encontramos en ningun hijo
        return SearchReasons.NoChildrenWithNextChar;
    }
}
