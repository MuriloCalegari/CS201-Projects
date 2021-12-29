import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

/**
 * Main class for the Autocomplete program.
 * 
 * @author Austin Lu
 * @author Owen Astrachan
 *
 */
public class AutocompleteMain {

	final static String BRUTE_AUTOCOMPLETE = "BruteAutocomplete";
	final static String BINARY_SEARCH_AUTOCOMPLETE = "BinarySearchAutocomplete";
	final static String HASHLIST_AUTOCOMPLETE = "HashListAutocomplete";
	
	/* Modify name of Autocompletor implementation as necessary */
	
	final static String AUTOCOMPLETOR_CLASS_NAME = BRUTE_AUTOCOMPLETE;
	//final static String AUTOCOMPLETOR_CLASS_NAME = BINARY_SEARCH_AUTOCOMPLETE;
	//final static String AUTOCOMPLETOR_CLASS_NAME = HASHLIST_AUTOCOMPLETE;
	
	public static void main(String[] args) {
		final int K = 10;
		String filename = null;
		File file = FileSelector.selectFile();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new AutocompleteGUI(file.getAbsolutePath(),
					 K, AUTOCOMPLETOR_CLASS_NAME).setVisible(true);
			}
		});
	}
}
