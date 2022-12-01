package toy.entropic.gravity;
import processing.core.*;

public class Model extends PApplet{
	
	Simulation sim;
	boolean run = false;
	boolean invert = false;
	float offsetX, offsetY;
	String numParticles = "";
	String numChords = "";
	boolean numParticlesEdit = false;
	boolean numChordsEdit = false;
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
        
        sim = new Simulation(height / 2 - 5, 0, 0);
        
        offsetX = width - 5 - sim.getRadius();
        offsetY = height / 2;
    }
    
    public void draw(){
    	background(130);
    	stroke(0);
    	if(!run) {
    		fill(0, 200, 0);
    	} else {
    		fill(200, 0 ,0);
    	}
    	rect(20, height - 60, 40, 40);
    	
    	if(invert) {
    		fill(0);
    	} else {
    		fill(255);
    	}
    	rect(80, height - 60, 40, 40);
    	
    	
    	// Input boxes
    	fill(250);
    	if(numParticlesEdit) { stroke(222, 197, 7); } else { stroke(0); }
    	rect(20,20,100,40);
    	if(numChordsEdit) { stroke(222, 197, 7); } else { stroke(0); }
    	rect(140,20,100,40);
    	textAlign(LEFT,CENTER);
    	textSize(20);
    	if(numParticles == "") {
    		fill(100);
    		text("#Particles",25,20,100,40);
    	} else {
    		fill(0);
    		text(numParticles,25,20,100,40);
    	}
    	if(numChords == "") {
    		fill(100);
    		text("#Chords",145,20,100,40);
    	} else {
    		fill(0);
    		text(numChords,145,20,100,40);
    	}
    	
    	stroke(0);
    	fill(73, 164, 230);
    	rect(20,80,220,40);
    	fill(0);
    	textAlign(CENTER, CENTER);
    	text("Generate",20,80,220,35);
    	
    	if(invert) {
    		fill(0);
    	} else {
    		fill(255);
    	}
    	stroke(0);
    	strokeWeight(1);
    	circle(offsetX, offsetY, 2 * sim.getRadius());
    	
    	for(Chord c : sim.getChords()) {
    		if(c.isExcluded()) {
    			stroke(150);
    		} else {
    			if(invert) {
    				stroke(255);
    			} else {
    				stroke(0);
    			}
    		}
    		line(offsetX + c.getX1(), offsetY + c.getY1(), offsetX + c.getX2(), offsetY + c.getY2());
    	}
    	
    	for(Particle p : sim.getParticles()) {
    		noFill();
    		strokeWeight(1);
    		stroke(255,0,0);
    		circle(offsetX + p.getX(), offsetY + p.getY(), p.getR() * 2);
    	}
    	
    	noFill();
    	stroke(0);
    	strokeWeight(1);
    	circle(offsetX, offsetY, 2 * sim.getRadius());
    	
    	if(run) {
	    	sim.update();
    	}
    }
    
    public void createNewSim() {
    	int c, p;
    	if(numChords.length() == 0) {
    		c = 0;
    	} else {
    		c = Integer.parseInt(numChords);
    	}
    	
    	if(numParticles.length() == 0) {
    		p = 0;
    	} else {
    		p = Integer.parseInt(numParticles);
    	}
    	
    	System.out.println("\nNew simulation started;\n #Particles: " + p + "\n #Chords:    " + c);
    	sim = new Simulation(height / 2 - 5, c, p);
    }
    
    public void mousePressed() {
    	if(mouseX>20 && mouseX<60 && mouseY>height-60 && mouseY<height-20) {
    		run = !run;
    	} else if(mouseX>80 && mouseX<120 && mouseY>height-60 && mouseY<height-20) {
    		invert = !invert;
    	} else if(mouseX>20 && mouseX<240 && mouseY>80 && mouseY<120) {
    		createNewSim();
    	}
    	
    	if(mouseX>20 && mouseX<120 && mouseY>20 && mouseY<60) {
    		numParticlesEdit = true;
    		numChordsEdit = false;
    	} else if(mouseX>140 && mouseX<240 && mouseY>20 && mouseY<60) {
    		numChordsEdit = true;
    		numParticlesEdit = false;
    	} else {
    		numParticlesEdit = false;
    		numChordsEdit = false;
    	}
    }
    
    public void keyPressed() {
    	if(key=='0'||key=='1'||key=='2'||key=='3'||key=='4'||key=='5'||key=='6'||key=='7'||key=='8'||key=='9') {
	    	if(numParticlesEdit && numParticles.length() < 3) {
	    		if(numParticles.length() == 0) {
	    			// Prevents leading zeros
	    			if(key != '0') {
	    				numParticles += key;
	    			}
	    		} else {
	    			numParticles += key;
	    		}
	    	} else if(numChordsEdit && numChords.length() < 5) {
	    		if(numChords.length() == 0) {
	    			// Prevents leading zeros
	    			if(key != '0') {
	    				numChords += key;
	    			}
	    		} else {
	    			numChords += key;
	    		}
	    	}
    	} else if(keyCode == BACKSPACE) {
    		if(numParticlesEdit && numParticles.length() > 0) {
    			if(numParticles.length() == 1) {
    				numParticles = "";
    			} else {
    				numParticles = numParticles.substring(0,numParticles.length()-1);
    			}
    		} else if(numChordsEdit && numChords.length() > 0) {
    			if(numChords.length() == 1) {
    				numChords = "";
    			} else {
    				numChords = numChords.substring(0,numChords.length()-1);
    			}
    		}
    	} else if(key == ' ') {
    		run = !run;
    	} else if(key == 'i' || key == 'I') {
    		invert = !invert;
    	} else if(key == 'n' || key == 'N') {
    		createNewSim();
    	}
    }
}