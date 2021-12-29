import java.util.*;

/**
 * Base class for Markov text generation. This version
 * rescans the training text for each randomly-generated
 * character. This makes generating N characters an O(T*N) 
 * task where T is the size of the training text
 * 
 * This class is designed to be extended with state
 * protected rather than private
 * 
 * @author ola
 *
 */

public class BaseMarkov  implements MarkovInterface<String> {
	protected String myText;
	protected Random myRandom;
	protected int myOrder;
	protected static String PSEUDO_EOS = "";
	protected static long RANDOM_SEED = 1234;
	
	/**
	 * Construct a BaseMarkov object with
	 * the specified order
	 * @param order size of this markov generator
	 */
	public BaseMarkov(int order) {
		myOrder = order;
		myRandom = new Random(RANDOM_SEED);
	}
	
	/**
	 * Default constructor has order 3
	 */
	public BaseMarkov() {
		this(3);
	}
	
	/**
	 * Change the order of this markov generator
	 * @param order is the new order
	 * @return the previous order
	 */
	public int setOrder(int order) {
		int old = myOrder;
		myOrder = order;
		return old;
	}
	
	@Override
	public String getRandomText(int length) {
		StringBuilder sb = new StringBuilder();
		int index = myRandom.nextInt(myText.length() - myOrder + 1);

		String current = myText.substring(index, index + myOrder);
		sb.append(current);
		
		for(int k=0; k < length-myOrder; k += 1){
			ArrayList<String> follows = getFollows(current);
			if (follows.size() == 0){
				break;
			}
			index = myRandom.nextInt(follows.size());
			
			String nextItem = follows.get(index);
			if (nextItem.equals(PSEUDO_EOS)) {
				break;
			}
			sb.append(nextItem);
			current = current.substring(1)+ nextItem;
		}		
		return sb.toString();
	}
	
	@Override
	public ArrayList<String> getFollows(String key){
		ArrayList<String> follows = new ArrayList<>();
		
		int pos = 0;  // location where search for key in text starts
		
		while (pos < myText.length()){
			int start = myText.indexOf(key,pos);
			if (start == -1){
				//System.out.println("didn't find "+key);
				break;
			}
			if (start + key.length() >= myText.length()){
				//System.out.println("found end with "+key);
				follows.add(PSEUDO_EOS);
				break;
			}
			// next line is string equivalent of myText.charAt(start+key.length())
			String next = myText.substring(start+key.length(), start+key.length()+1);
			follows.add(next);
			pos = start+1;  // search continues after this occurrence
		}
		return follows;
	}

	@Override
	public int getOrder() {
		return myOrder;
	}

	public void setSeed(long seed) {
		RANDOM_SEED = seed;
		myRandom = new Random(RANDOM_SEED);	
	}
	
	@Override
	public void resetRandom() {
		myRandom.setSeed(RANDOM_SEED);
	}

	@Override
	public void setTraining(String text) {
		myText = text;
	}
	
}
