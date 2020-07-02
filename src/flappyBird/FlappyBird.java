package flappyBird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;



//ActionListener is an interface

public class FlappyBird implements ActionListener,MouseListener{
	private int width = 800;
	private int height = 800;
	
	//defines the bird object
	public static FlappyBird flappyBird;
	
	public Renderer renderer;
	
	//Make bird
	private Rectangle bird;
	
	
	//<> declares type of objects in array
	private ArrayList<Rectangle> columns;
	
	private Random random = new Random();
	
	private int ticks;
	private int yMotion;
	
	private boolean gameOver;
	private boolean started;
	private int score;

	
	
	
	public FlappyBird() {
		JFrame window = new JFrame();
		
		//Timer takes in the parameters integer and action
		Timer timer = new Timer(20,this);
		
		//Makes it no longer a null object
		renderer = new Renderer();

		
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(width, height);
		window.setVisible(true);
		window.addMouseListener(this);
		window.setResizable(false);
		window.setTitle("Flappy Bird");
		window.add(renderer);
		
		
		bird = new Rectangle(width/2-10,height/2-10,20,20);
		columns = new ArrayList<Rectangle>();
		
		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);
		
		timer.start();
			
	}
	
	public void jump() {
		if(gameOver) {
			bird = new Rectangle(width/2-10,height/2-10,20,20);
			columns.clear();
			yMotion= 0;
			score = 0;
			
			
			
			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);
			
			gameOver=false;
			
		}
		if(!started) {
			started = true;
		}
		else if(!gameOver){
			if(yMotion>0) {
				yMotion =0;
			}
			else {
				yMotion-=10;
			}
		}
	}
	
	public void addColumn(boolean start) {
		int space = 300;
		int width = 100;
		int height = 50 + random.nextInt(300);
		
		if(start) {
			columns.add(new Rectangle(this.width+width+columns.size()*300,this.height-height-120,width,height));
			columns.add(new Rectangle(this.width+width+(columns.size()-1)*300,0,width,this.height-height-space));
		}
		else {
			columns.add(new Rectangle(columns.get(columns.size()-1).x+600,this.height-height-120,width,height));
			columns.add(new Rectangle(columns.get(columns.size()-1).x,0,width,this.height-height-space));
		}
		

		
	}
	
	public void paintColumn(Graphics g, Rectangle column) {
		g.setColor(Color.green.darker());
		g.fillRect(column.x, column.y, column.width, column.height);
		
	}
	
	public void repaint(Graphics g) {
		//Make the sky
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, width, height);
		//Makes the bird object
		g.setColor(Color.RED);
		g.fillRect(bird.x, bird.y, bird.width, bird.height);
		//makes the ground(dirt)
		g.setColor(Color.orange);
		g.fillRect(0, height-120, width, 120);
		//Makes the grass
		g.setColor(Color.green);
		g.fillRect(0, height-120, width, 20);
		
		//paint columns
		for(Rectangle column: columns) {
			paintColumn(g,column);
		}
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial",1,100));
		
		if(!started) {
			g.drawString("Click to Start", 100, this.height/2 -50);
		}
		
		if(gameOver) {
			g.drawString("Game Over", 100, this.height/2 -50);
		}
		
	}


	public static void main(String[] args) {

		flappyBird = new FlappyBird();
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		int speed = 10;
		
		ticks++;
		
		if(started) { 
			
			for(int i =0; i<columns.size();i++) {
				Rectangle column = columns.get(i);
				column.x -= speed;
				Thread.yield();
			}
			
			if(ticks%2==0 && yMotion <15) {
				yMotion+=2;
			}
			for(int i =0; i<columns.size();i++) {
				Rectangle column = columns.get(i);
				
				if (column.x+column.width <0) {
					columns.remove(column);
					if(column.y== 0) {
						addColumn(false);
					}
					
				}
				Thread.yield();
			}
			
			bird.y+=yMotion;
			for(Rectangle column: columns) {
				if(column.intersects(bird)) {
					gameOver = true;
					
					bird.x = column.x -column.width;
				}
			}
			if(bird.y>this.height-120||bird.y<0) {

				gameOver = true;
			}
			if(gameOver) {
				bird.y = this.height -120-bird.height;
			}
			
		}

		renderer.repaint();
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		jump();
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}


}
