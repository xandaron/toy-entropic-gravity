package toy.entropic.gravity;
import java.util.Random;

public class Simulation {
	
	private float radius;
	private Particle[] particles;
	private int numParticles = 0;
	private Chord[] chords;
	private int numChords = 0;
	private Random rand = new Random();
	private int timeStep = 1;
	
	public Simulation(float r, int n, int p) {
		radius = r;
		particles = new Particle[p];
		chords = new Chord[n];
		setup();
	}
	
	private void setup() {
		addParticles();
		addChords();
		for(int i = 0; i < 10000; i++) {
			chordUpdate();
		}
	}
	
	private void addParticles() {
		for(int i = 0; i < particles.length; i++) {
			float r = 20 + rand.nextFloat() * 20;
			float angle = (float) (rand.nextFloat() * Math.PI * 2);
			float distance = rand.nextFloat() * (radius - r);
			float x = (float) (distance * Math.cos(angle));
			float y = (float) (distance * Math.sin(angle));
			particles[numParticles] = new Particle(x, y, r);
			numParticles++;
		}
	}
	
	private void addChords() {
		for(int i = 0; i < chords.length; i++) {
			float angle = (float) (rand.nextFloat() * Math.PI * 2);
			float distance = rand.nextFloat() * radius;
			 
			float x = (float) (distance * Math.cos(angle));
			float y = (float) (distance * Math.sin(angle));
			angle += (float) (Math.PI / 2);
			float dx = (float) Math.cos(angle);
			float dy = (float) Math.sin(angle);
			
			float x1, x2, y1, y2;
			if(dy == 0.0f) {
				x1 = x;
				y1 = (float) Math.sqrt(radius*radius - x*x);
				x2 = x;
				y2 = -y1;
			} else if (dx == 0.0f) {
				y1 = y;
				x1 = (float) Math.sqrt(radius*radius - y*y);
				y2 = y;
				x2 = -x1;
			} else {
				float grad = dy / dx;
				float inte = (float) (y - grad*x);
				float a = grad*grad + 1;
				float b = 2*grad*inte;
				float c = inte*inte - radius*radius;
				x1 = (float) ((-b - Math.sqrt(b*b - 4*a*c)) / (2*a));
				x2 = (float) ((-b + Math.sqrt(b*b - 4*a*c)) / (2*a));
				y1 = grad*x1 + inte;
				y2 = grad*x2 + inte;
			}
			chords[numChords] = new Chord(x1, y1, x2, y2);
			updateExclusion(chords[numChords]);
			numChords++;
		}
	}
	
	public void update() {
		particleUpdate();
		chordUpdate();
	}
	
	private void particleUpdate() {
		
	}
	
	private void chordUpdate() {
		if(chords.length == 0) {
			return;
		}
		
		for(int i = 0; i < timeStep; i++) {
			int k = rand.nextInt(chords.length-1);
			if(!chords[k].isExcluded()) {
				chords[k].setExcluded(true);
			} else {
				updateExclusion(chords[k]);
			}
		}
	}
	
	private void updateExclusion(Chord c) {
		c.setExcluded(false);
		for(Particle p : particles) {
			float grad = c.getGradient();
			
			if(grad == 0) {
				if(p.getY()-p.getR()<c.getY() && p.getY()+p.getR()>c.getY()) {
					c.setExcluded(true);
					break;	
				} else {
					continue;
				}
			}
			
			if(grad == Float.POSITIVE_INFINITY || grad == Float.NEGATIVE_INFINITY) {
				if(p.getX()-p.getR()<c.getX() && p.getX()+p.getR()>c.getX()) {
					c.setExcluded(true);
					break;
				} else {
					continue;
				}
			}
			
			float inte = c.getIntercept();
			float a = 1 + grad*grad;
			float b = 2*(grad * (inte - p.getY()) - p.getX());
			float d = p.getX()*p.getX() - p.getR()*p.getR() +
					(inte - p.getY())*(inte - p.getY());
			if(b*b - 4*a*d >= 0.0f) {
				c.setExcluded(true);
				break;
			}
		}
	}
	
	public void setTimeStep(int t) {
		timeStep = t;
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
}
