package toy.entropic.gravity;

public class Chord {
	
	private Vector2 v;
	private float X1, X2, Y1, Y2;
	private boolean excluded;
	
	public Chord(Vector2 v, float x1, float y1, float x2, float y2) {
		this.v = v;
		
		X1 = x1;
		X2 = x2;
		Y1 = y1;
		Y2 = y2;
		
		excluded = false;
	}
	
	public float getGradient() {
		return v.grad();
	}
	
	public float getIntercept() {
		return v.inte();
	}
	
	public boolean isExcluded() {
		return excluded;
	}
	
	public void setExcluded(boolean e) {
		excluded = e;
	}
	
	public float getX1() {
		return X1;
	}
	
	public float getX2() {
		return X2;
	}
	
	public float getY1() {
		return Y1;
	}
	
	public float getY2() {
		return Y2;
	}
}
