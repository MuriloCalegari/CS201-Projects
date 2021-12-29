

/**
 * Celestial Body class for NBody
 * Modified from original Planet class
 * used at Princeton and Berkeley
 * @author ola
 *
 * If you add code here, add yourself as @author below
 * @author murilocalegari
 *
 */
public class CelestialBody {

	private double myXPos;
	private double myYPos;
	private double myXVel;
	private double myYVel;
	private final double myMass;
	private final String myFileName;

	/**
	 * Create a Body from parameters	
	 * @param xp initial x position
	 * @param yp initial y position
	 * @param xv initial x velocity
	 * @param yv initial y velocity
	 * @param mass of object
	 * @param filename of image for object animation
	 */
	public CelestialBody(double xp, double yp, double xv,
			             double yv, double mass, String filename){
		this.myXPos = xp;
		this.myYPos = yp;
		this.myXVel = xv;
		this.myYVel = yv;
		this.myMass = mass;
		this.myFileName = filename;
	}

	/**
	 * Returns the current X-Coordinate of the planet,
	 * updated in the last call to update()
	 *
	 * @return the current X-Coordinate
	 */
	public double getX() {
		return myXPos;
	}

	/**
	 * Accessor for the current Y-Coordinate of the body,
	 * updated in the last call to update()
	 *
	 * @return the current Y-Coordinate
	 */
	public double getY() {
		return myYPos;
	}

	/**
	 * Accessor for the x-velocity
	 * @return the value of this object's x-velocity
	 */
	public double getXVel() {
		return myXVel;
	}
	/**
	 * Return y-velocity of this Body.
	 * @return value of y-velocity.
	 */
	public double getYVel() {
		return myYVel;
	}

	/**
	 * Accessor for the mass
	 * @return the mass
	 */
	public double getMass() {
		return myMass;
	}

	/**
	 * Accessor for the file name of the body's image
	 * @return the file name
	 */
	public String getName() {
		return myFileName;
	}

	/**
	 * Return the distance between this body and another
	 * @param b the other body to which distance is calculated
	 * @return distance between this body and b
	 */
	public double calcDistance(CelestialBody b) {
		return Math.sqrt(
				(myXPos - b.getX()) * (myXPos - b.getX()) +
						(myYPos - b.getY()) * (myYPos - b.getY())
		);
	}

	public double calcForceExertedBy(CelestialBody b) {

		// I store distance in a variable to avoid
		// unnecessary calculation
		double distance = calcDistance(b);

		return ((6.67*1e-11) * myMass * b.getMass()) / (distance * distance);
	}

	public double calcForceExertedByX(CelestialBody b) {
		return (calcForceExertedBy(b) * (b.getX() - myXPos)) / calcDistance(b);
	}
	public double calcForceExertedByY(CelestialBody b) {
		return (calcForceExertedBy(b) * (b.getY() - myYPos)) / calcDistance(b);
	}

	public double calcNetForceExertedByX(CelestialBody[] bodies) {
		double sum = 0.0;

		for(CelestialBody body: bodies) {
			if(!body.equals(this)) {
				sum += calcForceExertedByX(body);
			}
		}

		return sum;
	}

	public double calcNetForceExertedByY(CelestialBody[] bodies) {
		double sum = 0.0;

		for(CelestialBody body: bodies) {
			if(!body.equals(this)) {
				sum += calcForceExertedByY(body);
			}
		}

		return sum;
	}

	public void update(double deltaT, 
			           double xforce, double yforce) {

		double ax = xforce / myMass;
		double ay = yforce / myMass;

		double nvx = myXVel + deltaT * ax;
		double nvy = myYVel + deltaT * ay;

		double nx = myXPos + deltaT * nvx;
		double ny = myYPos + deltaT * nvy;

		this.myXPos = nx;
		this.myYPos = ny;
		this.myXVel = nvx;
		this.myYVel = nvy;

	}

	/**
	 * Draws this planet's image at its current position
	 */
	public void draw() {
		StdDraw.picture(myXPos,myYPos,"images/"+myFileName);
	}
}
