import java.io.*;
import java.util.*;
import java.net.*;

/**
 * Read from files provided in assignment
 * to illustrate reading, parsing strings from
 * lines, and creating Person201 objects
 * @author Owen Astrachan
 */

public class Person201Scanner {

    public static Person201[] readFile(String fname) throws IOException {
        Scanner s = new Scanner(new File(fname));
        ArrayList<Person201> list = new ArrayList<>();

        while (s.hasNextLine()) {
            String line = s.nextLine();
            String[] data = line.split(",");
            Person201 p = new Person201(
                    data[0],
                    Double.parseDouble(data[1]),
                    Double.parseDouble(data[2]),
                    data[3]);
            list.add(p);
        }
        return list.toArray(new Person201[0]);
    }

    public static Person201[] readURL(String url) throws IOException {

        BufferedInputStream input = new BufferedInputStream(new URL(url).openStream());
        Scanner s = new Scanner(input);
        ArrayList<Person201> list = new ArrayList<>();

        while (s.hasNextLine()) {
            String line = s.nextLine();
            String[] data = line.split(",");
            Person201 p = new Person201(
                    data[0],
                    Double.parseDouble(data[1]),
                    Double.parseDouble(data[2]),
                    data[3]);
            list.add(p);
        }
        return list.toArray(new Person201[0]);
    }

    public static void main(String[] args) throws IOException {
        String fname = "data/large.txt";

        String url = "https://courses.cs.duke.edu/compsci201/current/data/medium.txt";

        Person201[] list = readURL(url);
        for(Person201 p : list) {
            System.out.println(p);
        }
        System.out.println("total # "+list.length);
    }
}
