package OurGame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Dude {
	int x, y, dy, nx2, nx;
	int dx = 5;
	Image still;
	int ammo = 10;

	ImageIcon s = new ImageIcon("C:/Users/MaxBlue/Desktop/pugCop2.gif");
	ImageIcon j = new ImageIcon("C:/Users/MaxBlue/Desktop/pugfart.png");



	public Dude() {
		x = 75;
	//	y = 1500;
		still = s.getImage();
		nx2 = 640;
		nx = 0;

		ammo = 10;
	}



	public void move() {
		x = x + dx;
		//  y = y + dy;
		nx2 = nx2 + dx;
		nx = nx + dx;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getdx() {
		return dx;
	}

	public int getdy() {
		return dy;
	}

	public int getnx() {
		return nx;
	}

	public int getnx2() {
		return nx2;
	}

	public Image getImage() {
		return still;
	}

	public Image getJumpImage() {
		return j.getImage();
	}

	public Rectangle getBounds(){
		return new Rectangle(75,y, 90, 73);
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_UP)
		{
			dy = 1;
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_UP)
		{dy = 0;
		}
	}
}