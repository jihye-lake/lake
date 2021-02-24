package game_project_2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

class GraphicObject {
	BufferedImage img = null;
	int x = 0, y = 0;
	

	
	public GraphicObject(String name) {
		try {
			img = ImageIO.read(new File(name));
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(0);
//			e.printStackTrace();
		}
	}
	
	public void update() {
		  
	}
	
	public void draw(Graphics g) {
		g.drawImage(img,  x, y, null);
	}
	
	public void KeyPressed(KeyEvent event) {
		
	}
}

class Missile extends GraphicObject {
	boolean launched = false;
	
	public Missile(String name) {
		super(name);
		y = -100;

	}
	
	public void update() {
		if (launched) 
			y -= 3;
		if (y < -100) 
				launched = false;
			}
			
			public void KeyPressed(KeyEvent event, int x, int y) {
				if (event.getKeyCode() == KeyEvent.VK_SPACE) {
					launched = true;
					this.x = x;
					this.y = y;
				}
			}
		}
		
		class Enemy extends GraphicObject {
			int dx = 10;
			
			public Enemy(String name) {
				super(name);
				x = 500;
				y = 100;
		}
		
		public void update() {
			x += dx;
			if (x < 0)
				dx = +10;
			if (x > 500)
				dx = -10;
		}
	}
		
		class Enemy1 extends GraphicObject {
			int dx = 10;
			
			public Enemy1(String name) {
				super(name);
				x = 300;
				y = -10;
			}
			
			public void update() {
				x += dx;
				if (x < 0)
					dx = +15;
				if (x > 500)
					dx = -15;
			}
		}
		
	class SpaceShip extends GraphicObject {
		public SpaceShip(String name) {
			super(name);
			x = 150;
			y = 350;
		}
			
		public void KeyPressed(KeyEvent event) {
			if (event.getKeyCode() == KeyEvent.VK_LEFT) {
					x -= 10;					
			}
				
			if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
					x += 10;					
			}
				
//			if (event.getKeyCode() == KeyEvent.VK_LEFT) {
//					y -= 10;					
//			}
//				
//			if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
//					y += 10;					
//		}
	}
}
	
	class MyPanel4 extends JPanel implements KeyListener {
		Enemy enemy;
		Enemy1 enemy1;
		SpaceShip spaceship;
		Missile missile;
		
		public MyPanel4() {
			super();
			this.addKeyListener(this);
			this.requestFocus();
			setFocusable(true);
			this.setBackground(new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255)));
			
			enemy1 = new Enemy1("./src/main/img/snack_bite.png");
			enemy = new Enemy("./src/main/img/combat.png");
			spaceship = new SpaceShip("./src/main/img/popcorn11.png");
			missile = new Missile("./src/main/img/popcorn_2.png");
			class MyThread extends Thread {
				public void run() {
					while (true) {
						enemy.update();
						enemy1.update();
						spaceship.update();
						missile.update();
						repaint();
						
						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
//							e.printStackTrace();
						}
						
					}
				}
			}
		Thread t = new MyThread();
		t.start();
		}
		
		public void paint(Graphics g) {
			super.paint(g);
			
			enemy.draw(g);
			enemy1.draw(g);
			spaceship.draw(g);
			missile.draw(g);
		}
		
		public void keyPressed(KeyEvent event) {
			spaceship.KeyPressed(event);
			missile.KeyPressed(event, spaceship.x, spaceship.y);
		}
		
		public void keyReleased(KeyEvent args) {
			
		}
		
		public void keyTyped(KeyEvent args) {
			
		}
	}

public class game_project_2 extends JFrame{
	public game_project_2() {
		setTitle("  내가 제일 맛있는 과자다!!!!!!!! 다 비켜!!!!");
		add(new MyPanel4());
		setSize(600, 550);
		setVisible(true);
		ArrayList Missile_List = new ArrayList();
	}
	
	public static void main(String[] args) {
		new game_project_2();
	}

}