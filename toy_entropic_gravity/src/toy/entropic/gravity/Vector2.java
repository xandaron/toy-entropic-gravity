package toy.entropic.gravity;

public class Vector2 {
	
	public float x, y;
	private float x1, y1;
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2(float x, float y, float x1, float y1) {
		this.x = x;
		this.y = y;
		this.x1 = x1;
		this.y1 = y1;
	}
	
	public double mag() {
		return Math.sqrt(x*x + y*y);
	}
	
	public Vector2 norm() {
		double m = mag();
		return new Vector2((float) (x/m), (float) (y/m));
	}
	
	public float grad() {
		return y / x;
	}
	
	public float inte() {
		return y1 - grad()*x1;
	}
}
