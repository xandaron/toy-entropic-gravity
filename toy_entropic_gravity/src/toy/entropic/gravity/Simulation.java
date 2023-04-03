package toy.entropic.gravity;
import java.util.Random;

public class Simulation {
	
	public Model owner;
	private Particle[] particles = new Particle[2];
	private float radius;
	private Chord[] chords;
	private double dstStep;
	private double radiusLimit = 1d;
	private double pSize;
	private double delta;
	private int[] legalTracker = {0, 0};
	public boolean complete = false;
	private Random rand = new Random();
	
	
	public Simulation(Model o, float r, int n, int i) {
		owner = o;
		radius = r;
		chords = new Chord[n];
		pSize = radius * 0.10;
		delta = radius * 0.01;
		dstStep = ((radius * radiusLimit) - (pSize * 2) - delta) / --i;
		setup();
	}
	
	private void setup() {
		addParticles();
		if(owner.graphics) {
			addChords();
		}
	}
	
	private void addParticles() {
		for(int i = 0; i < particles.length; i++) {
			double angle = Math.PI * i;
			particles[i] = new Particle(angle, pSize, pSize);
		}
	}
	
	private void addChords() {
		for(int i = 0; i < chords.length; i++) {
			double angle = rand.nextDouble() * Math.PI * 2;
			double distance = rand.nextDouble() * radius;
			 
			double x = distance * Math.cos(angle);
			double y = distance * Math.sin(angle);
			angle += Math.PI / 2;
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
			} else {
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
	
	public void update() {
		legalTracker[0] = 0;
		legalTracker[1] = 0;
		addChords();
		String ds = Double.toString(calculateDerivative());
		String d = Double.toString(particles[0].distance(particles[1]));
		String e = Double.toString(calculateEntropy());
		String[] data = {d, ds, e};
		owner.addData(data);
		
		complete = particleUpdate();
		if(complete) {
			System.out.println("Sim complete.");
		}
	}
	
	private boolean particleUpdate() {
		for(Particle p : particles) {
			double distance = p.getDistance() + dstStep;
			if(distance + p.getR() > radius * radiusLimit) {
				return true;
			}
			p.setDistance(distance);
		}
		return false;
	}
	
	public double calculateDerivative() {
		if(owner.graphics) {
			double e = calculateEntropy();
			for(Particle p : particles) {
				p.setDistance(p.getDistance() + delta);
			}
			double a = calculateEntropy();
			for(Particle p : particles) {
				p.setDistance(p.getDistance() - delta);
			}
			for(Chord c : chords) {
				updateExclusion(c);
			}
			return (a - e) / delta;
		} else {
			double r = (double) (legalTracker[1] - legalTracker[0]) / (delta * chords.length);
			return r;
		}
	}
	
	public double calculateEntropy() {
		if(owner.graphics) {
			double e = 0;
			for(Chord c : chords) { updateExclusion(c); if(!c.isExcluded()) { e++; } }
			return e / chords.length;
		} else {
			return (double) (legalTracker[0]) / chords.length;
		}
	}
	
	private void updateExclusion(Chord c) {
		c.setExcluded(false);
		for(Particle p : particles) {
			double grad = c.getGradient();
			
			if(grad == 0) {
				if(p.getY()-p.getR()<=c.getY() && p.getY()+p.getR()>=c.getY()) {
					c.setExcluded(true);
					break;	
				} else {
					continue;
				}
			}
			
			if(grad == Double.POSITIVE_INFINITY || grad == Double.NEGATIVE_INFINITY) {
				if(p.getX()-p.getR()<=c.getX() && p.getX()+p.getR()>=c.getX()) {
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
