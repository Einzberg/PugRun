package OurGame;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Bullet {
	int x,y;
	boolean visible;
	Image img; 
	
	public Bullet(int startX, int startY){
		x = startX;
		y = startY;
		ImageIcon newBullet = new ImageIcon(this.getClass().getResource("/Images/bullet.png"));
		img = newBullet.getImage();
		visible = true;
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x,y, 60, 56);
	}
	
	public void move(){
		x += 5;
		if (x > 640)
			visible = false;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public Image getImg(){
		return img;
	}
}
