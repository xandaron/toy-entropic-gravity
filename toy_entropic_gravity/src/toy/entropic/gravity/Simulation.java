package toy.entropic.gravity;
import java.util.Random;

public class Simulation {
	
	static int distances = 20;
	static int iterations = 100;
	static int run = 0;
	static int counter = 0;
	
	static int numChords = 10000000;
	static Particle[] particles = new Particle[2];
	
	static float radius = 1000f;
	static double radiusLimit = 1d;
	static double pSize = radius * 0.10;
	static double delta = radius * 0.01;
	static double dstStep = ((radius * radiusLimit) - (pSize * 2) - delta) / (distances - 1);
	static int[] legalTracker = {0, 0};
	static boolean complete = false;
	
	static FileHandler fileHandler = new FileHandler();
	static Random rand = new Random();
	
	public static void main(String args[]) {
		fileHandler.newFile();
		addParticles();
		while(!complete) {
			update();
		}
		fileHandler.saveData();
	}
	
	static void update() {
		legalTracker[0] = 0;
		legalTracker[1] = 0;
		addChords();
		String ds = Double.toString((double) ((legalTracker[1] - legalTracker[0])) / (delta * numChords));
		String d = Double.toString(particles[0].distance(particles[1]));
		String e = Double.toString((double) (legalTracker[0]) / numChords);
		String[] data = {d, ds, e};
		fileHandler.addDataLine(data);
		run++;
		counter++;
		System.out.println("[" + counter + "/" + iterations*distances + "]");
		if(run == iterations) {
			complete = particleUpdate();
			run = 0;
		}
		if(complete) {
			System.out.println("Sim complete.");
		}
	}
	
	static void addParticles() {
		for(int i = 0; i < particles.length; i++) {
			double angle = Math.PI * i;
			particles[i] = new Particle(angle, pSize, pSize);
		}
	}
	
	static boolean particleUpdate() {
		for(Particle p : particles) {
			double distance = p.getDistance() + dstStep;
			if(distance + p.getR() > radius * radiusLimit) {
				return true;
			}
			p.setDistance(distance);
		}
		return false;
	}
	
	static void addChords() {
		for(int i = 0; i < numChords; i++) {
			double angle = rand.nextDouble() * Math.PI * 2;
			double distance = rand.nextDouble() * radius;
			 
			double x = distance * Math.cos(angle);
			double y = distance * Math.sin(angle);
			angle += Math.PI / 2;
			double grad = Math.sin(angle)/Math.cos(angle);
			
			boolean[] exclusion = {false, false};
			for(Particle p : particles) {
				for(int k = 0; k < 2; k++) {
					p.setDistance(p.getDistance() + delta * k);
					if(grad == 0) {
						if(p.getY()-p.getR()<=y && p.getY()+p.getR()>=y) {
							exclusion[k] = true;
						}
					} else if(grad == Double.POSITIVE_INFINITY || grad == Double.NEGATIVE_INFINITY) {
						if(p.getX()-p.getR()<=x && p.getX()+p.getR()>=x) {
							exclusion[k] = true;
						}
					} else {
						double inte = y - grad * x;
						double a = 1 + grad*grad;
						double b = 2*(grad * (inte - p.getY()) - p.getX());
						double d = p.getX()*p.getX() - p.getR()*p.getR() +
								(inte - p.getY())*(inte - p.getY());
						if(b*b - 4*a*d >= 0.0d) {
							exclusion[k] = true;
						}
					}
					p.setDistance(p.getDistance() - delta * k);
				}
			}
			if(!exclusion[0]) { legalTracker[0] += 1; }
			if(!exclusion[1]) { legalTracker[1] += 1; }
		}
	}
}
