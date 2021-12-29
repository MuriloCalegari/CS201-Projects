/**
 * An implementation of the IDnaStrand interface using an internal linked list to model recombinant DNA.
 * This implementation is much more efficient than using String or StringBuilder to model DNA splicing operations.
 * @author Jaeden Joy
 * @author Murilo Calegari
 */
public class LinkStrand implements IDnaStrand {

    private Node myFirst, myLast;
    private long mySize;
    private int myAppends;
    private int myIndex;
    private Node myCurrent;
    private int myLocalIndex;

    private class Node {
        String info;
        Node next;

        public Node(String s, Node n) {
            info = s;
            next = n;
        }
    }

    /**
     * The default constructor just creates an empty DNA Strand
     */
    public LinkStrand() {
        this("");
    }

    /**
     * Create a strand representing s. No error checking is done to see if s
     * represents valid genomic/DNA data.
     *
     * @param s is the source of cgat data for this strand
     */
    public LinkStrand(String s) {
        initialize(s);
    }

    @Override
    public long size() {
        return mySize;
    }

    @Override
    public void initialize(String source) {
        myFirst = new Node(source, null);
        myLast = myFirst;
        mySize = source.length();
        myAppends = 0;
        myIndex = 0;
        myCurrent = myFirst;
        myLocalIndex = 0;
    }

    @Override
    public IDnaStrand getInstance(String source) {
        return new LinkStrand(source);
    }

    private void addFirst(String s) {
        Node newFirst = new Node(s, myFirst);
        myFirst = newFirst;
        mySize += s.length();
    }

    @Override
    public IDnaStrand append(String dna) {
        Node newNode = new Node(dna, null);

        myLast.next = newNode;
        myLast = newNode;
        mySize += dna.length();
        myAppends++;

        return this;
    }

    @Override
    public IDnaStrand reverse() {
        LinkStrand rev = new LinkStrand();
        Node current = myFirst;
        while (current != null) {
            StringBuilder temp = new StringBuilder(current.info);
            rev.addFirst(temp.reverse().toString());
            current = current.next;
        }
        return rev;
    }

    @Override
    public int getAppendCount() {
        return myAppends;
    }

    @Override
    public char charAt(int index) {

        if(index > mySize - 1 || index < 0) {
            throw new IndexOutOfBoundsException(
                    String.format("Tried to access char %s while Strand has size %s", index, mySize)
            );
        }
        /*
         We start from the beginning when we haven't searched
         this LinkStrand looking for charAt(index) or
         when the index is not located within myCurrent node
        */
        if(myIndex == 0 || index < (myIndex - myLocalIndex)) {
            return iterativeCharAt(index, myFirst, 0);
        }

        return iterativeCharAt(index, myCurrent, myIndex - myLocalIndex);

    }

    /**
     * Finds the char at a specific location in the overall strand.
     *
     * The search starts from a given nodeStart for specific scenarios
     * where we don't want to search the entire LinkStrand
     *
     * We ask for the size of the preceding nodes
     * so we can keep track of the overall index
     * and update myIndex
     *
     * @param index The overall index we are looking for. Referring to the entire strand.
     * @param nodeStart The node from where we should start the search.
     * @param precedingNodeCount The total size of the preceding nodes
     * @return The char at a specific index in the overall Strand
     */
    private char iterativeCharAt(int index, Node nodeStart, int precedingNodeCount) {
        int totalCount = precedingNodeCount;
        int localCount = 0; // for each node
        Node list = nodeStart;

        while (totalCount != index) {
            // If the index is not on the current node, then we skip it
            if (index > totalCount + list.info.length() - 1) {
                totalCount += list.info.length();
                list = list.next;
            } else {
                localCount = index - totalCount;
                totalCount = index;
            }
        }

        myIndex = index;
        myCurrent = list;
        myLocalIndex = localCount;

        return list.info.charAt(localCount);
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        Node current = myFirst;
        while (current != null) {
            ret.append(current.info);
            current = current.next;
        }

        return ret.toString();
    }
}
