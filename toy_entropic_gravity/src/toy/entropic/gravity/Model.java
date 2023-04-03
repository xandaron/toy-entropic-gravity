package toy.entropic.gravity;
import processing.core.*;

public class Model extends PApplet{
	
	Simulation sim;
	float offsetX, offsetY;
	int distances = 20;
	int iterations = 50;
	float modelRadius = 1000f;
	float scale;
	boolean complete = false;
	int runs = 1;
	String[] data = new String[distances];
	int pointer = 0;
	Button[] buttons = new Button[5];
	InputBox[] inputBoxs = new InputBox[2];
	FileHandler fileHandler = new FileHandler();
	boolean graphics = true;
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
        // Set high so it will run as fast as possible
        frameRate(1000000);
        strokeWeight(1);
        
        fileHandler.newFile();
        sim = new Simulation(this, modelRadius, 0, 1);
    	scale = (height/2-5)/modelRadius;
        offsetX = width - 5 - (sim.getRadius() * scale);
        offsetY = height / 2;
        
    	textSize(20);
    	
    	// Buttons
        // Start/Stop
        buttons[0] = new Button(20, height - 60, 40, 40, color(0, 200, 0), color(200, 0 ,0));
        // Colour invert
        buttons[1] = new Button(80, height - 60, 40, 40, color(255), color(0));
        // Generate
        buttons[2] = new Button(20, 80, 220, 40, color(73, 164, 230), "Generate");
        // Save
        buttons[3] = new Button(20, 130, 220, 40, color(255), "Save");
        // Line uniformity test
        buttons[4] = new Button(20, height - 120, 100, 40, color(138, 233, 255), "Test");
        
        // Input fields
        // Particles
        inputBoxs[0] = new InputBox(20,20,100,40,"#Iterations",3);
        // Chords
        inputBoxs[1] = new InputBox(140,20,100,40,"#Chords",1000);
    }
    
    public void draw(){
    	background(255);
    	stroke(0);
    	
    	if(buttons[2].state) {
    		fileHandler.newFile();
    		runs = 1;
    		createNewSim();
    		buttons[2].toggleState();
    	}
    	if(buttons[3].state) {
    		fileHandler.saveData();
    		fileHandler.newFile();
    		buttons[3].toggleState();
    	}
    	if(buttons[4].state) {
    		fileHandler.newFile();
    		runs = 1;
    		test();
    		buttons[4].toggleState();
    	}
    	
    	for(Button b : buttons) {
    		fill(b.getColour());
    		rect(b.x, b.y, b.width, b.height);
    		if(b.text != "") {
    			fill(0);
    	    	textAlign(CENTER, CENTER);
    	    	text(b.text,b.x,b.y,b.width,b.height);
    		}
    	}
    	
    	for(InputBox b : inputBoxs) {
        	if(b.state) { stroke(222, 197, 7); } else { stroke(0); }
        	fill(250);
        	rect(b.x,b.y,b.width,b.height);
        	
        	textAlign(LEFT,CENTER);
        	if(b.value == "") {
        		fill(100);
        		text(b.text,b.x+5,b.y,b.width,b.height);
        	} else {
        		fill(0);
        		text(b.value,b.x+5,b.y,b.width,b.height);
        	}
    	}
    	
    	if(buttons[1].state) {
    		fill(0);
    	} else {
    		fill(255);
    	}
    	stroke(0);
    	strokeWeight(1);
    	circle(offsetX, offsetY, 2 * sim.getRadius() * scale);
    	
    	if(graphics) {
	    	for(Chord c : sim.getChords()) {
	    		if(c.isExcluded()) {
	    			stroke(150);
	    		} else {
	    			if(buttons[1].state) {
	    				stroke(255);
	    			} else {
	    				stroke(0);
	    			}
	    		}
	    		line((float) (offsetX + c.getX1() * scale), (float) (offsetY + c.getY1() * scale),
	    			 (float) (offsetX + c.getX2() * scale), (float) (offsetY + c.getY2() * scale));
	    	}
    	}
    	for(Particle p : sim.getParticles()) {
    		noFill();
    		strokeWeight(1);
    		stroke(255,0,0);
    		circle(offsetX + p.getX() * scale, offsetY + p.getY() * scale, (float) (p.getR() * 2 * scale));
    	}
    	
    	
    	noFill();
    	stroke(0);
    	strokeWeight(1);
    	circle(offsetX, offsetY, (float) (2 * sim.getRadius() * scale));
    	
    	if(buttons[0].state) {
    		if(!sim.complete) {
    			sim.update();
    		} else if (iterations!=runs) {
    			System.out.println(runs+"/"+iterations);
    			runs++;
    			createNewSim();
    			if(iterations == runs) { complete = true; }
    		} else if (complete) {
    			System.out.println(runs+"/"+iterations);
    			fileHandler.saveData();
    			complete = false;
    		}
    	}
    }
    
    public void createNewSim() {
    	int c;
    	if(inputBoxs[0].value.length() == 0) {
    		iterations = 1;
    	} else {
    		iterations = inputBoxs[0].getValue();
    	}
    	if(inputBoxs[1].value.length() == 0) {
    		c = 0;
    	} else {
    		c = inputBoxs[1].getValue();
    		if(c > 1000) { graphics = false; } else { graphics = true; }
    	}
    	
    	System.out.println("\nNew simulation started;\n #Iterations: " + iterations + "\n #Chords:     " + c);
    	sim = new Simulation(this, modelRadius, c, distances);
    }
    
    public void test() {
    	int c;
    	if(inputBoxs[0].value.length() == 0) {
    		iterations = 1;
    	} else {
    		iterations = inputBoxs[0].getValue();
    	}
    	if(inputBoxs[1].value.length() == 0) {
    		c = 0;
    	} else {
    		c = inputBoxs[1].getValue();
    		if(c > 1000) { graphics = false; } else { graphics = true; }
    	}
    	System.out.println("Method 1");
    	new BertrandMethodTest(this, 1, modelRadius, c, distances, iterations).methodTest();
    	fileHandler.saveData();
    	fileHandler.newFile();
    	System.out.println("Method 2");
    	new BertrandMethodTest(this, 2, modelRadius, c, distances, iterations).methodTest();
    	fileHandler.saveData();
    	fileHandler.newFile();
    }
    
    public void addData(String[] d) {
    	fileHandler.addDataLine(d);
    }
    
    public void mousePressed() {
    	for(Button b : buttons) {
    		if(mouseX>b.x && mouseX<b.x+b.width && mouseY>b.y && mouseY<b.y+b.height) {
        		b.toggleState();
        	}
    	}
    	
    	for(InputBox b : inputBoxs) {
    		if(mouseX>b.x && mouseX<b.x+b.width && mouseY>b.y && mouseY<b.y+b.height) {
    			b.setState(true);
    		} else {
    			b.setState(false);
    		}
    	}
    }
    
    public void keyPressed() {
    	if(Character.isDigit(key)) {
    		for(InputBox b : inputBoxs) {
    			if(b.state && b.value.length() < b.maxLength) {
    				b.appendValue(key);
    			}
    		}
    	} else if(keyCode == BACKSPACE) {
    		for(InputBox b : inputBoxs) {
    			if(b.state) {
    				b.popValue();
    			}
    		}
    	} else if(key == ' ') {
    		buttons[1].state = !buttons[1].state;
    	} else if(key == 'i' || key == 'I') {
    		buttons[1].state = !buttons[1].state;
    	} else if(key == 'n' || key == 'N') {
    		createNewSim();
    	}
    }
}