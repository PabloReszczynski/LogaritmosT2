
public class TernaryTree implements IDict {
	
	private Character value;
	private TernaryTree left;
	private TernaryTree right;
	private TernaryTree center;
	
	public TernaryTree() {
		this.value = null;
		this.left = null;
		this.right = null;
		this.center = null;
	}
	
	public TernaryTree(char chr) {
		this.value = chr;
		this.left = null;
		this.right = null;
		this.center = null;
	}
	
	@Override
	public int insert(String str) {
		// Caso base: ya no me quedan mas chars que buscar
		if (str.length() == 0) {
			return 1;
		}
		
		if (this.value == null) {
			this.value = str.charAt(0);
			this.center = new TernaryTree();
			return this.center.insert(str.substring(1));
		}

		// Si estamos buscando el char P[i] (con P = palabra)

		// Caso 1: P[i] = this.value
		// Buscamos ahora P[i+1] en el nodo del centro:
		if (this.value == str.charAt(0)){
			if (this.center == null) {
				this.center = new TernaryTree();
				return this.center.insert(str.substring(1));
			} else {
				return this.center.insert(str.substring(1));
			}
		}
		// Caso 2: P[i] < this.value
		// Seguimos buscando P[i] en el nodo de la izquierda:
		if (this.value < str.charAt(0)) {
			if (this.left == null) {
				this.left = new TernaryTree();
				return this.left.insert(str.substring(1));
			} else {
				return this.left.insert(str);
			}
		}
		// Caso 3: P[i] > this.value
		// Seguimos buscando P[i] en el nodo de la derecha:
		if (this.value > str.charAt(0)) {
			if (this.right == null) {
				this.right = new TernaryTree();
				return this.right.insert(str.substring(1));
			}
			else {
				return this.right.insert(str);
			}
		}
		return 0;
	}


	
	/*
	 * Hace una busqueda infructuosa en el arbol.
	 * Si retorna null, entonces si se encontro el str y por lo tanto 
	 * no hay que agregar nada.
	 */
	public TernaryTree unsuccessfulSearch(String str) {
		// Caso base: ya no me quedan mas chars que buscar
		if (str.length() == 0) {
			return null;
		}

		// Si estamos buscando el char P[i] (con P = palabra)

		// Caso 1: P[i] = this.value
		// Buscamos ahora P[i+1] en el nodo del centro:
		if (this.value == str.charAt(0)){
			if (this.center == null) {
				this.center = new TernaryTree();
				return this.center;
			} else {
				return this.center.unsuccessfulSearch(str.substring(1));
			}
		}
		// Caso 2: P[i] < this.value
		// Seguimos buscando P[i] en el nodo de la izquierda:
		if (this.value < str.charAt(0)) {
			if (this.left == null) {
				this.left = new TernaryTree();
				return this.left;
			} else {
				return this.left.unsuccessfulSearch(str);
			}
		}
		// Caso 3: P[i] > this.value
		// Seguimos buscando P[i] en el nodo de la derecha:
		if (this.value > str.charAt(0)) {
			if (this.right == null) {
				this.right = new TernaryTree();
				return this;
			}
			else {
				return this.right.unsuccessfulSearch(str);
			}
		}
		return null;
	}
		

	@Override
	public int search(String str) {
		
		// Caso base: ya no me quedan mas chars que buscar
		if (str.length() == 0) {
			return 1;
		}
		
		// Si estamos buscando el char P[i] (con P = palabra)
		
		// Caso 1: P[i] = this.value
		// Buscamos ahora P[i+1] en el nodo del centro:
		if (this.value == str.charAt(0)){
			if (this.center == null) {
				return 0;
			} else {
				return this.center.search(str.substring(1));
			}
		}
		// Caso 2: P[i] < this.value
		// Seguimos buscando P[i] en el nodo de la izquierda:
		if (this.value < str.charAt(0)) {
			if (this.left == null) {
				return 0;
			} else {
				return this.left.search(str);
			}
		}
		// Caso 3: P[i] > this.value
		// Seguimos buscando P[i] en el nodo de la derecha:
		if (this.value > str.charAt(0)) {
			if (this.right == null) {
				return 0;
			}
			else {
				return this.right.search(str);
			}
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return "Contenido del Nodo: '" + this.value.toString() + "'";
	}

	public char getValue() {
		return value;
	}




	public void setValue(char value) {
		this.value = value;
	}




	public TernaryTree getLeft() {
		return left;
	}




	public void setLeft(TernaryTree left) {
		this.left = left;
	}




	public TernaryTree getRight() {
		return right;
	}




	public void setRight(TernaryTree right) {
		this.right = right;
	}




	public TernaryTree getCenter() {
		return center;
	}




	public void setCenter(TernaryTree center) {
		this.center = center;
	}





}
