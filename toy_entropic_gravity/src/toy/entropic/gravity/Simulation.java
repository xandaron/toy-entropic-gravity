package toy.entropic.gravity;
import java.util.Random;

public class Simulation {
	
	private Model owner;
	private float radius;
	private int n;
	private Particle[] particles =  new Particle[50];
	private int numParticles = 0;
	public Simulation(Model owner, float r, int n) {
		this.owner = owner;
		radius = r;
		this.n = n;
		setup();
	}
	
	private void setup() {
		Random rand = new Random();
		for(int i = 0; i < 50; i++) {
			float r = rand.nextFloat() * 10;
			float angle = (float) (rand.nextFloat() * Math.PI * 2);
			float distance = rand.nextFloat() * (radius - r);
			float x = (float) (distance * Math.cos(angle));
			float y = (float) (distance * Math.sin(angle));
			addParticle(x, y, r);
		}
		
//		for(int i = 0; i < n; i++) {
//			float angle = (float) (rand.nextFloat() * Math.PI * 2);
//			float position = rand.nextFloat();
//		}
	}
	
	private void addParticle(float x, float y, float r) {
		 particles[numParticles] = new Particle(x, y, r);
		 numParticles++;
	}
	
	public Particle[] getParticles() {
		return particles;
	}

	public float getRadius() {
		return radius;
	}
}
