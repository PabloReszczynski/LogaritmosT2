import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Experiments {
    

	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException {

		//executeExperiments(PatriciaTree.class);
		//executeExperiments(TernaryTree.class);
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
		double searchTime;
		IDict dict;

		Random rand = new Random();
		
		// Experimento: Tiempo de construccion para Dark Tower.
		System.out.println("Tiempo de insercion de DarkTower para " + clss.getName() +" :\nPalabras\tTiempoInsercion\tTiempoBusqueda");
		for (int i = 10; i < 21; i++) {
			
			// Creamos/Vaciamos diccionarios - WarmUp
			dict = clss.newInstance();
			dict.insert("xyzw");
			
			// Medimos el tiempo de la insercion:
			time = System.currentTimeMillis();
			for (int j = 0; j <= Math.pow(2, i) - 1 && j < text1.length; j++) {
				String word = text1[j];
				dict.insert(word);
			}
			time = (System.currentTimeMillis() - time) / 1000;
			
			
			searchTime = searchExperiment(dict, text1, i, rand);
					
			// Imprimimos los resultados:
			System.out.println((int) Math.pow(2,i)+ "\t\t" + time + "\t\t" + searchTime );

			// Experimento: Guardar archivos para comprobar tamano.
			saveObject("DarkTower_" + clss.getName() + "_" + Double.toString(Math.pow(2, i)), dict);
		}
		
		
		// Experimento: Tiempo de construccion para The Expanse.
		System.out.println("\nTiempo de insercion de TheExpanse para " + clss.getName() +" :\nPalabras\tTiempoInsercion\tTiempoBusqueda");
		for (int i = 10; i < 21; i++) {
			
			// Creamos/Vaciamos diccionarios
			dict = clss.newInstance();
			// WarmUp
			dict.insert("xyzw");
			
			time = System.currentTimeMillis();
			for (int j = 0; j <= Math.pow(2, i) - 1 && j < text2.length; j++) {
				String word = text2[j];
				dict.insert(word);
			}
			time = (System.currentTimeMillis() - time) / 1000;
			
			
			searchTime = searchExperiment(dict, text2, i, rand);
			
			// Imprimimos los resultados:
			System.out.println((int) Math.pow(2,i)+ "\t\t" + time + "\t\t" + searchTime );
			
			// Experimento : Tamano del objeto creado para The Expanse
			saveObject("TheExpanse_" + clss.getName() + "_" + Double.toString(Math.pow(2, i)), dict);			
		}
		
		// Experimento: Tiempo de calculo de la similaridad.
		// Solo se mide la busqueda de las palabras y el calculo del indice, no la insercion.
		ArrayList<Double> similarityTimes = new ArrayList<Double>();
		System.out.println("Tiempo tomado para calculo de similaridad usando " + clss.getName() + "\nPalabras\tTiempo");
		for (int i = 10; i < 21; i ++) {
			
			time = (similarityExperiment (
					Arrays.copyOfRange(text1, 0, (int) Math.pow(2, i)),
					Arrays.copyOfRange(text2, 0, (int) Math.pow(2, i)),
					clss)) / 1000;
			
			System.out.println((int) Math.pow(2,i) + "\t\t" + time);
			
			similarityTimes.add(time);
		}
		
		
	}
	
	/*
	 * searchExperiment
	 * Experimento : elegir n/10 palabras aleatoriamente y buscarlas
	 */
	public static double searchExperiment(IDict dict, String[] text, int index, Random rand) {
		
	 
		int randomLenght = (int) (Math.pow(2, index)/10);
		double searchTime = 0;
		ArrayList<String> randomWordList = new ArrayList<String>();
		

		for (int k = 0; k < Math.pow(2, index)/10; k++) {
			randomWordList.add(text[rand.nextInt(randomLenght)]);
		}

		searchTime = System.nanoTime();

		for (String word : randomWordList) {
			dict.search(word);
		}

		return System.nanoTime() - searchTime;
	}
	
	
	/*
	 * testSimilarityTime
	 * Retorna el tiempo que se demora en calcular la similaridad entre dos textos,
	 * usando el diccionario especificado en el parametro.
	 */
	public static double similarityExperiment(
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
	
	// ------------------ Funciones Utilitarias -------------------
	
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

}
