package toy.entropic.gravity;
import processing.core.*;

public class Model extends PApplet{

	public static void main(String[] args) {
		PApplet.main(new String[] {toy.entropic.gravity.Model.class.getName()});
	}
	
	// method used only for setting the size of the window
    public void settings(){
    	size((int)(displayWidth * 0.7), (int)(displayHeight * 0.7));
    }

    // identical use to setup in Processing IDE except for size()
    public void setup(){
        surface.setTitle("Toy Entropic Gravity");
        surface.setResizable(true);
        surface.setLocation(10, 10);
    }

    // identical use to draw in Prcessing IDE
    public void draw(){
    	background(130);
    	fill(255);
    	rect(10, 10, 20, 20);
    }
}