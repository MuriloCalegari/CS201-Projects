
import java.math.*;

/**
 *  Tests calcPairwiseForce
 */
public class TestCalcForceExertedByXY {

    /**
     *  Tests calcForceExertedByXY.
     */
    public static void main(String[] args) {
        checkCalcForceExertedByXY();
    }

    /**
     *  Checks whether or not two Doubles are equal and prints the result.
     *
     *  @param  expected    Expected double
     *  @param  actual      Double received
     *  @param  label   Label for the 'test' case
     *  @param  eps     Tolerance for the double comparison.
     */
    private static void checkEquals(double actual, double expected, String label, double eps) {
        if (Math.abs(expected - actual) <= eps * Math.abs(Math.max(expected, actual))) {
            System.out.println("PASS: " + label + ": Expected " + expected + " and you gave " + actual);
        } else {
            System.out.println("FAIL: " + label + ": Expected " + expected + " and you gave " + actual);
        }
    }

    /**
     *  Checks the Body class to make sure calcForceExertedByXY works.
     */
    private static void checkCalcForceExertedByXY() {
        System.out.println("Checking calcForceExertedByX and calcForceExertedByY...");

        CelestialBody p1 = new CelestialBody(1.0, 1.0, 3.0, 4.0, 5.0, "jupiter.gif");
        CelestialBody p2 = new CelestialBody(2.0, 1.0, 3.0, 4.0, 4e11, "jupiter.gif");
        CelestialBody p3 = new CelestialBody(4.0, 5.0, 3.0, 4.0, 5.0, "jupiter.gif");

        checkEquals(p1.calcForceExertedByX(p2), 133.4, "calcForceExertedByX()", 0.01);
        checkEquals(p2.calcForceExertedByX(p1), -133.4, "calcForceExertedByX()", 0.01);
        checkEquals(p1.calcForceExertedByX(p3), 4.002e-11, "calcForceExertedByX()", 0.01);
        checkEquals(p1.calcForceExertedByY(p2), 0.0, "calcForceExertedByY()", 0.01);
        checkEquals(p1.calcForceExertedByY(p3), 5.336e-11, "calcForceExertedByY()", 0.01);
    }
}
