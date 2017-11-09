import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DictionaryTest {

	IDict ternaryTree1;
	IDict hashMap1;
	
	
	
	String dir = "";
	
	@BeforeEach
	void setUp() throws Exception {
		ternaryTree1 = new TernaryTree();	
		hashMap1 = new HashMap();
	}
	

	@Test
	void testTernaryTree() {
		// Insertar una letra:
		ternaryTree1.insert("a");
		

		assertEquals(1, ternaryTree1.search("a"));
		
		// Test: Insertar varias palabras
		ternaryTree1.insert("ala");
		
		assertEquals(1, ternaryTree1.search("a"));
		assertEquals(1, ternaryTree1.search("ala"));

		ternaryTree1.insert("alabardo");

		assertEquals(1, ternaryTree1.search("a"));
		assertEquals(1, ternaryTree1.search("ala"));
		assertEquals(1, ternaryTree1.search("alabardo"));
		
		ternaryTree1.insert("alabastra");
		
		assertEquals(1, ternaryTree1.search("a"));
		assertEquals(1, ternaryTree1.search("ala"));
		assertEquals(1, ternaryTree1.search("alabardo"));
		assertEquals(1, ternaryTree1.search("alabastra"));
		
		ternaryTree1.insert("castro");
		
		assertEquals(1, ternaryTree1.search("a"));
		assertEquals(1, ternaryTree1.search("ala"));
		assertEquals(1, ternaryTree1.search("alabardo"));
		assertEquals(1, ternaryTree1.search("alabastra"));
		assertEquals(1, ternaryTree1.search("castro"));
		
		assertEquals(0 ,ternaryTree1.search("cata"));
		assertEquals(0, ternaryTree1.search("cataplasma"));
		assertEquals(0, ternaryTree1.search("catapulta"));
		assertEquals(0, ternaryTree1.search("caternaria"));

		ternaryTree1.insert("cataplasma");
		ternaryTree1.insert("catapulta");
		ternaryTree1.insert("caternaria");
		
		
		assertEquals(1, ternaryTree1.search("cataplasma"));
		assertEquals(1, ternaryTree1.search("catapulta"));
		assertEquals(1, ternaryTree1.search("caternaria"));
		assertEquals(0, ternaryTree1.search("cata"));
		
		assertEquals(0, ternaryTree1.search("fundibolo"));
		
		ternaryTree1.insert("fundibolo");
		assertEquals(1, ternaryTree1.search("fundibolo"));
		
		// Test: Insertar el mismo elemento 2 veces
		ternaryTree1.insert("fundibolo");
		assertEquals(1, ternaryTree1.search("fundibolo"));
		
		ternaryTree1.insert("tamarindo");
		assertEquals(1, ternaryTree1.search("tamarindo"));
		
		// Test: Mayusculas - Minusculas
		ternaryTree1.insert("tortuga");
		
		assertEquals(1, ternaryTree1.search("tortuga"));
		assertEquals(0, ternaryTree1.search("TORTUGA"));
		assertEquals(0, ternaryTree1.search("Tortuga"));
		assertEquals(0, ternaryTree1.search("tortugA"));
		
		// Test : espacio, caracteres raros:
		
		assertEquals(0, ternaryTree1.search("tortuga "));
		assertEquals(0, ternaryTree1.search(" tortuga "));
		assertEquals(0, ternaryTree1.search("tortuga\n"));
		assertEquals(0, ternaryTree1.search("tórtuga"));
		
		// Test : Cantidad de apariciones
		
		assertEquals(1, ternaryTree1.frequency("a"));
		assertEquals(1, ternaryTree1.frequency("ala"));
		assertEquals(1, ternaryTree1.frequency("alabardo"));
		
		assertEquals(0, ternaryTree1.frequency("cata"));
		assertEquals(1, ternaryTree1.frequency("cataplasma"));
		assertEquals(1, ternaryTree1.frequency("catapulta"));
		assertEquals(1, ternaryTree1.frequency("caternaria"));
		
		assertEquals(0, ternaryTree1.frequency("abc"));

		
		assertEquals(2, ternaryTree1.frequency("fundibolo"));
		
		for (int i = 0; i < 10000 ; i ++) {
			ternaryTree1.insert("superfluo");
		}
		
		assertEquals(10000, ternaryTree1.frequency("superfluo"));
	}
	
	
	@Test
	public void readFileTernaryTreeTest() throws IOException {
		
		// Leemos las palabras del archivo
		String [] splited = null;
		BufferedReader br = new BufferedReader(new FileReader("alice.txt"));
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {		    	
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    String everything = sb.toString();
		    
		    // Reemplazamos los saltos de linea, tabs, etc.. por espacios:
		    everything.replaceAll("[\\t\\n\\r]+"," ");
		    
		    // Separamos las palabras por espacios:
		    splited = everything.split("\\s+");
		    

			// Insertamos todas las palabras en el arbol
			for (String word : splited) {
				ternaryTree1.insert(word);
			}
			// Buscamos todas las palabras insertadas en el arbol
			for (String word: splited) {
				assertEquals(1, ternaryTree1.search(word));
			}
		
		} finally {
		    br.close();
		}
	}
	
	
	@Test
	public void testTernaryTreeSimilarity() throws Exception {
		
		// Test con diccionario TernaryTree
		assertEquals(1, SimilarityFunctions.calculateSimilarity("alice.txt", "alice.txt", TernaryTree.class));
		assertEquals(1, SimilarityFunctions.calculateSimilarity("sherlock.txt", "sherlock.txt", TernaryTree.class));
		assertEquals(0.02826535701751709, SimilarityFunctions.calculateSimilarity("alice.txt", "sherlock.txt", TernaryTree.class));
		assertEquals(0.02826535701751709, SimilarityFunctions.calculateSimilarity("sherlock.txt", "alice.txt", TernaryTree.class));

	}
	
	
	// --------------------------------------- HASH MAP TEST ---------------------------------------

	@Test
	void testHashMap() {
		// Insertar una letra:
		hashMap1.insert("a");
		

		assertEquals(1, hashMap1.search("a"));
		
		// Test: Insertar varias palabras
		hashMap1.insert("ala");
		
		assertEquals(1, hashMap1.search("a"));
		assertEquals(1, hashMap1.search("ala"));

		ternaryTree1.insert("alabardo");

		assertEquals(1, hashMap1.search("a"));
		assertEquals(1, hashMap1.search("ala"));
		assertEquals(1, hashMap1.search("alabardo"));
		
		ternaryTree1.insert("alabastra");
		
		assertEquals(1, hashMap1.search("a"));
		assertEquals(1, hashMap1.search("ala"));
		assertEquals(1, hashMap1.search("alabardo"));
		assertEquals(1, hashMap1.search("alabastra"));
		
		hashMap1.insert("castro");
		
		assertEquals(1, hashMap1.search("a"));
		assertEquals(1, hashMap1.search("ala"));
		assertEquals(1, hashMap1.search("alabardo"));
		assertEquals(1, hashMap1.search("alabastra"));
		assertEquals(1, hashMap1.search("castro"));
		
		assertEquals(0 ,hashMap1.search("cata"));
		assertEquals(0, hashMap1.search("cataplasma"));
		assertEquals(0, hashMap1.search("catapulta"));
		assertEquals(0, hashMap1.search("caternaria"));

		hashMap1.insert("cataplasma");
		hashMap1.insert("catapulta");
		hashMap1.insert("caternaria");
		
		
		assertEquals(1, hashMap1.search("cataplasma"));
		assertEquals(1, hashMap1.search("catapulta"));
		assertEquals(1, hashMap1.search("caternaria"));
		assertEquals(0, hashMap1.search("cata"));
		
		assertEquals(0, hashMap1.search("fundibolo"));
		
		hashMap1.insert("fundibolo");
		assertEquals(1, hashMap1.search("fundibolo"));
		
		// Test: Insertar el mismo elemento 2 veces
		hashMap1.insert("fundibolo");
		assertEquals(1, hashMap1.search("fundibolo"));
		
		hashMap1.insert("tamarindo");
		assertEquals(1, hashMap1.search("tamarindo"));
		
		// Test: Mayusculas - Minusculas
		hashMap1.insert("tortuga");
		
		assertEquals(1, hashMap1.search("tortuga"));
		assertEquals(0, hashMap1.search("TORTUGA"));
		assertEquals(0, hashMap1.search("Tortuga"));
		assertEquals(0, hashMap1.search("tortugA"));
		
		// Test : espacio, caracteres raros:
		
		assertEquals(0, hashMap1.search("tortuga "));
		assertEquals(0, hashMap1.search(" tortuga "));
		assertEquals(0, hashMap1.search("tortuga\n"));
		assertEquals(0, hashMap1.search("tórtuga"));
		
		// Test : Cantidad de apariciones
		
		assertEquals(1, hashMap1.frequency("a"));
		assertEquals(1, hashMap1.frequency("ala"));
		assertEquals(1, hashMap1.frequency("alabardo"));
		
		assertEquals(0, hashMap1.frequency("cata"));
		assertEquals(1, hashMap1.frequency("cataplasma"));
		assertEquals(1, hashMap1.frequency("catapulta"));
		assertEquals(1, hashMap1.frequency("caternaria"));
		
		assertEquals(0, hashMap1.frequency("abc"));

		
		assertEquals(2, hashMap1.frequency("fundibolo"));
		
		for (int i = 0; i < 10000 ; i ++) {
			hashMap1.insert("superfluo");
		}
		
		assertEquals(10000, hashMap1.frequency("superfluo"));
	}
	
	@Test
	public void testHashSimilarity() throws Exception {
		assertEquals(1, SimilarityFunctions.calculateSimilarity("alice.txt", "alice.txt", HashMap.class));
		assertEquals(1, SimilarityFunctions.calculateSimilarity("sherlock.txt", "sherlock.txt", HashMap.class));
		assertEquals(0.02826535701751709, SimilarityFunctions.calculateSimilarity("alice.txt", "sherlock.txt", HashMap.class));
		assertEquals(0.02826535701751709, SimilarityFunctions.calculateSimilarity("sherlock.txt", "alice.txt", HashMap.class));
	}	
	
}
