import java.util.ArrayList;

public class PatriciaTree implements IDict {

	private ArrayList<PatriciaTree> children;
    private char[] label;
    PatriciaTree root;

    public PatriciaTree() {
        children = new ArrayList<PatriciaTree>();
        label = "".toCharArray();
        root= this;
    }
    public PatriciaTree(String label, PatriciaTree root) {
        children = new ArrayList<PatriciaTree>();
        this.label = label.toCharArray();
        this.root=root;
    }
    public void setChildren(ArrayList<PatriciaTree> children) {
    	this.children=children;
    }
    public void setLabel(char[] label) {
    	this.label=label;
	}

	@Override
	public int insert(String str) {
		//este nodo es el nodo con el prefijo mas grande en comun con str
		PatriciaTree nodo =this.mysearch(str.toCharArray(), 0);
		String prefix =greatestCommonPrefix(new String(nodo.label), str);
		root.insertar(prefix, str, 0);

		return 0;
	}
	public String greatestCommonPrefix(String a, String b) {
        int minLength = Math.min(a.length(), b.length());
        for (int i = 0; i < minLength; i++) {
            if (a.charAt(i) != b.charAt(i)) {
                return a.substring(0, i);
            }
        }
        return a.substring(0, minLength);
    }

	@Override
	public int search(String str) {
		PatriciaTree nodo=mysearch(str.toCharArray(), 0);
		String strlabel=new String(nodo.label);
		if (strlabel==str) return 1;
		else return 0;
	}

	@Override
	public int frequency(String word) {
		return 0;
	}

	private void insertar(String prefix, String str, int pos) {
		char[] word =prefix.toCharArray();
		for (PatriciaTree child : this.children) {

			//me sirve este hijo, buen camino
			if (word[pos] == label[0]) {
				if (label.length==(word.length-pos)) {
					//tamaño justo
					//p se encontro en el nodo
					this.children.add(new PatriciaTree(str, this.root));
				}
				else if (label.length > (word.length-pos)) {
					PatriciaTree nodoviejo=
							new PatriciaTree(new String(this.label), this.root);
					nodoviejo.setChildren(this.children);
					String strlabel=new String(label).substring(prefix.length());
					this.setLabel(strlabel.toCharArray());
					this.setChildren(new ArrayList<PatriciaTree>());
					this.children.add(nodoviejo);
					this.children.add(new PatriciaTree(str, root));


				}
				else{
					//tengo que buscar en los hijos de este nodo
					this.insertar(prefix, str, pos+this.label.length);
				}
				break;
			}
		}
	}
	public PatriciaTree mysearch(char[] word , int pos) {
		//llegue a una hoja
		if (this.children.isEmpty()) {
			return this;
		}
		//estoy en un nodo donde la palabra es prefijo de el label de este
		//o es igual al label
		if ((word.length-pos)<= this.label.length) {
			return this;
		}
		//sino reviso en cada hijo si logro bajar mas
		for (PatriciaTree child : this.children) {
			if (child.label[0]==word[pos+1]) {
				//tengo hijo con letra siguiente
				return child.mysearch(word, pos+1);
			}
		}
		//si llego aca no tengo hijo con letra siguiente
		return this;
	}

}