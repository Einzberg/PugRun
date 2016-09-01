package OurGame;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Enemy {
Image img;
int x,y;
boolean isAlive;

	public Enemy(int startX, int startY, ImageIcon enemy){
		x = startX;
		y = startY;
		ImageIcon newEnemy = enemy;
		img = newEnemy.getImage();
		isAlive = true;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public boolean isAlive(){
		return isAlive;
	}
	public Image getImg(){
		return img;
	}
	
	public void move(int dx){
		x = x - dx;
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x,y, 40, 36);
	}
}
