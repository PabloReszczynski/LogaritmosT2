import java.util.ArrayList;

public class PatriciaTree implements IDict, java.io.Serializable {

	private ArrayList<PatriciaTree> children;
	private char[] label;
	private int frequency;
	private int altura;

	public PatriciaTree() {
		setChildren(new ArrayList<PatriciaTree>());
		setLabel("".toCharArray());
		setFrequency(0);
		altura=0;
	}
	public PatriciaTree(String label, int lp) {
		setChildren(new ArrayList<PatriciaTree>());
		this.setLabel(label.toCharArray());
		setFrequency(1);
		altura=lp;
	}

	@Override
	public int search(String word) {
		String str=word+"$";
		PatriciaTree nodo=mysearch(str.toCharArray(), 0);
		String label =new String(nodo.getLabel());
		if (str.equals(label))
			return 1;
		else return 0;

	}
	@Override
	public int frequency(String word) {
		String str=word+"$";
		PatriciaTree nodo=mysearch(str.toCharArray(), 0);
		String label =new String(nodo.getLabel());
		if (str.equals(label))
			return nodo.getFrequency();
		else return 0;
	}
	@Override
	public int insert(String word) {
		//System.out.print("insert "+word+" ");
		String str=word+"$";
		PatriciaTree nodo=mysearch(str.toCharArray(), 0);
		//System.out.print("nodo: ");
		//System.out.println(nodo.getLabel());
		//trato de insertar un nodo que ya está
		if (nodo.getChildren().isEmpty() && new String(nodo.getLabel()).equals(str)) {
			nodo.setFrequency(nodo.getFrequency()+1);
			return 0;
		}

		PatriciaTree hoja;
		if (nodo.getChildren().isEmpty()) {
			hoja=nodo;
		}
		else hoja=primeraHoja(nodo);

		// prefijo nunca incluye $ porq seria una palabra que ya esta insertada
		String prefix =greatestCommonPrefix(str, new String(hoja.getLabel()));

		this.reinsertar(str, prefix);
		return 1;
	}

	//str ya tiene el $ al final
	private void reinsertar(String str, String prefix) {
		PatriciaTree nodo= mysearch(prefix.toCharArray(), 0);

		//si es hoja
		if (nodo.getChildren().isEmpty()) {
			//si es la raiz
			if (new String(nodo.getLabel()).equals("")) {
				nodo.addChild(new PatriciaTree(str, 0));
			}
			else {
				PatriciaTree exnodo=new PatriciaTree(new String(nodo.getLabel()),0);
				exnodo.setFrequency(nodo.getFrequency());
				nodo.setLabel(prefix.substring(nodo.getAltura()).toCharArray());
				nodo.addChild(new PatriciaTree(str, nodo.label.length+nodo.getAltura()));
				exnodo.setAltura(nodo.label.length+nodo.getAltura());
				nodo.addChild(exnodo);
			}
		}
		//si nodo no es hoja
		else {
			//si se encontro un nodo igual al prefijo
			if (nodo.getLabel().length+ nodo.getAltura()==prefix.length()) {
				nodo.addChild(new PatriciaTree(str, nodo.getLabel().length + nodo.getAltura()));
			}
			//si se encontro en la arista
			else{
				String exnodolabel=new String(nodo.getLabel()).substring(prefix.length()-nodo.altura);

				PatriciaTree exnodo=new PatriciaTree(exnodolabel, 0);
				nodo.setLabel(prefix.substring(nodo.altura).toCharArray());

				exnodo.setChildren(nodo.getChildren());
				exnodo.setAltura(nodo.getLabel().length+ nodo.getAltura());
				nodo.setChildren(new ArrayList<PatriciaTree>());
				nodo.addChild(exnodo);
				nodo.addChild(new PatriciaTree(str, nodo.getLabel().length+ nodo.getAltura()));
			}
		}

	}

	private void setAltura(int length) {
		altura=length;

	}
	public PatriciaTree primeraHoja(PatriciaTree nodo) {
		PatriciaTree firstChild=nodo.getChildren().get(0);
		if (firstChild.getChildren().isEmpty()) {
			return firstChild;
		}
		else
			return primeraHoja(firstChild);
	}
	/*
	 * busca nodo con prefijo mas cercano a word
	 */
	public PatriciaTree mysearch(char[] word, int i) {
		if (this.getChildren().isEmpty()|| word.length<=i) {
			return this;
		}
		else {
			for (PatriciaTree child : this.getChildren()) {
				if ((child.getChildren().isEmpty() && word[i]==child.getLabel()[i])
						|| (!child.getChildren().isEmpty() && word[i]==child.getLabel()[0])) {
					//si cumple esas condiciones ese child es un buen camino
					return child.mysearch(word, i+child.getLabel().length);
				}
			}
			return this;
		}
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
	/*
	 *
	 *
	 * getters y setters
	 */
	public ArrayList<PatriciaTree> getChildren() {
		return children;
	}
	public void setChildren(ArrayList<PatriciaTree> children) {
		this.children = children;
	}
	public char[] getLabel() {
		return label;
	}
	public void setLabel(char[] label) {
		this.label = label;
	}
	public int getFrequency() {
		return frequency;
	}
	public int getAltura (){
		return altura;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public void addChild(PatriciaTree a) {
		this.children.add(a);

	}
	public void printChildren(PatriciaTree tree) {
		for (PatriciaTree c:tree.getChildren()) {
			System.out.print("hijo de ");
			System.out.println(new String(tree.getLabel()));
			System.out.println(new String(c.getLabel()));
			if (!c.getChildren().isEmpty()) {
				printChildren(c);
			}
		}
	}
}