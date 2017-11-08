
public class TernaryTree implements IDict {
	
	private Character value;
	private TernaryTree left;
	private TernaryTree right;
	private TernaryTree center;
	
	private int wordCount;
	
	public TernaryTree() {
		this.value = null;
		this.left = null;
		this.right = null;
		this.center = null;
		this.wordCount = 0;
	}
	

	@Override
	public int insert(String str) {
		// Caso base: ya no me quedan mas chars que buscar
		if (str.length() == 0) {
			return 1;
		}
		
		// Caso: this.value == null
		// Inserto el primer char de str en this.value:
		if (this.value == null) {
			this.value = str.charAt(0);
			
			// Caso base: ya no me quedan mas chars que buscar
			if (str.length() == 1) {
				this.center = new TernaryTree();
				wordCount ++; // Aumentamos el contador
				
				return this.center.insert(str.substring(1));
			}
			
			// Caso base 2; Quedan chars, creamos un nuevo nodo e insertamos el char:
			if (str.length() > 1) {
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
			
			if (str.length() == 1) {
				wordCount ++;
			}
			
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
		else {
			if (this.right == null) {
				this.right = new TernaryTree();
				return this.right.insert(str);
			}
			else {
				return this.right.insert(str);
			}
		}
	}


	@Override
	public int search(String str) {
		
		if (this.value == null) {
			return 0;
		}
		
		// Si estamos buscando el char P[i] (con P = palabra)
	
		// Caso 1: P[i] = this.value
		// Buscamos ahora P[i+1] en el nodo del centro:
		if (this.value == str.charAt(0)){
			
			if (this.center == null) {
				return 0;
				
			} else if (str.length() == 1){
				return this.wordCount >= 1 ? 1 : 0;
				
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
	public int frequency(String str) {
		if (this.value == null) {
			return 0;
		}
		
		// Si estamos buscando el char P[i] (con P = palabra)
	
		// Caso 1: P[i] = this.value
		// Buscamos ahora P[i+1] en el nodo del centro:
		if (this.value == str.charAt(0)){
			
			if (this.center == null) {
				return 0;
				
			} else if (str.length() == 1){
				return this.wordCount;
				
			} else {
				return this.center.frequency(str.substring(1));
			}
			
		}
		// Caso 2: P[i] < this.value
		// Seguimos buscando P[i] en el nodo de la izquierda:
		if (this.value < str.charAt(0)) {
			if (this.left == null) {
				return 0;
			} else {
				return this.left.frequency(str);
			}
		}
		// Caso 3: P[i] > this.value
		// Seguimos buscando P[i] en el nodo de la derecha:
		if (this.value > str.charAt(0)) {
			if (this.right == null) {
				return 0;
			}
			else {
				return this.right.frequency(str);
			}
		}
		return 0;
	}
}
