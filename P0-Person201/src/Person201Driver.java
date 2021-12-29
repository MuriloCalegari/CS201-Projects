/**
 * Illustrates use of default and other constructors
 * for Person201, use of .toString() to print,
 * and accessor methods for printing information
 * @author Owen Astrachan
 */
public class Person201Driver {
    public static void main(String[] args) {
        Person201 p = new Person201();
        Person201 q =
                new Person201("Ricardo",-34.6037, -58.3816,"harambee");
        Person201 r =
                new Person201("Gelareh",-33.89,151.2,"affective");
        Person201 s = new Person201("Sam", 44.9978, -93.2650, "hello");

        System.out.println(p);
        System.out.println(q);
        System.out.println(r);
        System.out.println(s);

        System.out.printf("%s %s\n",q.getName(), q.getPhrase());
        System.out.printf("%s %s\n",r.getName(), r.getPhrase());
    }
}
