import java.util.*;

/**
 * Similar to BaseMarkov, but uses WordGram objects instead of String
 * objects to generate random text.
 * 
 * @author ola
 *
 */
public class BaseWordMarkov implements MarkovInterface<WordGram> {
	
	protected String[] myWords;
	protected Random myRandom;
	protected int myOrder;
	protected static String PSEUDO_EOS = "";
	protected static long RANDOM_SEED = 1234;
	
	public BaseWordMarkov() {
		this(2);
	}
	
	public BaseWordMarkov(int order){
		myOrder = order;
		myRandom = new Random(RANDOM_SEED);
	}
	

	@Override
	public void setTraining(String text){
		myWords = text.split("\\s+");
	}

	/**
	 * Find and return the first index >= start in words at which target occurs.
	 * Each target.length() sequence of strings is converted to a WordGram
	 * which is tested against target 
	 * @param words is the sequence of words being searched
	 * @param target is the WordGram being searched for
	 * @param start index in array words at which search begins
	 * @return index of first occurrence of target (>= start) or -1
	 * if not found
	 */
	protected int indexOf(String[] words, WordGram target, int start){
		int size = target.length();
		for(int k=start; k < words.length - size + 1; k += 1){

			WordGram current = new WordGram(words,k,size);
			if (current.equals(target)) {
				return k;
			}
		}
		return -1;
	}

	@Override
	public ArrayList<String> getFollows(WordGram kGram) {

		int pos = 0;            
		ArrayList<String> follows = new ArrayList<String>();
		while (true) {
			int index = indexOf(myWords,kGram,pos);
			if (index == -1) {
				break;
			}
			int start = index + kGram.length();
			if (start >= myWords.length) {
				follows.add(PSEUDO_EOS);
				break;
			}
			
			follows.add(myWords[start]);
			pos = index+1;
		}
		return follows;
	}

	@Override
	public String getRandomText(int length){
		ArrayList<String> sb = new ArrayList<>();
		int index = myRandom.nextInt(myWords.length - myOrder + 1);
		WordGram current = new WordGram(myWords,index,myOrder);
		
		sb.add(current.toString());
		for(int k=0; k < length-myOrder; k += 1){
			ArrayList<String> follows = getFollows(current);
			if (follows.size() == 0){
				break;
			}
			index = myRandom.nextInt(follows.size());
			
			String nextItem = follows.get(index);
			if (nextItem.equals(PSEUDO_EOS)) {
				//System.out.println("PSEUDO");
				break;
			}
			sb.add(nextItem);
			current = current.shiftAdd(nextItem);
		}
		return String.join(" ", sb);
	}
	@Override
	public int getOrder() {
		return myOrder;
	}

	@Override
	public void setSeed(long seed) {
		RANDOM_SEED = seed;
		myRandom.setSeed(seed);
	}
	
	@Override
	public void resetRandom() {
		myRandom.setSeed(RANDOM_SEED);
	}
	
	@Override
	public int setOrder(int order) {
		int old = myOrder;
		myOrder = order;
		return old;
	}

}
