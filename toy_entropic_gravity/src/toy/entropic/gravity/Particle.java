package toy.entropic.gravity;

public class Particle {
	
	private float x, y;
	private float r;
	public Particle(float x, float y, float r) {
		this.x = x;
		this.y = y;
		this.r = r;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getR() {
		return r;
	}
	
	public String eq() {
		return "(x - " + x + ")^2 + (y - " + y + ")^2 = " + r + "^2";
	}
}