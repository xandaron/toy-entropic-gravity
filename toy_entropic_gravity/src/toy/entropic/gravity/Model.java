package toy.entropic.gravity;
import processing.core.*;

public class Model extends PApplet{
	
	Simulation sim;
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
        surface.setLocation(10, 10);
        strokeWeight(1);
        
        sim = new Simulation(this, (height - 5) / 2, 100);
    }

    // identical use to draw in Prcessing IDE
    public void draw(){
    	background(130);
    	
    	fill(255);
    	stroke(0);
    	circle(width - 5 - sim.getRadius(), height / 2, 2 * sim.getRadius());
    	
    	for(Particle p : sim.getParticles()) {
    		if(p != null) {
	    		noFill();
	    		stroke(255,0,0);
	    		circle(p.getX() + width - 5 - sim.getRadius(), p.getY() + height / 2, p.getR() * 2);
    		} else {
    			break;
    		}
    	}
    }
}