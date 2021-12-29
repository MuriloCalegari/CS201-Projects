import java.util.Comparator;

/**
 * Factor pattern for obtaining PrefixComparator objects
 * without calling new. Users simply use
 *
 *     Comparator<Term> comp = PrefixComparator.getComparator(size)
 *
 * @author owen astrachan
 * @date October 8, 2020
 */
public class PrefixComparator implements Comparator<Term> {

    private int myPrefixSize; // size of prefix

    /**
     * private constructor, called by getComparator
     * @param prefix is prefix used in compare method
     */
    private PrefixComparator(int prefix) {
        myPrefixSize = prefix;
    }


    /**
     * Factory method to return a PrefixComparator object
     * @param prefix is the size of the prefix to compare with
     * @return PrefixComparator that uses prefix
     */
    public static PrefixComparator getComparator(int prefix) {
        return new PrefixComparator(prefix);
    }



    /**
     * Use at most myPrefixSize characters from each of v and w
     * to return a value comparing v and w by words. Comparisons
     * should be made based on the first myPrefixSize chars in v and w.
     * @return < 0 if v < w, == 0 if v == w, and > 0 if v > w
     */
    @Override
    public int compare(Term v, Term w) {

        String wordV = v.getWord();
        String wordW = w.getWord();

        for(int i = 0; i < myPrefixSize; i++) {

            // Scenario where we are out of bounds for some word,
            // Since we skip equal characters, this is only reached
            // when the two prefixes are the same, but we want to
            // return the smaller one.
            if(i == wordV.length() || i == wordW.length()) {
                return wordV.length() - wordW.length();
            }

            // Comparison is done when characters are different
            if(wordV.charAt(i) == wordW.charAt(i)) continue;

            // Only reached for different character,
            // effectively comparing the two terms
            return wordV.charAt(i) - wordW.charAt(i);
        }

        // Reached when all first myPrefixSize chars are
        // equal to one another and
        // min(wordV.length(), wordW.length()) > myPrefixSize
        return 0;
    }
}
