/**
 * simple reference solution for Part 2
 * @author ola
 *
 */
public class WordGram {
	private String[] myWords;
	private String myToString;
	private int myHash;

	public WordGram(String[] source, int start, int size) {
		myWords = new String[size];
		System.arraycopy(source, start, myWords, 0, size);
	}

	/**
	 * Return word given its index
	 * @param index of word
	 * @return string/word[index]
	 * @throws IndexOutOfBoundsException if index < 0 or index >= length()
	 */
	public String wordAt(int index) {
		if (index < 0 || index >= myWords.length) {
			throw new IndexOutOfBoundsException("bad index in wordAt "+index);
		}
		return myWords[index];
	}

	/**
	 * Returns # words in this WordGram
	 * @return order of wordgram, # words
	 */
	public int length(){
		return myWords.length;
	}

	@Override
	public boolean equals(Object o) {
		if (! (o instanceof WordGram) || o == null){
			return false;
		}

		WordGram other = (WordGram) o;
		if (this.length() != other.length()) {
			return false;
		}
		for(int k=0; k < myWords.length; k++) {
			if (! myWords[k].equals(other.wordAt(k))){
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode(){
		if (myHash == 0){
			myHash = toString().hashCode();
		}
		return myHash;
	}

	/**
	 * Return a new WordGram based on this one and last, new
	 * WordGram has words[1],2,...,length()-1, and then last.
	 * @param last added as last string of returned WordGram
	 * @return new WordGram as specified
	 */
	public WordGram shiftAdd(String last) {
		WordGram wg = new WordGram(myWords,0,myWords.length);
		for(int k=0; k < wg.myWords.length-1; k++){
			wg.myWords[k] = wg.myWords[k+1];
		}
		wg.myWords[wg.myWords.length-1] = last;
		return wg;
	}

	@Override
	public String toString(){
		if (myToString == null) {
			myToString = String.join(" ",myWords);
		}
		return myToString;
	}
}
