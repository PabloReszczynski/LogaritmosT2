import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Experiments {
	
	public static void main(String[] args) throws IOException {
		
		
		// TernaryTree Similarity:
		
		String [] text1 = readTextFile("DarkTower.txt");
		String [] text2 = readTextFile("TheExpanse.txt");

		
		
		
		
		
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
	
	public static double testSimilarityTime(
			String[] text1, 
			String[] text2, 
			Class<? extends IDict> dictClass)
					throws InstantiationException, IllegalAccessException {

		IDict text1Dict = dictClass.newInstance();
		IDict text2Dict = dictClass.newInstance();
		
		double deltaT = 0;
		@SuppressWarnings("unused")
		float similarity = 0;

		ArrayList<String> concatenatedTextUniqueWords = new ArrayList<String>();
		
		int numberOfWords = text1.length + text2.length;
		int numerator = 0;

		
		// Comenzamos a contar el tiempo:
		deltaT = System.nanoTime();

		// Agregamos palabras del texto 1 al diccionario 1
		for (String word : text1) {
			if (text1Dict.search(word) == 0 && text2Dict.search(word) == 0) {
				concatenatedTextUniqueWords.add(word);
			}
			text1Dict.insert(word);
		}

		// Agregamos palabras del texto 2 al diccionario 2
		for (String word : text2) {
			if (text1Dict.search(word) == 0 && text2Dict.search(word) == 0) {
				concatenatedTextUniqueWords.add(word);
			}
			text2Dict.insert(word);
		}

		// Calculo Numerador Similaridad
		for (String word : concatenatedTextUniqueWords) {
			numerator += Math.abs( text1Dict.frequency(word) - text2Dict.frequency(word)); 
		}
		
		similarity = 1 - numerator/(float)numberOfWords;
		
		return System.nanoTime() - deltaT;
	}

}
