
/**
 * A WordGram represents a sequence of strings
 * just as a String represents a sequence of characters
 * 
 * @author Murulo Calegari
 *
 */
public class WordGram {
	
	private String[] myWords;
	private String myToString;  // cached string
	private int myHash;         // cached hash value

	/**
	 * Create WordGram by creating instance variable myWords and copying
	 * size strings from source starting at index start
	 * @param source is array of strings from which copying occurs
	 * @param start starting index in source for strings to be copied
	 * @param size the number of strings copied
	 */
	public WordGram(String[] source, int start, int size) {
		myWords = new String[size];

		for(int i = 0; i < size; i++) {
			myWords[i] = source[start + i];
		}

		myToString = null;
		myHash = 0;
	}

	/**
	 * Return string at specific index in this WordGram
	 * @param index in range [0..length() ) for string 
	 * @return string at index
	 */
	public String wordAt(int index) {
		if (index < 0 || index >= myWords.length) {
			throw new IndexOutOfBoundsException("bad index in wordAt "+index);
		}
		return myWords[index];
	}

	/**
	 * Returns the amount of words that makes this WordGram, that is its order
	 * @return the order of this WordGram
	 */
	public int length(){
		return myWords.length;
	}


	/**
	 * WordGrams are equal to each other if they have the same length and their compounding words
	 * are the same and in the same order
	 * @param other
	 * @return
	 */
	@Override
	public boolean equals(Object other) {
		if (! (other instanceof WordGram)){
			return false;
		}

		WordGram wg = (WordGram) other;

		if(this.length() != wg.length()) return false;

		for (int i = 0; i < myWords.length; i++) {
			if(!myWords[i].equals(wg.myWords[i])) return false;
		}

		return true;
	}

	@Override
	public int hashCode(){

		if(myHash == 0) {
			myHash = this.toString().hashCode();
		}

		return myHash;
	}
	

	/**
	 * Create and complete this comment
	 * @param last is last String of returned WordGram
	 * @return
	 */
	public WordGram shiftAdd(String last) {
		WordGram wg = new WordGram(myWords,0,myWords.length);

		for(int i = 1; i < wg.myWords.length; i++) {
			wg.myWords[i - 1] = wg.myWords[i];
		}

		wg.myWords[wg.myWords.length - 1] = last;

		return wg;
	}

	@Override
	public String toString(){

		if(myWords.length == 1) return myWords[0];

		if(myToString == null) {
			myToString = String.join(" ", myWords);
		}

		return myToString;
	}
}
