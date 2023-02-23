package toy.entropic.gravity;

public class Chord {
	
	private double X1, X2, Y1, Y2;
	private boolean excluded;
	
	public Chord(double x1, double y1, double x2, double y2) {
		X1 = x1;
		X2 = x2;
		Y1 = y1;
		Y2 = y2;
		
		excluded = false;
	}
	
	public double getGradient() {
		return (Y2 - Y1) / (X2 - X1);
	}
	
	// Y = m(X - i) + j where x = 0
	public double getIntercept() {
		return Y1 - getGradient() * X1;
	}
	
	public boolean isExcluded() {
		return excluded;
	}
	
	public void setExcluded(boolean e) {
		excluded = e;
	}
	
	public double getX1() {
		return X1;
	}
	
	public double getX2() {
		return X2;
	}
	
	public double getY1() {
		return Y1;
	}
	
	public double getY2() {
		return Y2;
	}
	
	// returns the x-coordinate of the midpoint of the chord
	public double getX() {
		return (X2 - X1) / 2 + X1;
	}
	
	// returns the y-coordinate of the midpoint of the chord
	public double getY() {
		return (Y2 - Y1) / 2 + Y1;
	}
	
	public double getDX() {
		return X2 - X1;
	}
	
	public double getDY() {
		return Y2 - Y1;
	}
	
	public String eq() {
		return "y = " + getGradient() + "x + " + getIntercept();
	}
}
