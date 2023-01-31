package toy.entropic.gravity;

public class Button {
	
	int x, y;
	int width, height;
	int c1, c2;
	boolean state = false;
	String text = "";
	
	public Button(int x, int y, int width, int height, int c1, int c2) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.c1 = c1;
		this.c2 = c2;
	}
	
	public Button(int x, int y, int width, int height, int c1, String text) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.c1 = c1;
		this.c2 = c1;
		this.text = text;
	}
	
	public int getColour() {
		if(state) { return c2; }
		return c1;
	}
	
	public void toggleState() {
		state = !state;
	}
}
