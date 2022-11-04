package toy.entropic.gravity;
import processing.core.*;

public class Model extends PApplet{
	
	Simulation sim;
	float offsetX, offsetY;
	public static void main(String[] args) {
		PApplet.main(new String[] {toy.entropic.gravity.Model.class.getName()});
	}
	
	// method used only for setting the size of the window
    public void settings(){
    	size((int)(displayWidth * 0.7), (int)(displayHeight * 0.7));
    }
    
    public void setup(){
        surface.setTitle("Toy Entropic Gravity");
        surface.setLocation(10, 10);
        strokeWeight(1);
        
        sim = new Simulation((height - 10) / 2, 1000, 10);
        offsetX = width - 5 - sim.getRadius();
        offsetY = height / 2;
    }
    
    public void draw(){
    	background(130);
    	
    	fill(255);
    	stroke(0);
    	circle(offsetX, offsetY, 2 * sim.getRadius());
    	
    	for(Chord c : sim.getChords()) {
    		if(c.isExcluded()) {
    			stroke(150);
    		} else {
    			stroke(0);
    		}
    		line(c.getX1() + offsetX, c.getY1() + offsetY, c.getX2() + offsetX, c.getY2() + offsetY);
    	}
    	
    	for(Particle p : sim.getParticles()) {
    		noFill();
    		stroke(255,0,0);
    		circle(p.getX() + offsetX, p.getY() + offsetY, p.getR() * 2);
    	}
    }
}