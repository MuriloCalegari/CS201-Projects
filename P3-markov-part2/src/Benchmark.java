import java.io.*;
import java.util.*;

/**
 * Modified from Spring 2016 offering of Compsci 201
 * Rewritten for Fall 2018, updated Spring 2019
 * @author ola
 * @version 2.0
 */


public class Benchmark {
	
	private static final int TRIALS = 5; // number of trials for each run

	
	/**
	 * Returns the MarkovModel object used to benchmark, e.g., BaseMarkov
	 * or EfficientMarkov. Isolates dependency on the model to this single method.
	 * @param order of the markov model returned
	 * @return a model that implements the proper interface
	 */
	private static MarkovInterface<String> getMarkov(int order) {
		//return new BaseMarkov(order);
		return new EfficientMarkov(order);
	}
	
	/**
	 * Runs model based on parameters passed, returns mean and standard deviation
	 * in an array of two double values with mean in ret[0] and sigma in ret[1]
	 * @param size is size of random text generated
	 * @param source is string used for training
	 * @return mean and sigma/standard deviation in that order in double[]
	 * @throws Exception thrown if thread issues occur
	 */
	private static double[] benchmark(MarkovInterface<?> model,
			                          String source, int size) throws Exception {
		
		double[] times = new double[TRIALS];

		for (int i = 0; i < TRIALS; i++) {			
			double start = System.nanoTime();
			model.setTraining(source);
			Thread thread = new Thread(() -> {
				String dummy = model.getRandomText(size);
			});
			thread.run();
			thread.join();
			times[i] = (System.nanoTime() - start) / 1.0e9;
		}
		
		double mean = 0;
		for (int i = 0; i < TRIALS; i++) {
			mean += times[i];
		}
		mean /= TRIALS;
		
		double stddev = 0;
		for (int i = 0; i < TRIALS; i++) {
			stddev += Math.pow(times[i] - mean, 2);
		}
		stddev /= TRIALS - 1;
		
		return new double[] {mean, stddev};
	}
	
	/**
	 * Returns number of unique k-grams in a text.
	 * @param text is characters being analyzed
	 * @param order is order of k-gram being found
	 * @return number of unique k-grams in text
	 */
	public static int uniqueKeys(String text, int order) {
		HashSet<String> set = new HashSet<>();
		for(int k=0; k < text.length() - order + 1; k++) {
			String s = text.substring(k, k+order);
			set.add(s);
		}
		return set.size();
	}
	
	
	public static void main(String[] args) throws Exception {
		System.out.println("Starting tests\n");
		
		String fileName = "hawthorne.txt";
		File file = new File("data/"+fileName);
		double[] data;
		String source = TextSource.textFromFile(file);
		int[] sizes = {2000,4000,8000,16000,32000};
		int order = 5;
		MarkovInterface<String> model = getMarkov(order); 
		
		// call benchmark and ignore value, first trial generates bogus data
		data = benchmark(model,source,1000);
		
		// benchmark keeping N fixed, varying T: #random chars generated
		
		System.out.printf("time\tsource\t#chars\n");
		for(int size : sizes) {
			model.resetRandom();
			data = benchmark(model,source,size);
			System.out.printf("%1.3f\t%d\t%d\n", data[0],source.length(),size);
		}
		System.out.println();
		
		// benchmark keeping T constant, varying N (size of training)
		
		String text = source;
		String textForTrial = text;
		int tSize = 10;
		int tLength = 4096;
		for(int k=0; k < tSize; k += 1) {
			data = benchmark(model,textForTrial,tLength);
			System.out.printf("%1.3f\t%d\t%d\n", data[0],textForTrial.length(),tLength);
			textForTrial += text;
		}
	
		System.out.println();
		System.out.println("Finished tests");
	}
}