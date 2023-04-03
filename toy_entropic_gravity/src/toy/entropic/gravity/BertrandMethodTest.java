package toy.entropic.gravity;

import java.util.Random;

public class BertrandMethodTest {
	Model owner;
	Particle p;
	int numChords;
	double radius;
	boolean complete = false;
	double dstStep;
	private Random rand = new Random();
	int numExcluded = 0;
	int iterations;
	int numDistances;
	int test;
	
	
	public BertrandMethodTest(Model o, int t, double r, int c, int d, int i) {
		owner = o;
		test = t;
		radius = r;
		numChords = c;
		dstStep = radius * 0.79 / d;
		p = new Particle(0, 0, radius * 0.10);
		iterations = i;
		numDistances = d;
	}
	
	public void methodTest() {
		String[] distances = new String[20];
		
		for(int i = 0; i < numDistances; i++) {
			distances[i] = Double.toString(p.getDistance());
			p.setDistance(p.getDistance() + dstStep);
		}
		owner.addData(distances);
		
		for(int i = 0; i < iterations; i++) {
			System.out.println("Pass [" + (i+1) + "/50]");
			p.setDistance(radius * 0.13);
			String[] data = new String[20];
			for(int k = 0; k < numDistances; k++) {
				numExcluded = 0;
				if(test == 1) {
					method1();
				} else {
					method2();
				}
				String ne = Integer.toString(numExcluded);
				data[k] = ne;
				p.setDistance(p.getDistance() + dstStep);
			}
			owner.addData(data);
		}
	}
	
	private void method1() {
		for(int i = 0; i < numChords; i++) {
			double angle1 = rand.nextDouble() * Math.PI * 2;
			double x1 = radius * Math.cos(angle1);
			double y1 = radius * Math.sin(angle1);
			
			double angle2 = rand.nextDouble() * Math.PI * 2;
			double x2 = radius * Math.cos(angle2);
			double y2 = radius * Math.sin(angle2);
			
			double grad = (y2 - y1) / (x2 - x1);
			// y - y1 = m(x - x1) therefore c = y1 -m*x1
			double inte = y1 - grad*x1;
			
			if (intersects(x1, y1, grad, inte)) {
				numExcluded += 1;
			}
		}
	}
	
	private void method2() {
		for(int i = 0; i < numChords; i++) {
			double angle = rand.nextDouble() * Math.PI * 2;
			double distance = rand.nextDouble() * radius;
			 
			double x = distance * Math.cos(angle);
			double y = distance * Math.sin(angle);
			angle += Math.PI / 2;
			double grad = Math.sin(angle)/Math.cos(angle);
			// y - y1 = m(x - x1) therefore c = y1 -m*x1
			double inte = y - grad * x;
			
			if (intersects(x, y, grad, inte)) {
				numExcluded += 1;
			}
		}
	}
	
	private boolean intersects(double x, double y, double grad, double inte) {
		if(grad == 0) {
			if(p.getY()-p.getR()<=y && p.getY()+p.getR()>=y) {
				return true;
			}
		} else if(grad == Double.POSITIVE_INFINITY || grad == Double.NEGATIVE_INFINITY) {
			if(p.getX()-p.getR()<=x && p.getX()+p.getR()>=x) {
				return true;
			}
		} else {
			double a = 1 + grad*grad;
			double b = 2*(grad * (inte - p.getY()) - p.getX());
			double d = p.getX()*p.getX() - p.getR()*p.getR() +
					(inte - p.getY())*(inte - p.getY());
			if(b*b - 4*a*d >= 0.0d) {
				return true;
			}
		}
		return false;
	}
}
