package _08_LeagueSnake;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import processing.core.PApplet;

public class LeagueSnake extends PApplet {
    static final int WIDTH = 800;
    static final int HEIGHT = 800;
    
    /*
     * Game variables
     * 
     * Put all the game variables here.
     */
    Segment segment;
    int foodX;
    int foodY;
    int direction = UP;
    int pieces;
    ArrayList<Segment> snakeTail = new ArrayList<>();

    
    /*
     * Setup methods
     * 
     * These methods are called at the start of the game.
     */
    @Override
    public void settings() {
        size(500,500);
    }

    @Override
    public void setup() {
        segment = new Segment(250,250);
        frameRate(20);
        dropFood();
    }

    void dropFood() {
        // Set the food in a new random location
    	foodX = ((int)random(50)*10);
    	foodY = ((int)random(50)*10);
    }

    /*
     * Draw Methods
     * 
     * These methods are used to draw the snake and its food
     */

    @Override
    public void draw() {
        background(101,205,111);
        drawFood();
        move();
        drawSnake();
        eat();
    }

    void drawFood() {
        // Draw the food
    	fill(160,15,247);
        rect(foodX, foodY, 10, 10);
    }

    void drawSnake() {
        // Draw the head of the snake followed by its tail
    	fill(220,21,21);
    	rect(segment.x, segment.y, 20, 20);
    	manageTail();
    }

    void drawTail() {
        // Draw each segment of the tail
        for (int i = 0; i < snakeTail.size(); i++) {
        	fill(220,21,21);
        	rect(snakeTail.get(i).x, snakeTail.get(i).y, 20, 20);
        }
    }

    /*
     * Tail Management methods
     * 
     * These methods make sure the tail is the correct length.
     */

    void manageTail() {
        // After drawing the tail, add a new segment at the "start" of the tail and
        // remove the one at the "end"
        // This produces the illusion of the snake tail moving.
    	checkTailCollision();
    	drawTail();
    	Segment newS = new Segment(segment.x, segment.y);
    	snakeTail.add(newS);
    	snakeTail.remove(0);
    }

    void checkTailCollision() {
        // If the snake crosses its own tail, shrink the tail back to one segment
	    	for (int i = 0; i < snakeTail.size(); i++) {
	        	if (segment.x == snakeTail.get(i).x && segment.y == snakeTail.get(i).y) {
	        		pieces = 1;
	        		snakeTail = new ArrayList<>();
	        		Segment newS = new Segment(segment.x, segment.y);
	            	snakeTail.add(newS);
	            
	        	}
	        }
    	}


    /*
     * Control methods
     * 
     * These methods are used to change what is happening to the snake
     */

    @Override
    public void keyPressed() {
        // Set the direction of the snake according to the arrow keys pressed
        if (keyCode == KeyEvent.VK_UP) {
        	if (direction != DOWN) {
        		direction = UP;
        	}
        }
        else if (keyCode == KeyEvent.VK_DOWN) {
        	if (direction != UP) {
        		direction = DOWN;
        	}
        }
        else if (keyCode == KeyEvent.VK_LEFT) {
        	if (direction != RIGHT) {
        		direction = LEFT;
        	}
        }
        else if (keyCode == KeyEvent.VK_RIGHT) {
        	if (direction != LEFT) {
        		direction = RIGHT;
        	}
        }
    }

    void move() {
        // Change the location of the Snake head based on the direction it is moving.

        
        if (direction == UP) {
            // Move head up
        	segment.y -= 10;
            
        } else if (direction == DOWN) {
            // Move head down
        	segment.y += 10;
                
        } else if (direction == LEFT) {
        	segment.x -= 10;
            
        } else if (direction == RIGHT) {
            segment.x += 10;
        }
        checkBoundaries();
    }

    void checkBoundaries() {
        // If the snake leaves the frame, make it reappear on the other side
        if (segment.x >= 500) {
        	segment.x = 0;
        }
        else if (segment.x <= 0) {
        	segment.x = 500;
        }
        if (segment.y >= 500) {
        	segment.y = 0;
        }
        else if (segment.y <= 0) {
        	segment.y = 500;
        }
    }

    void eat() {
        // When the snake eats the food, its tail should grow and more
        // food appear
        if (foodX >= segment.x && foodX <= segment.x+20 && foodY >= segment.y && foodY <= segment.y+20) {
        	pieces++;
        	dropFood();
        	Segment newS = new Segment(segment.x, segment.y);
        	snakeTail.add(newS);
        }
    }

    static public void main(String[] passedArgs) {
        PApplet.main(LeagueSnake.class.getName());
    }
}
