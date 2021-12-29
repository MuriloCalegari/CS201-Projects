import java.util.*;

public class HashListAutocomplete implements Autocompletor {

    private static final int MAX_PREFIX = 10;
    private Map<String, List<Term>> myMap;
    private int mySize;

    public HashListAutocomplete(String[] terms, double[] weights) {
        if (terms == null || weights == null) {
            throw new NullPointerException("One or more arguments null");
        }

        initialize(terms,weights);
    }

    @Override
    public List<Term> topMatches(String prefix, int k) {
        if(!myMap.containsKey(prefix)) return new ArrayList<>();

        if(prefix.length() > MAX_PREFIX) {
            prefix = prefix.substring(0, MAX_PREFIX);
        }

        List<Term> all = myMap.get(prefix);

        return myMap.get(prefix).subList(0, Math.min(k, all.size()));
    }

    @Override
    public void initialize(String[] terms, double[] weights) {
        myMap = new HashMap<>();

        for (int i = 0; i < terms.length; i++) {
            String termWord = terms[i];
            double weight = weights[i];

            Term term = new Term(termWord, weight);

            int greatestIndex = Math.min(termWord.length(), MAX_PREFIX);

            // Looping through all substrings from 0 to either
            // termWord.length() or MAX_PREFIX, whichever is smaller
            for (int j = 0; j <= greatestIndex; j++) {
                String substring = termWord.substring(0, j);

                myMap.putIfAbsent(substring, new ArrayList<>());
                myMap.get(substring).add(term);
            }
        }

        for(String key: myMap.keySet()) {
            Collections.sort(myMap.get(key), (Comparator.comparing(Term::getWeight).reversed()));
        }
    }

    @Override
    public int sizeInBytes() {

        int mySize = 0;

        HashSet<Term> terms = new HashSet<>();

        for(String key: myMap.keySet()) {
            mySize += BYTES_PER_CHAR*key.length();
            terms.addAll(myMap.get(key));
        }

        for(Term term: terms) {
            mySize += BYTES_PER_CHAR*term.getWord().length() + BYTES_PER_DOUBLE;
        }

        return mySize;
    }
}
