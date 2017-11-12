import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SimilarityFunctions {


	public static double calculateSimilarity(String text1, String text2, Class<? extends IDict> dictClass) throws Exception {
		
		try {
			String [] wordList1 = readTextFile(text1);
			String [] wordList2 = readTextFile(text2);
			
			return similarityFunction(wordList1, wordList2, dictClass );
			
		} catch (IOException e) {
			throw new Exception("Error en la lectura de algun archivo: \n", e); 
		}
	}
	
	
	public static String[] readTextFile (String filename) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(filename));

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
		
	
		br.close();	    
		// Retornamos un arreglo de las palabras
		return everything.split("\\s+");
	}
	
	
	/*
	 * testSimilarityTime
	 * Retorna el tiempo que se demora en calcular la similaridad entre dos textos,
	 * usando el diccionario especificado en el parametro.
	 */
	public static double similarityFunction(
			String[] text1, 
			String[] text2, 
			Class<? extends IDict> dictClass)
					throws InstantiationException, IllegalAccessException {

		IDict text1Dict = dictClass.newInstance();
		IDict text2Dict = dictClass.newInstance();

		ArrayList<String> concatenatedTextUniqueWords = new ArrayList<String>();
		
		int numberOfWords = text1.length + text2.length;
		int numerator = 0;

		

		// Agregamos palabras del texto 1 al diccionario 1
		for (String word : text1) {
			if (text1Dict.search(word) <= 0 && text2Dict.search(word) <= 0) {
				concatenatedTextUniqueWords.add(word);
			}
			text1Dict.insert(word);
		}

		// Agregamos palabras del texto 2 al diccionario 2
		for (String word : text2) {
			if (text1Dict.search(word) <= 0 && text2Dict.search(word) <= 0) {
				concatenatedTextUniqueWords.add(word);
			}
			text2Dict.insert(word);
		}


		// Calculo Numerador Similaridad
		for (String word : concatenatedTextUniqueWords) {
			numerator += Math.abs( text1Dict.frequency(word) - text2Dict.frequency(word)); 
		}
		
		return 1 - numerator/(float)numberOfWords;
	}
}
