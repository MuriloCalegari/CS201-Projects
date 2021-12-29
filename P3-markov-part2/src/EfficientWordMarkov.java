import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class EfficientWordMarkov extends BaseWordMarkov {

    private Map<WordGram, ArrayList<String>> myMap;

    public EfficientWordMarkov() {
        this(3);
    }

    public EfficientWordMarkov(int order) {
        super(order);
        myMap = new HashMap<>();
    }

    @Override
    public void setTraining(String text) {
        super.setTraining(text);

        myMap.clear();

        for(int i = 0; i < myWords.length - myOrder; i += 1) {
            WordGram kGram = new WordGram(myWords, i, myOrder);
            String nextWord = myWords[i + myOrder];

            myMap.putIfAbsent(kGram, new ArrayList<>());
            myMap.get(kGram).add(nextWord);
        }

        // Add the PSEUDO_EOS to the very final WordGram. I do this to avoid checking if I am dealing
        // with the last kGram in every iteration of the loop
        WordGram kGram = new WordGram(myWords, myWords.length - myOrder, myOrder);
        myMap.putIfAbsent(kGram, new ArrayList<>());
        myMap.get(kGram).add(PSEUDO_EOS);
    }

    @Override
    public ArrayList<String> getFollows(WordGram kGram) {
        if(!myMap.containsKey(kGram)) {
            throw new NoSuchElementException(kGram.toString() + " not in map");
        }

        return myMap.get(kGram);
    }
}
