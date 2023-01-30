package toy.entropic.gravity;

public class Chord {
	
	private float X1, X2, Y1, Y2;
	private boolean excluded;
	
	public Chord(float x1, float y1, float x2, float y2) {
		X1 = x1;
		X2 = x2;
		Y1 = y1;
		Y2 = y2;
		
		excluded = false;
	}
	
	public float getGradient() {
		return (Y2 - Y1) / (X2 - X1);
	}
	
	// Y = m(X - i) + j where x = 0
	public float getIntercept() {
		return Y1 - getGradient() * X1;
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
	
	// returns the x-coordinate of the midpoint of the chord
	public float getX() {
		return (X2 - X1) / 2 + X1;
	}
	
	// returns the y-coordinate of the midpoint of the chord
	public float getY() {
		return (Y2 - Y1) / 2 + Y1;
	}
	
	public float getDX() {
		return X2 - X1;
	}
	
	public float getDY() {
		return Y2 - Y1;
	}
	
	public String eq() {
		return "y = " + getGradient() + "x + " + getIntercept();
	}
}
