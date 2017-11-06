import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TernaryTreeTest {

	IDict ternaryTree1;
	IDict hashMap1;
	
	@BeforeEach
	void setUp() throws Exception {
		ternaryTree1 = new TernaryTree();	
	}

	@Test
	void testTernaryTree() {
		// Insertar una letra:
		ternaryTree1.insert("a");

		assertEquals(1, ternaryTree1.search("a"));
		
		ternaryTree1.insert("ala");
		
		assertEquals(1, ternaryTree1.search("a"));
		assertEquals(1, ternaryTree1.search("ala"));
		
		ternaryTree1.insert("alabardo");
		
		assertEquals(1, ternaryTree1.search("a"));
		assertEquals(1, ternaryTree1.search("ala"));
		assertEquals(1, ternaryTree1.search("alabardo"));
		
		ternaryTree1.insert("alabastra");
		
		assertEquals(1, ternaryTree1.search("a"), 1);
		assertEquals(1, ternaryTree1.search("ala"));
		assertEquals(1, ternaryTree1.search("alabardo"));
		assertEquals(1, ternaryTree1.search("alabastra"));
		
		ternaryTree1.insert("castro");
		
		assertEquals(1, ternaryTree1.search("a"));
		assertEquals(1, ternaryTree1.search("ala"));
		assertEquals(1, ternaryTree1.search("alabardo"));
		assertEquals(1, ternaryTree1.search("alabastra"));
		assertEquals(1, ternaryTree1.search("castro"));
		
		assertEquals(0, ternaryTree1.search("cataplasma"));
		assertEquals(0, ternaryTree1.search("catapulta"));
		assertEquals(0, ternaryTree1.search("caternaria"));

		ternaryTree1.insert("cataplasma");
		ternaryTree1.insert("catapulta");
		ternaryTree1.insert("caternaria");
		
		assertEquals(1, ternaryTree1.search("cataplasma"));
		assertEquals(1, ternaryTree1.search("catapulta"));
		assertEquals(1, ternaryTree1.search("caternaria"));
		
		assertEquals(0, ternaryTree1.search("fundibolo"));
		
		ternaryTree1.insert("fundibolo");
		assertEquals(1, ternaryTree1.search("fundibolo"));
		
		ternaryTree1.insert("tamarindo");
		assertEquals(1, ternaryTree1.search("tamarindo"));
		
		ternaryTree1.insert("tortuga");
		
		// Test: Mayusculas - Minusculas
		assertEquals(1, ternaryTree1.search("tortuga"));
		assertEquals(0, ternaryTree1.search("TORTUGA"));
		assertEquals(0, ternaryTree1.search("Tortuga"));
		assertEquals(0, ternaryTree1.search("tortugA"));
		
		// Test : espacio, caracteres raros:
		assertEquals(0, ternaryTree1.search("tortuga "));
		assertEquals(0, ternaryTree1.search(" tortuga "));
		assertEquals(0, ternaryTree1.search("tortuga\n"));
		assertEquals(0, ternaryTree1.search("tórtuga"));
	}
}
