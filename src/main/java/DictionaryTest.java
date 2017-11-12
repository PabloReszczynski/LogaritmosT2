import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DictionaryTest {

	IDict patriciaTree;
	IDict ternaryTree;
	IDict hashMap;
	
	String dir = "";
	
	@BeforeEach
	void setUp() throws Exception {
		patriciaTree = new PatriciaTree();
		ternaryTree = new TernaryTree();	
		hashMap = new HashMap();
	}

	@Test
	void testPatriciaTree() {
		// Insertar una letra:
		patriciaTree.insert("a");

		assertEquals(1, patriciaTree.search("a"));
		
		// Test: Insertar varias palabras
		patriciaTree.insert("ala");
		
		assertEquals(1, patriciaTree.search("a"));
		assertEquals(1, patriciaTree.search("ala"));

		patriciaTree.insert("alabardo");

		assertEquals(1, patriciaTree.search("a"));
		assertEquals(1, patriciaTree.search("ala"));
		assertEquals(1, patriciaTree.search("alabardo"));
		
		patriciaTree.insert("alabastra");
		
		assertEquals(1, patriciaTree.search("a"));
		assertEquals(1, patriciaTree.search("ala"));
		assertEquals(1, patriciaTree.search("alabardo"));
		assertEquals(1, patriciaTree.search("alabastra"));
		
		patriciaTree.insert("castro");
		
		assertEquals(1, patriciaTree.search("a"));
		assertEquals(1, patriciaTree.search("ala"));
		assertEquals(1, patriciaTree.search("alabardo"));
		assertEquals(1, patriciaTree.search("alabastra"));
		assertEquals(1, patriciaTree.search("castro"));
		
		assertEquals(0 ,patriciaTree.search("cata"));
		assertEquals(0, patriciaTree.search("cataplasma"));
		assertEquals(0, patriciaTree.search("catapulta"));
		assertEquals(0, patriciaTree.search("caternaria"));

		patriciaTree.insert("cataplasma");
		patriciaTree.insert("catapulta");
		patriciaTree.insert("caternaria");
		
		
		assertEquals(1, patriciaTree.search("cataplasma"));
		assertEquals(1, patriciaTree.search("catapulta"));
		assertEquals(1, patriciaTree.search("caternaria"));
		assertEquals(0, patriciaTree.search("cata"));
		
		assertEquals(0, patriciaTree.search("fundibolo"));
		
		patriciaTree.insert("fundibolo");
		assertEquals(1, patriciaTree.search("fundibolo"));
		
		// Test: Insertar el mismo elemento 2 veces
		patriciaTree.insert("fundibolo");
		assertEquals(1, patriciaTree.search("fundibolo"));
		
		patriciaTree.insert("tamarindo");
		assertEquals(1, patriciaTree.search("tamarindo"));
		
		// Test: Mayusculas - Minusculas
		patriciaTree.insert("tortuga");
		
		assertEquals(1, patriciaTree.search("tortuga"));
		assertEquals(0, patriciaTree.search("TORTUGA"));
		assertEquals(0, patriciaTree.search("Tortuga"));
		assertEquals(0, patriciaTree.search("tortugA"));
		
		// Test : espacio, caracteres raros:
		
		assertEquals(0, patriciaTree.search("tortuga "));
		assertEquals(0, patriciaTree.search(" tortuga "));
		assertEquals(0, patriciaTree.search("tortuga\n"));
		assertEquals(0, patriciaTree.search("tórtuga"));
		
		// Test : Cantidad de apariciones
		
		assertEquals(1, patriciaTree.frequency("a"));
		assertEquals(1, patriciaTree.frequency("ala"));
		assertEquals(1, patriciaTree.frequency("alabardo"));
		
		assertEquals(0, patriciaTree.frequency("cata"));
		assertEquals(1, patriciaTree.frequency("cataplasma"));
		assertEquals(1, patriciaTree.frequency("catapulta"));
		assertEquals(1, patriciaTree.frequency("caternaria"));
		
		assertEquals(0, patriciaTree.frequency("abc"));

		
		assertEquals(2, patriciaTree.frequency("fundibolo"));
		
		for (int i = 0; i < 10000 ; i ++) {
			patriciaTree.insert("superfluo");
		}
		
		assertEquals(10000, patriciaTree.frequency("superfluo"));
	}
	

	@Test
	void testTernaryTree() {
		// Insertar una letra:
		ternaryTree.insert("a");
		

		assertEquals(1, ternaryTree.search("a"));
		
		// Test: Insertar varias palabras
		ternaryTree.insert("ala");
		
		
		
		assertEquals(1, ternaryTree.search("a"));
		assertEquals(1, ternaryTree.search("ala"));
		assertEquals(3, ((TernaryTree) ternaryTree).getTreeSize(0));

		ternaryTree.insert("alabardo");

		assertEquals(1, ternaryTree.search("a"));
		assertEquals(1, ternaryTree.search("ala"));
		assertEquals(1, ternaryTree.search("alabardo"));
		assertEquals(8, ((TernaryTree) ternaryTree).getTreeSize(0));
		
		ternaryTree.insert("alabastra");
		
		assertEquals(1, ternaryTree.search("a"));
		assertEquals(1, ternaryTree.search("ala"));
		assertEquals(1, ternaryTree.search("alabardo"));
		assertEquals(1, ternaryTree.search("alabastra"));
		assertEquals(12, ((TernaryTree) ternaryTree).getTreeSize(0));
		
		
		ternaryTree.insert("castro");
		
		assertEquals(1, ternaryTree.search("a"));
		assertEquals(1, ternaryTree.search("ala"));
		assertEquals(1, ternaryTree.search("alabardo"));
		assertEquals(1, ternaryTree.search("alabastra"));
		assertEquals(1, ternaryTree.search("castro"));
		
		assertEquals(0 ,ternaryTree.search("cata"));
		assertEquals(0, ternaryTree.search("cataplasma"));
		assertEquals(0, ternaryTree.search("catapulta"));
		assertEquals(0, ternaryTree.search("caternaria"));

		ternaryTree.insert("cataplasma");
		ternaryTree.insert("catapulta");
		ternaryTree.insert("caternaria");
		
		
		assertEquals(1, ternaryTree.search("cataplasma"));
		assertEquals(1, ternaryTree.search("catapulta"));
		assertEquals(1, ternaryTree.search("caternaria"));
		assertEquals(0, ternaryTree.search("cata"));
		
		assertEquals(0, ternaryTree.search("fundibolo"));
		
		ternaryTree.insert("fundibolo");
		assertEquals(1, ternaryTree.search("fundibolo"));
		
		// Test: Insertar el mismo elemento 2 veces
		ternaryTree.insert("fundibolo");
		assertEquals(1, ternaryTree.search("fundibolo"));
		
		ternaryTree.insert("tamarindo");
		assertEquals(1, ternaryTree.search("tamarindo"));
		
		// Test: Mayusculas - Minusculas
		ternaryTree.insert("tortuga");
		
		assertEquals(1, ternaryTree.search("tortuga"));
		assertEquals(0, ternaryTree.search("TORTUGA"));
		assertEquals(0, ternaryTree.search("Tortuga"));
		assertEquals(0, ternaryTree.search("tortugA"));
		
		// Test : espacio, caracteres raros:
		
		assertEquals(0, ternaryTree.search("tortuga "));
		assertEquals(0, ternaryTree.search(" tortuga "));
		assertEquals(0, ternaryTree.search("tortuga\n"));
		assertEquals(0, ternaryTree.search("tórtuga"));
		
		// Test : Cantidad de apariciones
		
		assertEquals(1, ternaryTree.frequency("a"));
		assertEquals(1, ternaryTree.frequency("ala"));
		assertEquals(1, ternaryTree.frequency("alabardo"));
		
		assertEquals(0, ternaryTree.frequency("cata"));
		assertEquals(1, ternaryTree.frequency("cataplasma"));
		assertEquals(1, ternaryTree.frequency("catapulta"));
		assertEquals(1, ternaryTree.frequency("caternaria"));
		
		assertEquals(0, ternaryTree.frequency("abc"));

		
		assertEquals(2, ternaryTree.frequency("fundibolo"));
		
		for (int i = 0; i < 10000 ; i ++) {
			ternaryTree.insert("superfluo");
		}
		
		assertEquals(10000, ternaryTree.frequency("superfluo"));
	}
	
	
	@Test
	void testHashMap() {
		// Insertar una letra:
		hashMap.insert("a");
		

		assertNotEquals(-1, hashMap.search("a"));
		
		// Test: Insertar varias palabras
		hashMap.insert("ala");
		
		assertNotEquals(-1, hashMap.search("a"));
		assertNotEquals(-1, hashMap.search("ala"));
		

		hashMap.insert("alabardo");

		assertNotEquals(-1, hashMap.search("a"));
		assertNotEquals(-1, hashMap.search("ala"));
		assertNotEquals(-1, hashMap.search("alabardo"));
		
		hashMap.insert("alabastra");
		
		assertNotEquals(-1, hashMap.search("a"));
		assertNotEquals(-1, hashMap.search("ala"));
		assertNotEquals(-1, hashMap.search("alabardo"));
		assertNotEquals(-1, hashMap.search("alabastra"));
		
		hashMap.insert("castro");
		
		assertNotEquals(-1, hashMap.search("a"));
		assertNotEquals(-1, hashMap.search("ala"));
		assertNotEquals(-1, hashMap.search("alabardo"));
		assertNotEquals(-1, hashMap.search("alabastra"));
		assertNotEquals(-1, hashMap.search("castro"));
		
		assertEquals(-1 ,hashMap.search("cata"));
		assertEquals(-1, hashMap.search("cataplasma"));
		assertEquals(-1, hashMap.search("catapulta"));
		assertEquals(-1, hashMap.search("caternaria"));

		hashMap.insert("cataplasma");
		hashMap.insert("catapulta");
		hashMap.insert("caternaria");
		
		
		assertNotEquals(-1, hashMap.search("cataplasma"));
		assertNotEquals(-1, hashMap.search("catapulta"));
		assertNotEquals(-1, hashMap.search("caternaria"));
		assertEquals(-1, hashMap.search("cata"));
		
		assertEquals(-1, hashMap.search("fundibolo"));
		
		hashMap.insert("fundibolo");
		assertNotEquals(-1, hashMap.search("fundibolo"));
		
		// Test: Insertar el mismo elemento 2 veces
		hashMap.insert("fundibolo");
		assertNotEquals(-1, hashMap.search("fundibolo"));
		
		hashMap.insert("tamarindo");
		assertNotEquals(-1, hashMap.search("tamarindo"));
		
		// Test: Mayusculas - Minusculas
		hashMap.insert("tortuga");
		
		assertNotEquals(-1, hashMap.search("tortuga"));
		assertEquals(-1, hashMap.search("TORTUGA"));
		assertEquals(-1, hashMap.search("Tortuga"));
		assertEquals(-1, hashMap.search("tortugA"));
		
		// Test : espacio, caracteres raros:
		
		assertEquals(-1, hashMap.search("tortuga "));
		assertEquals(-1, hashMap.search(" tortuga "));
		assertEquals(-1, hashMap.search("tortuga\n"));
		assertEquals(-1, hashMap.search("tórtuga"));
		
		// Test : Cantidad de apariciones
		
		assertEquals(1, hashMap.frequency("a"));
		assertEquals(1, hashMap.frequency("ala"));
		assertEquals(1, hashMap.frequency("alabardo"));
		
		assertEquals(0, hashMap.frequency("cata"));
		assertEquals(1, hashMap.frequency("cataplasma"));
		assertEquals(1, hashMap.frequency("catapulta"));
		assertEquals(1, hashMap.frequency("caternaria"));
		
		assertEquals(0, hashMap.frequency("abc"));

		
		assertEquals(2, hashMap.frequency("fundibolo"));
		
		for (int i = 0; i < 10000 ; i ++) {
			hashMap.insert("superfluo");
			hashMap.insert("cototo");
		}
		
		assertEquals(10000, hashMap.frequency("superfluo"));
	}
	
	
	@Test
	void readFileTest() throws IOException {
		// Test de esfuerzo
		BufferedReader br = new BufferedReader(new FileReader("alice.txt"));

		StringBuilder sb = new StringBuilder();
		String line = br.readLine();

		while (line != null) {		    	
			sb.append(line);
			line = br.readLine();
		}
		String everything = sb.toString();

		// Reemplazamos los saltos de linea, comas, numeros, tabs, etc.. por espacios:
		everything = everything.replaceAll("[\\t\\n\\r]+"," ").toLowerCase();
		everything = everything.replaceAll("[^a-zA-z ]",  "");
		
		String[] text = everything.split("\\s+");
		br.close();	   
		
		for (String word : text) {
			patriciaTree.insert(word);
			ternaryTree.insert(word);
			hashMap.insert(word);
		}
		
		
		for (String word : text) {
			assertTrue(patriciaTree.search(word) > 0);
			assertTrue(ternaryTree.search(word) > 0);
			assertTrue(hashMap.search(word) > 0);
		}
	}
	
	
	@Test
	void testPatriciaTreeSimilarity() throws Exception {
		
		// Test con diccionario TernaryTree
		assertEquals(1, SimilarityFunctions.calculateSimilarity("alice.txt", "alice.txt", PatriciaTree.class));
		assertEquals(1, SimilarityFunctions.calculateSimilarity("sherlock.txt", "sherlock.txt", PatriciaTree.class));
		assertEquals(0.028265334493304706, SimilarityFunctions.calculateSimilarity("alice.txt", "sherlock.txt", PatriciaTree.class));
		assertEquals(0.028265334493304706, SimilarityFunctions.calculateSimilarity("sherlock.txt", "alice.txt", PatriciaTree.class));

	}
	
	
	@Test
	void testTernaryTreeSimilarity() throws Exception {
		
		// Test con diccionario TernaryTree
		assertEquals(1, SimilarityFunctions.calculateSimilarity("alice.txt", "alice.txt", TernaryTree.class));
		assertEquals(1, SimilarityFunctions.calculateSimilarity("sherlock.txt", "sherlock.txt", TernaryTree.class));
		assertEquals(0.028265334493304706, SimilarityFunctions.calculateSimilarity("alice.txt", "sherlock.txt", TernaryTree.class));
		assertEquals(0.028265334493304706, SimilarityFunctions.calculateSimilarity("sherlock.txt", "alice.txt", TernaryTree.class));

	}

	
	@Test
	void testHashSimilarity() throws Exception {
		assertEquals(1, SimilarityFunctions.calculateSimilarity("alice.txt", "alice.txt", HashMap.class));
		assertEquals(1, SimilarityFunctions.calculateSimilarity("sherlock.txt", "sherlock.txt", HashMap.class));
		assertEquals(0.20374389744417643, SimilarityFunctions.calculateSimilarity("alice.txt", "sherlock.txt", HashMap.class));
		assertEquals(0.20374389744417643, SimilarityFunctions.calculateSimilarity("sherlock.txt", "alice.txt", HashMap.class));
	}	
	
}
