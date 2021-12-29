import java.util.PriorityQueue;

/**
 * Although this class has a history of several years,
 * it is starting from a blank-slate, new and clean implementation
 * as of Fall 2018.
 * <P>
 * Changes include relying solely on a tree for header information
 * and including debug and bits read/written information
 * 
 * @author Ow	en Astrachan
 *
 * Revise
 */

public class HuffProcessor {

	private class HuffNode implements Comparable<HuffNode> {
		HuffNode left;
		HuffNode right;
		int value;
		int weight;

		public HuffNode(int val, int count) {
			value = val;
			weight = count;
		}
		public HuffNode(int val, int count, HuffNode ltree, HuffNode rtree) {
			value = val;
			weight = count;
			left = ltree;
			right = rtree;
		}

		public int compareTo(HuffNode o) {
			return weight - o.weight;
		}
	}

	public static final int BITS_PER_WORD = 8;
	public static final int BITS_PER_INT = 32;
	public static final int ALPH_SIZE = (1 << BITS_PER_WORD); 
	public static final int PSEUDO_EOF = ALPH_SIZE;
	public static final int HUFF_NUMBER = 0xface8200;
	public static final int HUFF_TREE  = HUFF_NUMBER | 1;

	private boolean myDebugging = false;
	
	public HuffProcessor() {
		this(true);
	}
	
	public HuffProcessor(boolean debug) {
		myDebugging = debug;
	}

	/**
	 * Compresses a file. Process must be reversible and loss-less.
	 *
	 * @param in
	 *            Buffered bit stream of the file to be compressed.
	 * @param out
	 *            Buffered bit stream writing to the output file.
	 */
	public void compress(BitInputStream in, BitOutputStream out){
		int[] counts = getCounts(in);
		HuffNode root = makeTree(counts);

		in.reset();

		logIntBits(HUFF_TREE);
		out.writeBits(BITS_PER_INT, HUFF_TREE);

		writeTree(root, out);

		String[] encodings = new String[ALPH_SIZE + 1];
		makeEncodings(root, "", encodings);

		logEncodings(encodings);

		while (true) {
			int bits = in.readBits(BITS_PER_WORD);

			if (bits == -1) {
				break;
			} else {
				char c = (char) bits;
				String code = encodings[c];
				logIntBits(Integer.parseInt(code, 2));
				out.writeBits(code.length(), Integer.parseInt(code, 2));
			}
		}

		String code = encodings[PSEUDO_EOF];
		out.writeBits(code.length(), Integer.parseInt(code, 2));
		out.close();
	}

	private int[] getCounts(BitInputStream in) {
		int[] counts = new int[ALPH_SIZE];

		while(true) {
			int chunk = in.readBits(BITS_PER_WORD);
			if(chunk == -1) break;

			counts[chunk]++;
		}

		return counts;
	}

	private void writeTree(HuffNode root, BitOutputStream out) {
		if(isLeaf(root)) {
			out.writeBits(1, 1);
			out.writeBits(BITS_PER_WORD + 1, root.value);
		} else {
			out.writeBits(1, 0);
			writeTree(root.left, out);
			writeTree(root.right, out);
		}
	}

	private void makeEncodings(HuffNode root, String currentPath, String[] encoding) {
		if(isLeaf(root)) {
			encoding[root.value] = currentPath;
		} else {
			makeEncodings(root.left, currentPath + "0", encoding);
			makeEncodings(root.right, currentPath + "1", encoding);
		}
	}

	/**
	 * Decompresses a file. Output file must be identical bit-by-bit to the
	 * original.
	 *
	 * @param in
	 *            Buffered bit stream of the file to be decompressed.
	 * @param out
	 *            Buffered bit stream writing to the output file.
	 */
	public void decompress(BitInputStream in, BitOutputStream out){

		int bits = in.readBits(BITS_PER_INT);

		if(bits != HUFF_TREE) {
			throw new HuffException("invalid magic number" + bits);
		}

		HuffNode dicTreeRoot = readTree(in);
		HuffNode current = dicTreeRoot;

		while(true) {
			int bit = in.readBits(1);

			if(bit == -1) {
				throw new HuffException("bad input, no PSEUDO_EOF");
			} else {
				current = bit == 0 ? current.left : current.right;

				if(isLeaf(current)) {
					if(current.value == PSEUDO_EOF) {
						break;
					} else {
						out.writeBits(BITS_PER_WORD, current.value);
						current = dicTreeRoot;
					}
				}
			}
		}

		out.close();
	}

	private boolean isLeaf(HuffNode current) {
		return current.left == null && current.right == null;
	}

	private HuffNode readTree(BitInputStream in) {
		int bit = in.readBits(1);

		if(bit == -1) throw new HuffException("tried to read bit after file ended");

		if(bit == 0) {
			HuffNode leftTree = readTree(in);
			HuffNode rightTree = readTree(in);
			return new HuffNode(0, 0, leftTree, rightTree);
		} else {
			int value = in.readBits(BITS_PER_WORD + 1);
			return new HuffNode(value, 0, null, null);
		}
	}

	private HuffNode makeTree(int[] counts) {
		PriorityQueue<HuffNode> pq = new PriorityQueue<>();
		for (int i = 0; i < counts.length; i++) {
			if (counts[i] > 0) {
				pq.add(new HuffNode(i, counts[i], null, null));
			}
		}
		pq.add(new HuffNode(PSEUDO_EOF, 1, null, null));
		while (pq.size() > 1) {
			HuffNode left = pq.remove();
			HuffNode right = pq.remove();
			HuffNode t = new HuffNode(left.weight + right.weight, left.weight + right.weight, left, right);
			pq.add(t);
		}
		HuffNode root = pq.remove();
		return root;
	}

	private void log(String message) {
		if(myDebugging) {
			System.out.println(message);
		}
	}

	private void logEncodings(String[] encodings) {
		if(myDebugging) {
			System.out.println("Character\tHuffEncoding");
			for (int i = 0; i < encodings.length; i++) {
				if (encodings[i] != null) {
					System.out.printf("%s\t%s\n", (char) i, encodings[i]);
				}
			}
		}
	}

	private void logIntBits(int i) {
		if(myDebugging) System.out.println(Integer.toBinaryString(i));
	}
}