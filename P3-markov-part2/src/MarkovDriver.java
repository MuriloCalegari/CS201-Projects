import java.io.File;
import java.util.Scanner;
import java.util.*;

/**
 * Driver for Markov Model classes
 * @author ola
 * Modified for Fall 2021
 */

public class MarkovDriver {
	
	private static final int TEXT_SIZE = 144;
	
	public static void markovGenerate(MarkovInterface<?> markov, String text) {
		double start = System.nanoTime();
		for(int k=1; k <= 5; k++) {
			markov.setOrder(k);
			markov.resetRandom();
			markov.setTraining(text);
			String random = markov.getRandomText(TEXT_SIZE);
			System.out.printf("%d markov model with %d chars\n", k,random.length());
			printNicely(random,60);
		}
		double end = System.nanoTime();
		System.out.printf("total time = %2.3f\n", (end-start)/1e9);
	}
	
	public static void main(String[] args) {

		String filename = "data/biden-2021.txt";

		if (args.length > 0) {
			filename = args[1];
		}
		
		File f = new File(filename);
		String text = TextSource.textFromFile(f);

		// only one line below should be uncommented
		//MarkovInterface<String> standard = new BaseMarkov();
		//MarkovInterface<String> efficient = new EfficientMarkov();
		//MarkovInterface<WordGram> wmm = new BaseWordMarkov();
		MarkovInterface<WordGram> ewm = new EfficientWordMarkov();

		// first parameter is one of the MarkovInterface variables
		markovGenerate(ewm,text);
	}

	private static void printNicely(String random, int screenWidth) {
		String[] words = random.split("\\s+");
		int psize = 0;
		System.out.println("----------------------------------");
		for(int k=0; k < words.length; k++){
			System.out.print(words[k]+ " ");
			psize += words[k].length() + 1;
			if (psize > screenWidth) {
				System.out.println();
				psize = 0;
			}
		}
		System.out.println("\n----------------------------------");
	}
}
