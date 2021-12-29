import java.util.*;

public class EfficientMarkov extends BaseMarkov {
	private Map<String,ArrayList<String>> myMap;
	
	public EfficientMarkov(){
		this(3);
	}

	public EfficientMarkov(int order) {
		super(order);
		myMap = new HashMap<>();
	}

	@Override
	public void setTraining(String text) {
		super.setTraining(text);
		myMap.clear();

		for(int i = 0; i < myText.length() - myOrder; i += 1) { // Doesn't take into account the last kGram
			String kGram = myText.substring(i, i + myOrder);
			String nextChar = String.valueOf(myText.charAt(i + myOrder));

			myMap.putIfAbsent(kGram, new ArrayList<>());
			myMap.get(kGram).add(nextChar);
		}

		// Add the PSEUDO_EOS to the very final string. I do this to avoid checking if I am dealing
		// with the last kGram in every iteration of the loop
		String kGram = myText.substring(myText.length() - myOrder);
		myMap.putIfAbsent(kGram, new ArrayList<String>());
		myMap.get(kGram).add(PSEUDO_EOS);
	}

	@Override
	public ArrayList<String> getFollows(String key) {
		if(!myMap.containsKey(key)) throw new NoSuchElementException(key + " not in map");

		return myMap.get(key);
	}
}	
