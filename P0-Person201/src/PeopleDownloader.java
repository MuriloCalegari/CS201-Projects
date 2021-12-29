import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.net.URL;
import com.google.gson.*;

/**
 * Read information from a URL that has one json string
 * per line, each string representing a Person201 object.
 */

public class PeopleDownloader {
    private final static String DELIM = "<>";
    private static ArrayList<Person201>
            ourList = new ArrayList<>();

    /**
     * Read the info in the specified URL, a text file which
     * should contain one json string per line, where
     * the json string comes after an arbitrary string followed
     * by "<>" -- this string "<>" delimits the json from
     * other information, e.g., the IP address that posted the info.
     * @param address is the URL of a file storing valid
     *                json strings, one per line after "<>"
     * @return a Person201[] array where each entry represents
     * a Person 201 object formed by parsing the json on each line,
     * first line at index 0. Invalid json strings are silently
     * ignored
     * @throws IOException if reading the URL fails
     */
    public static Person201[] loadData(String address) throws IOException {
        URL url = new URL(address);
        Scanner s = new Scanner(url.openStream());
        ourList.clear();
        Gson gs = new Gson();
        while (s.hasNextLine()) {
            String line = s.nextLine();
            String[] data = line.split(DELIM);
            if (data.length > 1) {
                try {
                    String json = data[1];
                    Person201 p = gs.fromJson(data[1], Person201.class);
                    if(!ourList.contains(p)) {
                        ourList.add(p);
                    }
                }
                catch (JsonParseException jspe) {
                    // silently avoid bad json syntax
                }
            }
        }
        return ourList.toArray(new Person201[0]);
    }

    public static void main(String[] args){
        PeopleDownloader pd = new PeopleDownloader();
        try {
            Person201[] pa =
            PeopleDownloader.loadData("https://apt.cs.duke.edu/compsci201.log");
            for(Person201 p : pa) {
                System.out.println(p);
            }

            System.out.print(pa.length);
        }
        catch (Exception e) {
            System.err.println("problem loading data");
            e.printStackTrace();
        }
    }
}
