
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
	

	@Override
	public int insert(String str) {
		// Caso base: ya no me quedan mas chars que buscar
		if (str.length() == 0) {
			//System.out.println("------------ Fin Insercion ------------- \n");
			return 1;
		}
		
		// Caso: this.value == null
		// Inserto el primer char de str en this.value:
		if (this.value == null) {
			this.value = str.charAt(0);
			
			// Caso base: ya no me quedan mas chars que buscar
			if (str.length() >= 1) {
				this.center = new TernaryTree();
				return this.center.insert(str.substring(1));
			} else {
				return 1;
			}
			

		}

		// Si estamos buscando el char P[i] (con P = palabra)

		// Caso: P[i] = this.value
		// Buscamos ahora P[i+1] en el nodo del centro:
		if (this.value == str.charAt(0)){
			return this.center.insert(str.substring(1));
		}
		// Caso: P[i] < this.value
		// Seguimos buscando P[i] en el nodo de la izquierda:
		if (this.value < str.charAt(0)) {
			if (this.left == null) {
				this.left = new TernaryTree();
				return this.left.insert(str);
			} else {
				return this.left.insert(str);
			}
		}
		// Caso: P[i] > this.value
		// Seguimos buscando P[i] en el nodo de la derecha:
		if (this.value > str.charAt(0)) {
			if (this.right == null) {
				this.right = new TernaryTree();
				return this.right.insert(str);
			}
			else {
				return this.right.insert(str);
			}
		}
		return 0;
	}


	@Override
	public int search(String str) {
		
		// Caso base: ya no me quedan mas chars que buscar
		if (str.length() == 0) {
			return 1;
		}
		
		// Si estamos buscando el char P[i] (con P = palabra)
		/*
		System.out.println("\nthis.value: " + this.value);
		System.out.println("this.left: " + this.left);
		System.out.println("this.right: " + this.right);
		System.out.println("this.center: " + this.center);
		System.out.println("str: " + str.charAt(0) + "\n"); */
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
}
