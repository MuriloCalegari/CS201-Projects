public class SimpleWordGramDriver {
    public static void main(String[] args) {
        String[] words = {"Computer", "Science", "is", "fun","so","fun!!"};
        WordGram g = new WordGram(words,0,4);
        System.out.printf("gram = %s, length = %d, hash = %d\n",
                          g,g.length(),g.hashCode());

        WordGram ng = g.shiftAdd("sometimes");
        System.out.printf("gram = %s, length = %d, hash = %d\n",
                ng,ng.length(),ng.hashCode());
    }
}
