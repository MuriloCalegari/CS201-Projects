import java.util.Iterator;


/**
 * Simple but somewhat efficient implementation of IDnaStrand. \ This
 * implementation uses StringBuilders to represent genomic/DNA data.
 * 
 * @author ola
 * @date January 2008, modified and commented September 2008
 * @date October 2011, made myInfo a StringBuilder rather than a String
 * @date October 2011, modified to add new methods and remove old ones
 * @date October 2016, updated to implement new interface
 */

public class StringBuilderStrand implements IDnaStrand {
	
	private StringBuilder myInfo;
	private int myAppends;

	public StringBuilderStrand(){
		this("");
	}
	/**
	 * Create a strand representing s. No error checking is done to see if s
	 * represents valid genomic/DNA data.
	 * 
	 * @param s
	 *            is the source of cgat data for this strand
	 */

	public StringBuilderStrand(String s) {
		initialize(s);
	}

	/**
	 * Initialize this strand so that it represents the value of source. No
	 * error checking is performed.
	 * 
	 * @param source
	 *            is the source of this enzyme
	 */
	@Override 
	public void initialize(String source) {
		myInfo = new StringBuilder(source);
		myAppends = 0;
	}

	/**
	 * @return number of base-pairs in this strand
	 */
	@Override
	public long size() {
		return myInfo.length();
	}

	@Override
	public String toString() {
		return myInfo.toString();
	}

	/**
	 * Simply append a strand of dna data to this strand. No error checking is
	 * done. This method isn't efficient; it doesn't use a StringBuilder or a
	 * StringBuffer.
	 * 
	 * @param dna
	 *            is the String appended to this strand
	 */
	public IDnaStrand append(String dna) {
		myInfo.append(dna);
		myAppends++;
		return this;
	}

	public IDnaStrand reverse() {
		StringBuilder copy = new StringBuilder(myInfo);
		StringBuilderStrand ss = new StringBuilderStrand("replace");
		copy.reverse();
		ss.myInfo = copy;
		return ss;
	}

	@Override
	public int getAppendCount() {
		return myAppends;
	}
 
	public char charAt(int index) {
		return myInfo.charAt(index);
	}
	
	@Override
	public IDnaStrand getInstance(String source) {

		return new StringBuilderStrand(source);
	}
}
