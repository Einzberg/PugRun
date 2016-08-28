package OurGame;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

public class Board extends JPanel implements ActionListener, Runnable {
	Dude dude;
	public Image img;
	Timer time;
	Thread animator;
	int v = 272;
	boolean k = false;
	boolean lost = false;
	
	static Font font = new Font("SanSerif", Font.BOLD, 24);
	int ammo = 3;
	static ArrayList bullets;
	int bullety;
	static ArrayList enemies;
	int points;
	boolean fiver = false;
	boolean oner = false;
	int plusOneInt = 0;
	int plusFiveInt = 0;
	ImageIcon plusOne;
	ImageIcon plusFive;
	boolean h = false;
	boolean done = false;
	ImageIcon enemy1;

	public void fire(){
		if (ammo > 0){
			bullety = v + 23;
			Bullet z = new Bullet(75, bullety);
			bullets.add(z);
			ammo--;}
	}

	public void ekeyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_SPACE)
		{         fire(); 
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_SPACE)
		{
		}
	}

	public Board() {

		dude = new Dude();
		addKeyListener(new AL());
		setFocusable(true);
		ImageIcon i = new ImageIcon(this.getClass().getResource("/Images/test.jpg"));
		plusOne = new ImageIcon(this.getClass().getResource("/Images/+1.png"));
		plusFive = new ImageIcon(this.getClass().getResource("/Images/+5.png"));
		enemy1 = new ImageIcon(this.getClass().getResource("/Images/enemy.gif"));
		img = i.getImage();
		time = new Timer(5, this);
		time.start();
		enemies = new ArrayList();
		makeEnemies();
		bullets = new ArrayList();
	}

	public void makeEnemies(){
		int x = 700;
		for(int i = 0; i < 30; i++){
			Random rand = new Random();
			int ran = rand.nextInt(2);
			Enemy emi;
			Random randomnum = new Random();
			int ranran = randomnum.nextInt(550);
			if(ran == 0){
				emi = new Enemy(x+ranran, 272, enemy1);
				enemies.add(emi);
				x+=830;}
			else{
				emi = new Enemy(x+ranran, 200, enemy1);
				enemies.add(emi);
				x+=830;}
		}
	}

	public void actionPerformed(ActionEvent e) {
		for(int i =0; i < bullets.size(); i++){
			Bullet m = (Bullet) bullets.get(i);
			if (m.visible == true)
				m.move();
			else
				bullets.remove(i);
		}
		dude.move();
		calculatePoints();
		for(int i = 0; i < enemies.size(); i++){
			Enemy emi = (Enemy) enemies.get(i);
			emi.move(dude.getdx());
		}
		repaint();
	}

	public void calculatePoints(){
		for(int i=0; i < enemies.size(); i++){
			Enemy currentEnemy = (Enemy) enemies.get(i);
			if((currentEnemy.getX() == 75 || currentEnemy.getX() == 74 || currentEnemy.getX() == 76 ||currentEnemy.getX() == 73 ||currentEnemy.getX() == 77) && v < currentEnemy.getY()){

				if(currentEnemy.getY() < 210){
					points += 5;
					fiver = true;
					plusFiveInt = plusFiveInt + 50;
				}
				else{
					points++;
					oner = true;
					plusOneInt = plusOneInt + 50;			}	
			}
		}
	}
	
	public void paint(Graphics g) {
		if(lost) System.exit(0);
		if (k==false && dude.dy == 1)
		{
			k = true;
			animator = new Thread(this);
			animator.start();
		}

		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;

		if ((dude.getnx() - 1280) == 0)
			dude.nx = 0;
		if ((dude.getnx2() - 1280) == 0)
			dude.nx2 = 0;
		g2d.drawImage(img, 640-dude.nx2, 0, null);
		g2d.drawImage(img, 640-dude.nx, 0, null);


		if (v < 272)
			g2d.drawImage(dude.getJumpImage(),75, v, null);
		else g2d.drawImage(dude.getImage(),75, v, null);

		for(int i =0; i < bullets.size(); i++){
			Bullet m = (Bullet) bullets.get(i);
			g2d.drawImage(m.img, m.getX(), m.getY(), null);
		}
		g2d.setFont(font);
		g2d.setColor(Color.BLUE);
		g2d.drawString("Ammo left " + ammo, 400, 20);
		g2d.drawString("Points " + points, 100, 20);

		if(fiver == true){
			g2d.drawString("fiver" + ammo, 100, 20);
			fiver = false;
		}
		if(oner == true){
			g2d.drawString("oner" + ammo, 200, 20);
			oner = false;
		}

		if(plusOneInt > 0){
			plusOneInt--;
			g2d.drawImage(plusOne.getImage(), 175, 272, null);
		}

		if(plusFiveInt > 0){
			plusFiveInt--;
			g2d.drawImage(plusFive.getImage(), 175, 200, null);

		}
		for(int i = 0; i < enemies.size(); i++){
			Enemy em = (Enemy) enemies.get(i);
			if(em.isAlive() == true)
				g2d.drawImage(em.getImg(), em.getX(), em.getY(), null);
		}
		checkCollisions();
	}

	private class AL extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			dude.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			dude.keyPressed(e);
			ekeyPressed(e);
		}
	}

	@Override
	public void run() {
		long beforeTime, timeDiff, sleep;
		beforeTime = System.currentTimeMillis();
		boolean secondjump = false;
		while(done == false){
			if (v <= 272 && secondjump == false) v=v-15;
			if (v <= 20) secondjump = true;
			if (v >= 10) {v=v+5;};
			if (v == 272) done = true;
			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = 10 - timeDiff;
			if (sleep < 0) sleep =  2;
			try{Thread.sleep(sleep);}
			catch(Exception e){}
			beforeTime = System.currentTimeMillis();
		}
		done = false;
		h = false;
		k = false;
	}


	public void cycle(){

		if (h == false) 
			if (v == 0) h = true;
		if (h == true && v <= 272) v++;
		if (v == 272) done = true;
	}

	public void checkCollisions(){
		for(int a = 0; a < enemies.size(); a++){
			Enemy enemy = (Enemy) enemies.get(a);
			Rectangle enemyRec = enemy.getBounds();
			Rectangle dudeRec = new Rectangle(75, v, 90, 73);
			if(dudeRec.intersects(enemyRec) && enemy.isAlive){
				lost = true;
			}
			for(int i = 0; i < bullets.size(); i++){
				Bullet bullet = (Bullet) bullets.get(i);
				Rectangle bulletRec = bullet.getBounds();
				if(bulletRec.intersects(enemyRec) && enemy.isAlive()){
					enemy.isAlive = false;
					enemies.remove(enemy);
					bullet.visible = false;
				}				
			}
		}
	}
}