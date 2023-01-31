package toy.entropic.gravity;

public class InputBox extends Button {

	String value = "";
	int maxLength;
	public InputBox(int x, int y, int width, int height, String text, int maxLength) {
		super(x, y, width, height, -1, text);
		this.maxLength = maxLength;
	}
	
	public void appendValue(char c) {
		// Prevents leading zeros
		if(value.length() > 0 || c != '0') {
			value += c;
		}
	}
	
	public void popValue() {
		if(value.length() > 0) {
			value = value.substring(0,value.length()-1);
		}
	}
	
	public int getValue() {
		return Integer.parseInt(value);
	}
	
	public void setState(boolean b) {
		state = b;
	}
}
