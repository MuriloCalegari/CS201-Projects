import java.util.ArrayList;

/**
 * Interface for Compsci 201 Markov Assignment. 
 * Note that the order of the Markov model would be 
 * typically set via a constructor.
 * 
 * @author ola
 *
 * @param <Type> should be String or WordGram
 */

public interface MarkovInterface<Type> {
	
	/**
	 * Set the training text for subsequent random text generation.
	 * @param text is the training text
	 */
	public void setTraining(String text);
	
	/**
	 * Get randomly generated text based on the training text
	 * and order.
	 * @param length is the number of characters or words generated, this is a maximum
	 * since if EOS encountered possibly fewer items than length will be generated
	 * @return randomly generated text 
	 */
	public String getRandomText(int length);
	
	/**
	 * Really a helper method, but must be public to be part of interface. Used
	 * to get all the characters or strings that follow a key. Returns an
	 * ArrayList of the following items. 
	 * @param key is key being searched for in training text
	 * @return a list of items that follow key: single-character strings
	 * if <Type> is String and Strings if <Type> is WordGram
	 */
	public ArrayList<String> getFollows(Type key);
	
	/**
	 * returns the order of this Markov Model, typically set at construction 
	 * @return the order of this model
	 */
	public int getOrder();
	
	/**
	 * sets the order of this Markov Model, typically set at construction
	 * @param order is the new order of this model 
	 * @return the previous order of this model
	 */
	public int setOrder(int order);
	
	/**
	 * Reset the random number generator using
	 * an internal seed, e.g., as specified previously
	 * using @{setSeed}
	 */
	public void resetRandom();
	
	/**
	 * Sets the seed and initializes the random 
	 * number generator
	 * @param seed initial seed for java.util.Random
	 */
	public void setSeed(long seed);
}
