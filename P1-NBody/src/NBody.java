import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author Murilo Calegari de Souza
 *
 * Simulation program for the NBody assignment
 */
public class NBody {
	
	/**
	 * Read the specified file and return the radius
	 * @param fname is name of file that can be open
	 * @return the radius stored in the file
	 * @throws FileNotFoundException if fname cannot be open
	 */
	public static double readRadius(String fname) throws FileNotFoundException  {
		Scanner s = new Scanner(new File(fname));

		s.next(); // Skip first line

		double rad = s.nextDouble();
		
		s.close();

		return rad;
	}
	
	/**
	 * Read all data in file, return array of Celestial Bodies
	 * read by creating an array of Body objects from data read.
	 * @param fname is name of file that can be open
	 * @return array of Body objects read
	 * @throws FileNotFoundException if fname cannot be open
	 */
	public static CelestialBody[] readBodies(String fname) throws FileNotFoundException {

		Scanner s = new Scanner(new File(fname));

		int nb = s.nextInt(); // # bodies to be read

		CelestialBody[] celestialBodies = new CelestialBody[nb];
		s.nextDouble(); // Ignore radius

		for(int k=0; k < nb; k++) {

			double xPos = s.nextDouble();
			double yPos = s.nextDouble();
			double xVel = s.nextDouble();
			double yVel = s.nextDouble();
			double mass = s.nextDouble();
			String filename = s.next();

			celestialBodies[k] = new CelestialBody(
					xPos,
					yPos,
					xVel,
					yVel,
					mass,
					filename
			);

		}

		s.close();

		return celestialBodies;
	}
	public static void main(String[] args) throws FileNotFoundException{
		double totalTime = 39447000.0;
		double dt = 25000.0;

		String fname= "./data/kaleidoscope.txt";

		if (args.length > 2) {
			totalTime = Double.parseDouble(args[0]);
			dt = Double.parseDouble(args[1]);
			fname = args[2];
		}	
		
		CelestialBody[] bodies = readBodies(fname);
		double radius = readRadius(fname);

		StdDraw.enableDoubleBuffering();
		StdDraw.setScale(-radius, radius);
		StdDraw.picture(0,0,"images/starfield.jpg");

		// run simulation until over

		for(double t = 0.0; t < totalTime; t += dt) {

			double[] xForces = new double[bodies.length];
			double[] yForces = new double[bodies.length];

			for(int k=0; k < bodies.length; k++) {
				xForces[k] = bodies[k].calcNetForceExertedByX(bodies);
				yForces[k] = bodies[k].calcNetForceExertedByY(bodies);
  			}

			for(int k=0; k < bodies.length; k++){

				bodies[k].update(
						dt,
						xForces[k],
						yForces[k]
				);

			}

			StdDraw.clear();
			StdDraw.picture(0,0,"images/starfield.jpg");
			
			// TODO: loop over all bodies and call draw on each one

			for(CelestialBody b : bodies) {
				b.draw();
			}
			StdDraw.show();
			StdDraw.pause(10);

		}
		
		// prints final values after simulation
		
		System.out.printf("%d\n", bodies.length);
		System.out.printf("%.2e\n", radius);
		for (int i = 0; i < bodies.length; i++) {
		    System.out.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
		   		              bodies[i].getX(), bodies[i].getY(), 
		                      bodies[i].getXVel(), bodies[i].getYVel(), 
		                      bodies[i].getMass(), bodies[i].getName());	
		}
	}
}
