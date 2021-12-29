import java.io.IOException;

/**
 * Find distance between two Person201 objects
 * using Haversine method. Static method to be
 * called by client programs
 * @author Owen Astrachan
 */
public class Person201Utilities {
    public static final double EARTH_RADIUS_KM = 6372.8;

    // code taken from https://rosettacode.org/wiki/Haversine_formula#Java

    public static double distance(Person201 a, Person201 b) {
        double alat = a.getLatitude();
        double along = a.getLongitude();

        double blat = b.getLatitude();
        double blong = b.getLongitude();

        double deltaLat = Math.toRadians(blat-alat);
        double deltaLong = Math.toRadians(blong-along);

        double alatRad = Math.toRadians(alat);
        double blatRad = Math.toRadians(blat);

        double calc = Math.pow(Math.sin(deltaLat/2),2) +
                   Math.pow(Math.sin(deltaLong/2),2) *
                   Math.cos(alatRad) * Math.cos(blatRad);
        double c = 2*Math.asin(Math.sqrt(calc));
        return EARTH_RADIUS_KM * c;
    }

    public static void main(String[] args) throws IOException {
        Person201 a = new Person201();
        Person201 b = new Person201();

        Person201[] p = Person201Scanner.readFile("data/small.txt");
        for(int j=0; j < p.length; j++) {
            for(int k=0; k < p.length; k++) {
                double d = Person201Utilities.distance(p[j],p[k]);
                if (d > 2000) {
                    System.out.printf("%s, %s, %1.3f\n",p[j],p[k],d);}
            }
        }
    }

}
