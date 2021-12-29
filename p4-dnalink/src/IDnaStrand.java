import java.util.Iterator;

/**
 * Interface for DNA/strand experiments
 * 
 * @author Owen Astrachan
 * @date February, 2008
 * @date October, 2011, removed cutWith and added reverse
 * @date October, 2016, removed methods that aren't used in assignment,
 * also added .charAt(int index) and .getInstance() 
 * @date October, 2017, added cutAndSplice here
 * @date October 2018, added codonProfile
 * @date 2020, removed codonProfile
 */
public interface IDnaStrand extends Iterable<Character>{
	/**
	 * Cut this strand at every occurrence of enzyme, essentially replacing
	 * every occurrence of enzyme with splicee.
	 * 
	 * @param enzyme
	 *            is the pattern/strand searched for and replaced
	 * @param splicee
	 *            is the pattern/strand replacing each occurrence of enzyme
	 * @return the new strand leaving the original strand unchanged.
	 */
	default IDnaStrand cutAndSplice(String enzyme, String splicee) {
		int pos = 0;
		int start = 0;
		String search = this.toString();
		boolean first = true;
		IDnaStrand ret = null;

		while ((pos = search.indexOf(enzyme, start)) >= 0) {
			if (first) {
				ret = getInstance(search.substring(start, pos));
				first = false;
			} else {
				ret.append(search.substring(start, pos));

			}
			start = pos + enzyme.length();
			ret.append(splicee);
		}

		if (start < search.length()) {
			// NOTE: This is an important special case! If the enzyme
			// is never found, return an empty String.
			if (ret == null) {
				ret = getInstance("");
			} else {
				ret.append(search.substring(start));
			}
		}
		return ret;
	}

	/**
	 * Returns the number of elements/base-pairs/nucleotides in this strand.
	 * @return the number of base-pairs in this strand
	 */
	public long size();

	/**
	 * Initialize by copying DNA data from the string into this strand,
	 * replacing any data that was stored. The parameter should contain only
	 * valid DNA characters, no error checking is done by the this method.
	 * 
	 * @param source
	 *            is the string used to initialize this strand
	 */
	public void initialize(String source);

	/**
	 * Return this object, useful to obtain
	 * an object without knowing its type, e.g.,
	 * calling dna.getInstance() returns an IDnaStrand
	 * that will be the same concrete type as dna
	 * @param source is data from which object constructed
	 * @return an IDnaStrand whose .toString() method will be source
	 */
	public IDnaStrand getInstance(String source);
	
	/**
	 * Return some string identifying this class and
	 * internal tracked data, e.g., append calls.
	 * @return a string representing this strand and its characteristics
	 */
	default public String strandInfo() {
		return this.getClass().getName();
	}

	/**
	 * Append dna to the end of this strind.
	 * @param dna
	 *            is the string appended to this strand
	 * @return this strand after the data has been added
	 */
	public IDnaStrand append(String dna);

	/**
	 * Returns an IDnaStrand that is the reverse of this strand, e.g., for
	 * "CGAT" returns "TAGC"
	 * 
	 * @return reverse strand
	 */
	public IDnaStrand reverse();

	/**
	 * Returns the number of times append has been called.
	 * 
	 * @return
	 */
	public int getAppendCount();
	
	/**
	 * Returns character at a specified index, where 0 <= index < size()
	 * @param index specifies which character will be returned
	 * @return the character at index
	 * @throws IndexOutOfBoundsException if index < 0 or inde >= size()
	 */
	public char charAt(int index);
	
	/**
	 * Satisfy the Iterable<Character> interface
	 * @return an iterator over this DNA sequence
	 */
	default Iterator<Character> iterator(){
		return new CharDnaIterator(this);
	}
	
}
