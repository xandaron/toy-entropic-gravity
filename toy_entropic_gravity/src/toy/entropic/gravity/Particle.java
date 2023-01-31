package toy.entropic.gravity;

public class Particle {
	
	private double theta, dst;
	private float r;
	public Particle(double theta, double dst, float r) {
		this.theta = theta;
		this.dst = dst;
		this.r = r;
	}
	
	public double distance(Particle p) {
		double t = theta - p.getTheta();
		double distanceSq = dst*dst + p.getDistance()*p.getDistance() - 2 * dst * p.getDistance() * Math.cos(t);
		return Math.sqrt(distanceSq);
	}
	
	public double getTheta() {
		return theta;
	}
	
	public void setTheta(double t) {
		theta = t;
	}
	
	public double getDistance() {
		return dst;
	}
	
	public void setDistance(double d) {
		dst = d;
	}
	
	public float getX() {
		return (float) (dst * Math.cos(theta));
	}
	
	public float getY() {
		return (float) (dst * Math.sin(theta));
	}
	
	public float getR() {
		return r;
	}
	
	public String eq() {
		return "(x - " + getX() + ")^2 + (y - " + getY() + ")^2 = " + r + "^2";
	}
}