import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.Iterator;

/**
 * Class for running JUNit tests with different implementations of IDnaStrand.
 * To use a different implementation alter the method <code>getNewStrand</code>
 * since that method is called by all JUnit tests to create the IDnaStrand
 * objects being tested.
 * 
 * @author ola
 * @date January 2008, modified and commented in September 2008
 * @date January 2009, added splice testing
 * @date October 2015, added nodeList
 * @date October 2016, updated for iterator and no "" strings
 * @date September 2020, updated by Charles Lyu
 */

public class TestStrand {
	private static String[] strs = { "aggtccg", "aaagggtttcccaaagggtttccc", "a", "g",
			"aggtccgttccggttaaggagagagagagagttt" };

	/**
	 * Return a strand to test by other JUnit tests
	 * 
	 * @param s
	 *            is the string modeled by an IDnaStrand implementation
	 * @return an IDnaStrand object for testing in this JUnit testing class.
	 */
	public IDnaStrand getNewStrand(String s) {
		//return new StringStrand(s);
		return new LinkStrand(s);
		//return new StringBuilderStrand(s);
	}

	/**
	 * This test checks if .size() returns the correct value for basic cases"
	 */
	@Test
	public void testSize() {
		for (String s : strs) {
			final IDnaStrand strand = assertTimeout(Duration.ofMillis(10000),()->{
				IDnaStrand str = getNewStrand(s);
				return str;
			});
			assertEquals(s.length(), strand.size(),"This test checks if .size() returns the correct value"
					+ " for basic cases. Your code did not return the correct .size() for strand " + s);
		}
	}

	@Test
	public void testReverseSize() {
		for (String s : strs) {
			final IDnaStrand strand = assertTimeout(Duration.ofMillis(10000),()->{
				IDnaStrand str = getNewStrand(s);
				return str.reverse();
			});
			assertEquals(s.length(), strand.size(),"This test checks if .size() returns the correct value"
					+ " for reverse cases. Your code did not return the correct .size() for reversed strand " + s);
		}
	}

	/**
	 * This test checks if toString works correctly for basic cases.
	 */
	@Test
	public void testToString() {
		for (String s : strs) {
			final IDnaStrand strand = assertTimeout(Duration.ofMillis(10000),()->{
				IDnaStrand str = getNewStrand(s);
				return str;
			});
			assertEquals(s, strand.toString(),"This test checks if toString works correctly for "
					+ "basic cases. Your code did not return the correct .toString() for strand " + s);
		}
	}

	/** This test checks if initializeFrom works correctly for basic cases */
	@Test
	public void testInitialize() {
		for (String s : strs) {
			final IDnaStrand strand = assertTimeout(Duration.ofMillis(10000),()->{
				IDnaStrand str = getNewStrand("");
				str.initialize(s);
				return str;
			});
			assertEquals(s.length(), strand.size(),"This test checks if initialize works correctly"
					+ " for basic cases. Your code did not give the correct size() after calling initialize(" + s + ")");
			assertEquals( s, strand.toString(),"This test checks if initialize works correctly"
					+ " for basic cases. Your code did not give the correct toString() after calling initialize(" + s + ")");
		}
	}

	/**
	 * This test checks if the number of appends is the same after reversing a IDnaStrand
	 */
	@Test
	public void testReverseAppends() {
		IDnaStrand str = getNewStrand(strs[0]);
		int appends = str.getAppendCount();
		IDnaStrand rev = str.reverse();
		assertEquals(appends, rev.getAppendCount(), "This checks that you didn't modify append during reverse."
				+ " Make sure not to update myAppends when reversing!");
	}

	/**
	 * This test checks if reverse works correctly for strands with a single node
	 */
	@Test
	public void testReverseSingle() {
		for (String s : strs) {
			final IDnaStrand strand = assertTimeout(Duration.ofMillis(10000),()->{
				IDnaStrand str = getNewStrand(s);
				IDnaStrand rev = str.reverse();
				return rev;
			});
			String rs = new StringBuilder(s).reverse().toString();
			assertEquals(rs, strand.toString(), "This test checks if reverse works correctly for "
					+ "strands with a single node. Your code did not give the correct toString() after reversing " + s);
		}
	}

	/**
	 * This test checks if reverse works correctly for strands with multiple nodes
	 */
	@Test
	public void testReverseMulti() {
		// Two nodes
		String a = "actgcaggttaag";
		for (String s : strs) {
			final IDnaStrand strand = assertTimeout(Duration.ofMillis(10000),()->{
				IDnaStrand str = getNewStrand(s);
				str.append(a);
				IDnaStrand rev = str.reverse();
				return rev;
			});
			String rs = new StringBuilder(s).append(a).reverse().toString();
			assertEquals(rs, strand.toString(), "This test checks if reverse works correctly for "
					+ "strands with two nodes. Your code did not give the correct toString() after " +
					"reversing a strand with nodes " + s + ", " + a);
		}

		// Three nodes
		String b = "tttttccgaaaggc";
		for (String s : strs) {
			final IDnaStrand strand = assertTimeout(Duration.ofMillis(10000),()->{
				IDnaStrand str = getNewStrand(s);
				str.append(a);
				str.append(b);
				IDnaStrand rev = str.reverse();
				return rev;
			});
			String rs = new StringBuilder(s).append(a).append(b).reverse().toString();
			assertEquals(rs, strand.toString(), "This test checks if reverse works correctly for "
					+ "strands with three nodes. Your code did not give the correct toString() after " +
					"reversing a strand with nodes " + s + ", " + a + ", " + b);
		}
	}

