import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WordGramTest {
    private WordGram[] myGrams;

    @BeforeEach
    void setUp() throws Exception {
        String str = "aa bb cc aa bb cc aa bb cc aa bb dd ee ff gg hh ii jj";
        String[] array = str.split("\\s+");
        myGrams= new WordGram[array.length-2];
        for(int k=0; k < array.length-2; k++){
            myGrams[k] = new WordGram(array,k,3);
        }
    }

    @Test
    public void testEqualsTrue() {
        Assertions.assertAll("equals true",
                () -> assertTrue(myGrams[0].equals(myGrams[3]), "0 3"),
                () -> assertTrue(myGrams[0].equals(myGrams[6]), "0 6"),
                () -> assertTrue(myGrams[1].equals(myGrams[4]), "1 4"),
                () -> assertTrue(myGrams[2].equals(myGrams[5]), "2 5"),
                () -> assertTrue(myGrams[2].equals(myGrams[8]), "2 8")
        );
    }

    @Test
    public void testEqualsFalse() {
        Assertions.assertAll("equals false",
                () -> assertFalse(myGrams[0].equals(myGrams[2]), "0 2"),
                () -> assertFalse(myGrams[0].equals(myGrams[4]), "0 4"),
                () -> assertFalse(myGrams[2].equals(myGrams[3]), "2 3"),
                () -> assertFalse(myGrams[2].equals(myGrams[6]), "2 6"),
                () -> assertFalse(myGrams[7].equals(myGrams[8]), "7 8")
        );
    }

    @Test
    public void testHashEquals() {
        Assertions.assertAll("hashing equals",
                () -> assertEquals(myGrams[0].hashCode(),myGrams[3].hashCode(),"0 3"),
                () -> assertEquals(myGrams[0].hashCode(),myGrams[6].hashCode(),"0 6"),
                () -> assertEquals(myGrams[1].hashCode(),myGrams[4].hashCode(),"1 4"),
                () -> assertEquals(myGrams[2].hashCode(),myGrams[5].hashCode(),"2 5"),
                () -> assertEquals(myGrams[2].hashCode(),myGrams[8].hashCode(),"2 8")
        );
    }

    @Test
    public void testHashDensity() {
        Set<Integer> set = new HashSet<Integer>();
        for(WordGram w : myGrams) {
            set.add(w.hashCode());
        }
        assertTrue(set.size() > 9,"set size <= 9, got: "+set.size());
    }

    @Test
    public void testShift() {
        String[] words = {"apple", "zebra", "mongoose", "hat","cat"};
        WordGram a = new WordGram(words,0,4);
        WordGram b = new WordGram(words,1,4);
        String before = a.toString();
        WordGram as = a.shiftAdd("cat");
        String after = a.toString();
        assertTrue(as.equals(b),"shift add wordgrams");
        assertTrue(as.length() == a.length(),"shift add lengths");
        assertTrue(before.equals(after),"shift add immutable");
    }
}