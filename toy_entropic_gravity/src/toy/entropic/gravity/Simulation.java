package toy.entropic.gravity;
import java.util.Random;

public class Simulation {
	
	private float radius;
	private Particle[] particles;
	private int numParticles = 0;
	private Chord[] chords;
	private int numChords = 0;
	
	public Simulation(float r, int n, int p) {
		radius = r;
		particles = new Particle[p];
		chords = new Chord[n];
		setup();
	}
	
	private void setup() {
		addParticles();
		addChords();
	}
	
	private void addParticles() {
		Random rand = new Random();
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
	
	public void addChords() {
		Random rand = new Random();
		for(int i = 0; i < chords.length; i++) {
			float angle = (float) (rand.nextFloat() * Math.PI * 2);
			float distance = rand.nextFloat() * radius;
			
			float x = (float) (distance * Math.cos(angle));
			float y = (float) (distance * Math.sin(angle));
			angle += (float) (Math.PI / 2);
			
			Vector2 v = new Vector2((float) Math.cos(angle), (float) Math.sin(angle), x, y);
			
			float x1, x2, y1, y2;
			if(v.x == 1f) {
				x1 = x;
				y1 = (float) Math.sqrt(radius*radius - x*x);
				x2 = x;
				y2 = -y1;
			} else if (v.y == 1f) {
				y1 = y;
				x1 = (float) Math.sqrt(radius*radius - y*y);
				y2 = y;
				x2 = -x1;
			} else {
				float a = v.grad()*v.grad() + 1;
				float b = 2*v.grad()*v.inte();
				float c = v.inte()*v.inte() - radius*radius;
				x1 = (float) ((-b + Math.sqrt(b*b - 4*a*c)) / (2*a));
				x2 = (float) ((-b - Math.sqrt(b*b - 4*a*c)) / (2*a));
				y1 = v.grad()*x1 + v.inte();
				y2 = v.grad()*x2 + v.inte();
			}
			chords[numChords] = new Chord(v, x1, y1, x2, y2);
			
			for(Particle p : particles) {
				if(v.x == 1f) {
					if(p.getX() - p.getR() <= x && x <= p.getX() + p.getR()) {
						chords[numChords].setExcluded(true);
						break;
					}
				} else if(v.y == 1f) {
					if(p.getY() - p.getR() <= y && y <= p.getY() + p.getR()) {
						chords[numChords].setExcluded(true);
						break;
					}
				} else {
					float a = 1 + v.grad()*v.grad();
					float b = 2*(p.getX() + v.grad()*(v.inte() + p.getY()));
					float c = p.getX()*p.getX() - p.getR()*p.getR() +
							(v.inte() + p.getY())*(v.inte() + p.getY());
					if(b*b - 4*a*c >= 0.0f) {
						chords[numChords].setExcluded(true);
						break;
					}
				}
			}
			numChords++;
		}
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