	@Test
	public void testReverseMultiSize() {
		// Two nodes
		String a = "actgcaggttaag";
		for (String s : strs) {
			final IDnaStrand strand = assertTimeout(Duration.ofMillis(10000),()->{
				IDnaStrand str = getNewStrand(s);
				str.append(a);
				IDnaStrand rev = str.reverse();
				return rev;
			});
			String rs = new StringBuilder(s).append(a).reverse().toString();
			assertEquals(rs.length(), strand.size(), "This test checks if reverse SIZE works correctly for "
					+ "strands with two nodes. Your code did not give the correct size() after " +
					"reversing a strand with nodes " + s + ", " + a);
		}

		// Three nodes
		String b = "tttttccgaaaggc";
		for (String s : strs) {
			final IDnaStrand strand = assertTimeout(Duration.ofMillis(10000),()->{
				IDnaStrand str = getNewStrand(s);
				str.append(a);
				str.append(b);
				IDnaStrand rev = str.reverse();
				return rev;
			});
			String rs = new StringBuilder(s).append(a).append(b).reverse().toString();
			assertEquals(rs.length(), strand.size(), "This test checks if reverse SIZE works correctly for "
					+ "strands with three nodes. Your code did not give the correct size() after " +
					"reversing a strand with nodes " + s + ", " + a + ", " + b);
		}
	}

	@Test
	/** This test checks if append works correctly for simple cases */
	public void testAppend() {
		String app = "gggcccaaatttgggcccaaattt";
		for (String s : strs) {
			final IDnaStrand strand = assertTimeout(Duration.ofMillis(10000),()->{
				IDnaStrand str = getNewStrand(s);
				str.append(app);
				return str;
			});
			assertEquals(s + app, strand.toString(),
					"This test checks if append works correctly for "
							+ "simple cases. Your code did not give the correct toString() after "
							+ "appending " + app + " to " + s
					);
			assertEquals(s.length() + app.length(), strand.size(),
					"This test checks if append works correctly for "
							+ "simple cases. Your code did not give the correct size() after "
							+ "appending " + app + " to " + s
					);
		}
	}

	@Test
	/** This test checks if append works correctly when called multiple times */
	public void testAppendMulti() {
		String a = "gggcccaaatttgggcccaaattt";
		String b = "acgacttcg";
		String c = "aaggttc";
		for (String s : strs) {
			final IDnaStrand strand = assertTimeout(Duration.ofMillis(10000),()->{
				IDnaStrand str = getNewStrand(s);
				str.append(a);
				str.append(b);
				str.append(c);
				return str;
			});
			assertEquals(s + a + b + c, strand.toString(),
					"This test checks if append works correctly when called multiple "
							+ "times. Your code did not give the correct toString() after appending "
							+ a + ", " + b + ", " + c + " to " + s
			);
			assertEquals(s.length() + a.length() + b.length() + c.length(), strand.size(),
					"This test checks if append works correctly when called multiple "
							+ "times. Your code did not give the correct size() after appending "
							+ a + ", " + b + ", " + c + " to " + s
			);
		}
	}

	/**
	 * This test checks if cutAndSplice works correctly for simple cases
	 */
	/*@Test
	public void testSplice() {
		String r = "gat";
		String sp = "xxyyzz";
		String[] strands = { "ttgatcc", "tcgatgatgattc", 
				             "tcgatctgatttccgatcc", "gat",
				             "gatctgatctgat", "gtacc",
				             "gatgatgat" };
		String[] recombs = { "ttxxyyzzcc", "tcxxyyzzxxyyzzxxyyzztc", 
				             "tcxxyyzzctxxyyzzttccxxyyzzcc", "xxyyzz",
				             "xxyyzzctxxyyzzctxxyyzz", "","xxyyzzxxyyzzxxyyzz" };

		for (int k = 0; k < strands.length; k++) {
			IDnaStrand str = getNewStrand(strands[k]);
			String bef = str.toString();
			final IDnaStrand strand = assertTimeout(Duration.ofMillis(10000),()->{
				IDnaStrand rec = str.cutAndSplice(r, sp);
				return rec;
			});
			assertEquals(recombs[k], strand.toString(),
					"This test checks if cutAndSplice works correctly for "
							+ "simple cases. The test case failed on was splicing " + sp + " into " + strands[k]
					);
			assertEquals(bef, str.toString(),
					"This test checks if cutAndSplice works correctly for "
							+ "simple cases. The test case failed on was splicing " + sp + " into " + strands[k]
					);
		}
	}*/
	
	/**
	 *	Checks if iterator methods are implemented correctly
	 */
	@Test
	public void testIterator() {
		IDnaStrand test = getNewStrand(strs[0]);
		for (int i = 1; i < strs.length; i++) {
			test.append(strs[i]);
		}
		String all = test.toString();
		Iterator<Character> itc = test.iterator();

		for (int i = 0; i < all.length(); i++) {
			final int index = i;
			Assertions.assertAll("iterator output",
			()->assertTrue(itc.hasNext(),"hasNext() returned false when it should be true, when iterating through" +
					" index "+index+" of "+all.length() + ". This is typically due to errors with myIndex."),
			()->assertEquals(all.charAt(index), itc.next(),
					"charAt(" + index + ") does not match the expected character"));
		}
		
		assertFalse(itc.hasNext(),"hasNext() returned true when it should be false, after iterating " +
				"through the entire strand. This is typically due to errors with myIndex.");
	}
}
