import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class TestHashListAutocomplete {

    private Term[] myTerms = new Term[] { new Term("ape", 0), new Term("apple", 0), new Term("bat", 0),
            new Term("bee", 0), new Term("cat", 0) };
    private String[] myNames = { "ape", "app", "ban", "bat", "bee", "car", "cat" };
    private double[] myWeights = { 6, 4, 2, 3, 5, 7, 1 };

    /** A comparator which considers all terms equal **/
    public class AllEqual implements Comparator<Term> {
        public int compare(Term o1, Term o2) {
            return 0;
        }
    }

    /**
     * A comparator which is basically Term.PrefixOrder, but counts the number
     * of compare calls made
     */
    public class CompareCounter implements Comparator<Term> {

        private PrefixComparator comparator;
        private int count = 0;

        public CompareCounter(int r) {
            comparator = PrefixComparator.getComparator(r);
        }

        @Override
        public int compare(Term o1, Term o2) {
            count++;
            return comparator.compare(o1, o2);
        }

        public int compareCount() {
            return count;
        }

    }

    /**
     * Sorts terms by ascending weight
     */
    public class WeightSorter implements Comparator<Term> {

        @Override
        public int compare(Term o1, Term o2) {
            return (int) (100 * (o1.getWeight() - o2.getWeight()));
        }

    }

    public Autocompletor getInstance() {
        return getInstance(myNames, myWeights);
    }

    public Autocompletor getInstance(String[] names, double[] weights) {
        return new HashListAutocomplete(names, weights);
    }

    private String[] terms2strings(List<Term> list) {
        String[] ret = new String[list.size()];
        for(int k=0; k < list.size(); k++) {
            ret[k] = list.get(k).getWord();
        }
        return ret;
    }

    /**
     * Tests correctness of topKMatches() for a few simple cases
     */
    @Test
    public void testTopKMatches() {
        Autocompletor test = getInstance();
        String[] queries = { "", "", "", "", "a", "ap", "b", "ba", "d" };
        int[] ks = { 8, 1, 2, 3, 1, 1, 2, 2, 100 };
        String[][] results = { { "car", "ape", "bee", "app", "bat", "ban", "cat" }, { "car" }, { "car", "ape" },
                { "car", "ape", "bee" }, { "ape" }, { "ape" }, { "bee", "bat" }, { "bat", "ban" }, {} };
        for (int i = 0; i < queries.length; i++) {
            String query = queries[i];
            String[] reported = terms2strings(test.topMatches(query, ks[i]));
            String[] actual = results[i];
            assertArrayEquals(actual, reported,"wrong top matches for " + query + " " + ks[i]);
        }
    }

    /**
     * Tests that constructor throws the correct exceptions
     */
    @Test
    public void testConstructorException(){
        try{
            Autocompletor test = getInstance(null, myWeights);
            fail("No exception thrown");
        }
        catch(NullPointerException e){
        }
        catch(Throwable e){
            fail("Wrong throw");
        }
        try{
            Autocompletor test = getInstance(myNames, null);
            fail("No exception thrown");
        }
        catch(NullPointerException e){
        }
        catch(Throwable e){
            fail("Wrong throw");
        }
    }
}
