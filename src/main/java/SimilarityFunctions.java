import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SimilarityFunctions {


	public static float calculateSimilarity(String text1, String text2, Class<? extends IDict> dictClass) throws Exception {
		
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
		everything = everything.replaceAll("^[0-9,;]+$"," ");
		
		
		br.close();	    
		// Retornamos un arreglo de las palabras
		return everything.split("\\s+");
	}
	
	
	public static float similarityFunction(
			String[] text1, 
			String[] text2, 
			Class<? extends IDict> dictClass)
					throws InstantiationException, IllegalAccessException {

		IDict text1Dict = dictClass.newInstance();
		IDict text2Dict = dictClass.newInstance();

		ArrayList<String> concatenatedTextUniqueWords = new ArrayList<String>();

		int numberOfWords = text1.length + text2.length;
		int numerator = 0;


		for (String word : text1) {
			if (text1Dict.search(word) <= 0 && text2Dict.search(word) <= 0) {
				concatenatedTextUniqueWords.add(word);
			}
			text1Dict.insert(word);
		}

		for (String word : text2) {
			if (text1Dict.search(word) <= 0 && text2Dict.search(word) <= 0) {
				concatenatedTextUniqueWords.add(word);
			}
			text2Dict.insert(word);
		}

		// Calculo Numerador Similaridad
		for (String word : concatenatedTextUniqueWords) {

			numerator += Math.abs( text1Dict.frequency(word) - text2Dict.frequency(word)); 
			//System.out.println("word: " + word + " frequency: " + numerator);
		}
		return 1 - numerator/(float)numberOfWords;
	}
}
