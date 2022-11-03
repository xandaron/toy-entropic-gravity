package toy.entropic.gravity;

public class Chord {
	
	private float m, c;
	private float X1, X2, Y1, Y2;
	private boolean excluded;
	
	public Chord(float gradient, float intercept, float x1, float y1, float x2, float y2) {
		m = gradient;
		c = intercept;
		
		X1 = x1;
		X2 = x2;
		Y1 = y1;
		Y2 = y2;
		
		excluded = true;
	}
	
	public float getGradient() {
		return m;
	}
	
	public float getIntercept() {
		return c;
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
