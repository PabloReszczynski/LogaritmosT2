import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class Experiments {
    

	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException {
		
		//executeExperiments(PatriciaTree.class);
		executeExperiments(TernaryTree.class);
		executeExperiments(HashMap.class);

	}
	
	/*
	 * executeExperiments
	 * Ejecuta los experimentos pedidos en el enunciado de la tarea. Toma como parametro la clase del diccionario.
	 * 
	 */
	public static void executeExperiments(Class<? extends IDict> clss) 
			throws InstantiationException, IllegalAccessException, IOException {
		
		// Lectura de los archivos de texto. Por defecto, DarkTower y TheExpanse.
		String [] text1 = readTextFile("DarkTower.txt");
		String [] text2 = readTextFile("TheExpanse.txt");
		
		double time;
		IDict dict;
		
		// Experimento: Tiempo de construccion para Dark Tower.
		ArrayList<Double> fillTimes = new ArrayList<Double>();
		for (int i = 10; i < 21; i++) {
			
			//Creamos /Vaciamos diccionarios
			dict = clss.newInstance();
			// WarmUp
			dict.insert("xyzw");
			
			time = System.currentTimeMillis();
			
			for (int j = 0; j <= Math.pow(2, i) - 1 && j < text1.length; j++) {
				String word = text1[j];
				dict.insert(word);
			}
			
			time = (System.currentTimeMillis() - time) / 1000;
			

			saveObject(clss.getName() + "_" + Double.toString(Math.pow(2, i)), dict);
					
			
			System.out.println("Tiempo de insercion de "+ clss.getName() +
					" usando DarkTower para " + (int) Math.pow(2,i) + " palabras : " + time);
			fillTimes.add(time);
		}
		
		
		// Experimento: Tiempo de construccion para The Expanse.
		for (int i = 10; i < 21; i++) {
			
			//Creamos /Vaciamos diccionarios
			dict = clss.newInstance();
			// WarmUp
			dict.insert("xyzw");
			
			time = System.currentTimeMillis();
			
			for (int j = 0; j <= Math.pow(2, i) - 1 && j < text2.length; j++) {
				String word = text2[j];
				dict.insert(word);
			}
			
			time = (System.currentTimeMillis() - time) / 1000;
			
			
			// Experimento : Tamano del objeto creado para Dark Tower
			if (clss.getName() == "TernaryTree") {
				saveObject("TernaryTree_" + Double.toString(Math.pow(2, i)), dict);
			}
			
			
			System.out.println("Tiempo de insercion de "+ clss.getName() + 
					" usando The Expanse para " + (int) Math.pow(2,i) + " palabras : " + time);
			fillTimes.add(time);
		}
		
		
		
		// Experimento: Tiempo de calculo de la similaridad.
		// Solo se mide la busqueda de las palabras y el calculo del indice, no la insercion.
		ArrayList<Double> similarityTimes = new ArrayList<Double>();
		for (int i = 10; i < 21; i ++) {
			
			time = (testSimilarityTime (
					Arrays.copyOfRange(text1, 0, (int) Math.pow(2, i)),
					Arrays.copyOfRange(text2, 0, (int) Math.pow(2, i)),
					clss)) / 1000;
			
			System.out.println("Tiempo de calculo de similaridad en " + clss.getName() + " para " + (int) Math.pow(2,i) + " palabras: " + time);
			
			similarityTimes.add(time);
		}
	}
	
	/*
	 * readTextFile
	 * Lee el archivo dado en filename, extrae sus palabras y lo retorna como arreglo de strings.
	 */
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
	
	public static void saveObject(String filename, Object o) throws IOException {
		FileOutputStream fos = new FileOutputStream("results/" + filename + ".ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(o);
        oos.close();
	}
	
	/*
	 * testSimilarityTime
	 * Retorna el tiempo que se demora en calcular la similaridad entre dos textos,
	 * usando el diccionario especificado en el parametro.
	 */
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
		

		// Comenzamos a contar el tiempo:
		deltaT = System.currentTimeMillis();

		// Calculo Numerador Similaridad
		for (String word : concatenatedTextUniqueWords) {
			numerator += Math.abs( text1Dict.frequency(word) - text2Dict.frequency(word)); 
		}
		
		similarity = 1 - numerator/(float)numberOfWords;
		
		return System.currentTimeMillis() - deltaT;
	}

}
