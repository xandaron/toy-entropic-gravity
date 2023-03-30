package toy.entropic.gravity;
import java.util.Random;

public class Simulation {
	
	private float radius;
	private int numParticles;
	private int numChords;
	private double dstStep;
	private double pSize;
	private Particle[] particles;
	private Chord[] chords;
	private Random rand = new Random();
	private int[] legalTracker = {0, 0};
	public boolean complete = false;
	private double delta = 6.133777822;
	public Model owner;
	
	public Simulation(Model o, float r, int n, int p, int i) {
		owner = o;
		radius = r;
		particles = new Particle[p];
		chords = new Chord[n];
		numChords = n;
		pSize = radius * 0.13;
		dstStep = (radius * 0.74 - delta) / --i;
		setup();
	}
	
	private void setup() {
		addParticles();
		addChords();
	}
	
	private void addParticles() {
		for(int i = 0; i < particles.length; i++) {
			double r = pSize;
			double angle = Math.PI * numParticles;
			particles[numParticles] = new Particle(angle, r, r);
			numParticles++;
		}
	}
	
	private void addChords() {
		
		if(owner.graphics) {
			chords = new Chord[chords.length];
		}
		
		for(int i = 0; i < numChords; i++) {
			double angle = rand.nextDouble() * Math.PI * 2;
			double distance = rand.nextDouble() * radius;
			 
			double x = distance * Math.cos(angle);
			double y = distance * Math.sin(angle);
			angle += (float) (Math.PI / 2);
			double grad = Math.sin(angle)/Math.cos(angle);
			
			if(owner.graphics) {
				double x1, x2, y1, y2;
				if(grad == Double.POSITIVE_INFINITY || grad == Double.NEGATIVE_INFINITY) {
					x1 = x;
					y1 = Math.sqrt(radius*radius - x*x);
					x2 = x;
					y2 = -y1;
				} else if (grad == 0) {
					y1 = y;
					x1 = Math.sqrt(radius*radius - y*y);
					y2 = y;
					x2 = -x1;
				} else {
					double inte = y - grad*x;
					double a = grad*grad + 1;
					double b = 2*grad*inte;
					double c = inte*inte - radius*radius;
					x1 = (-b - Math.sqrt(b*b - 4*a*c)) / (2*a);
					x2 = (-b + Math.sqrt(b*b - 4*a*c)) / (2*a);
					y1 = grad*x1 + inte;
					y2 = grad*x2 + inte;
				}
				chords[i] = new Chord(x1, y1, x2, y2);
				updateExclusion(chords[i]);
			} else {
				boolean[] exclusion = {false, false};
				for(Particle p : particles) {
					for(int k = 0; k < 2; k++) {
						p.setDistance(p.getDistance() + delta * k);
						if(grad == 0) {
							if(p.getY()-p.getR()<y && p.getY()+p.getR()>y) {
								exclusion[k] = true;
							}
						} else if(grad == Double.POSITIVE_INFINITY || grad == Double.NEGATIVE_INFINITY) {
							if(p.getX()-p.getR()<x && p.getX()+p.getR()>x) {
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
	
	public void update() {
		legalTracker[0] = 0;
		legalTracker[1] = 0;
		complete = particleUpdate();
		if(!complete) {
			addChords();
			double entropy = calculateLegalRatio();
			double derivative = calculateDerivative();
			String r = Double.toString(particles[0].distance(particles[1]));
			String e = Double.toString(entropy);
			String d = Double.toString(derivative);
			String[] a = {r, e, d};
			owner.addData(a);
		} else {
			System.out.println("Sim complete.");
		}
	}
	
	private boolean particleUpdate() {
		for(Particle p : particles) {
			double distance = p.getDistance() + dstStep;
			if(distance + p.getR() > radius) {
				return true;
			}
			p.setDistance(distance);
		}
		return false;
	}
	
	public double calculateDerivative() {
		if(owner.graphics) {
			double e = calculateLegalRatio();
			for(Particle p : particles) {
				p.setDistance(p.getDistance() + delta);
			}
			for(Chord c : chords) {
				updateExclusion(c);
			}
			double a = calculateLegalRatio();
			for(Particle p : particles) {
				p.setDistance(p.getDistance() - delta);
			}
			for(Chord c : chords) {
				updateExclusion(c);
			}
			return (a - e) / delta;
		} else {
			double r = (double) (legalTracker[1] - legalTracker[0]) / (delta * numChords);
			return r;
		}
	}
	
	public double calculateLegalRatio() {
		if(owner.graphics) {
			double e = 0;
			for(Chord c : chords) { if(!c.isExcluded()) { e++; } }
			return e / chords.length;
		} else {
			return (double) (legalTracker[0]) / numChords;
		}
	}
	
	private void updateExclusion(Chord c) {
		c.setExcluded(false);
		for(Particle p : particles) {
			double grad = c.getGradient();
			
			if(grad == 0) {
				if(p.getY()-p.getR()<c.getY() && p.getY()+p.getR()>c.getY()) {
					c.setExcluded(true);
					break;	
				} else {
					continue;
				}
			}
			
			if(grad == Double.POSITIVE_INFINITY || grad == Double.NEGATIVE_INFINITY) {
				if(p.getX()-p.getR()<c.getX() && p.getX()+p.getR()>c.getX()) {
					c.setExcluded(true);
					break;
				} else {
					continue;
				}
			}
			
			double inte = c.getIntercept();
			double a = 1 + grad*grad;
			double b = 2*(grad * (inte - p.getY()) - p.getX());
			double d = p.getX()*p.getX() - p.getR()*p.getR() +
					(inte - p.getY())*(inte - p.getY());
			if(b*b - 4*a*d >= 0.0d) {
				c.setExcluded(true);
				break;
			}
		}
	}
	
	public double getDistanceStep() {
		return dstStep;
	}
	
	public Particle[] getParticles() {
		return particles;
	}
	
	public Chord[] getChords() {
		return chords;
	}

	public float getRadius() {
		return radius;
	}
	
	public int[] getLegalTracker() {
		return legalTracker;
	}
	
	public double getPSize() {
		return pSize;
	}
}
