package toy.entropic.gravity;

import java.util.Random;

public class BertrandMethodTest {
	Model owner;
	Particle p;
	int numChords = 1000000;
	private Random rand = new Random();
	double radius = (1080 / 2 - 5);
	int numExcluded = 0;
	boolean complete = false;
	double dstStep = (radius * 0.74 - 6.133777822) / 19;
	
	
	public BertrandMethodTest(Model o) {
		owner = o;
		p = new Particle(0, radius * 0.13, radius * 0.13);
	}
	
	public void update() {
		String[] distances = new String[20];
		
		for(int i = 0; i < 20; i++) {
			distances[i] = Double.toString(p.getDistance());
			p.setDistance(p.getDistance() + dstStep);
		}
		owner.addData(distances);
		
		for(int i = 0; i < 50; i++) {
			System.out.println("New pass [" + (i+1) + "/50]");
			p.setDistance(radius * 0.13);
			String[] data = new String[20];
			for(int k = 0; k < 20; k++) {
				numExcluded = 0;
				chords();
				String ne = Integer.toString(numExcluded);
				data[k] = ne;
				p.setDistance(p.getDistance() + dstStep);
			}
			owner.addData(data);
		}
	}
	
	private void chords() {
		for(int i = 0; i < numChords; i++) {
			double angle = rand.nextDouble() * Math.PI * 2;
			double distance = rand.nextDouble() * radius;
			 
			double x = distance * Math.cos(angle);
			double y = distance * Math.sin(angle);
			angle += (float) (Math.PI / 2);
			double grad = Math.sin(angle)/Math.cos(angle);
			boolean exclusion = false;
			
			if(grad == 0) {
				if(p.getY()-p.getR()<y && p.getY()+p.getR()>y) {
					exclusion = true;
				}
			} else if(grad == Double.POSITIVE_INFINITY || grad == Double.NEGATIVE_INFINITY) {
				if(p.getX()-p.getR()<x && p.getX()+p.getR()>x) {
					exclusion = true;
				}
			} else {
				double inte = y - grad * x;
				double a = 1 + grad*grad;
				double b = 2*(grad * (inte - p.getY()) - p.getX());
				double d = p.getX()*p.getX() - p.getR()*p.getR() +
						(inte - p.getY())*(inte - p.getY());
				if(b*b - 4*a*d >= 0.0d) {
					exclusion = true;
				}
			}
			if (exclusion) {
				numExcluded += 1;
			}
		}
	}
}
