package OurGame;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

public class Board extends JPanel implements ActionListener, Runnable {

	public Dude dude;
	public Image img;
	Timer time;
	Thread animator;
	public int yValueOfDude = 272;
	boolean isCurrentlyInJump = false;
	public static boolean lost = false;
	static Font font = new Font("SanSerif", Font.BOLD, 24);
	public int ammo = 3;
	public static ArrayList bullets;
	int bullety;
	public static ArrayList enemies;
	private int points;
	int plusOneInt = 0;
	int plusFiveInt = 0;
	ImageIcon plusOne;
	ImageIcon plusFive;
	boolean isJumpPositionAtTop = false;
	boolean isJumpFinished = false;
	ImageIcon enemy1;
	int instructionsCountdown = 500;

	public int getPoints(){
		return points;
	}

	public void fire(){
		if (ammo > 0){
			bullety = yValueOfDude + 23;
			Bullet z = new Bullet(75, bullety);
			bullets.add(z);
			ammo--;}
	}

	public Board() {
		dude = new Dude();
		addKeyListener(new AL());
		setFocusable(true);
		ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/Images/test.jpg"));
		plusOne = new ImageIcon(this.getClass().getResource("/Images/+1.png"));
		plusFive = new ImageIcon(this.getClass().getResource("/Images/+5.png"));
		enemy1 = new ImageIcon(this.getClass().getResource("/Images/enemy.gif"));
		img = imageIcon.getImage();
		time = new Timer(5, this);
		time.start();
		enemies = new ArrayList();
		makeEnemies();
		bullets = new ArrayList();
	}

	public void makeEnemies(){
		int enemyStartingPosition = 700;
		for(int i = 0; i < 30; i++){
			Random rand = new Random();
			int flyingOrGroundEnemy = rand.nextInt(2);
			Enemy emi;
			Random randomnum = new Random();
			int offset = randomnum.nextInt(550);
			if(flyingOrGroundEnemy == 0){
				emi = new Enemy(enemyStartingPosition+offset, 272, enemy1);
				enemies.add(emi);
				enemyStartingPosition+=830;}
			else{
				emi = new Enemy(enemyStartingPosition+offset, 200, enemy1);
				enemies.add(emi);
				enemyStartingPosition+=830;}
		}
	}

	public void actionPerformed(ActionEvent e) {
		for(int i =0; i < bullets.size(); i++){
			Bullet bullet = (Bullet) bullets.get(i);
			if (bullet.visible == true)
				bullet.move();
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
			if((currentEnemy.getX() <= 77 && currentEnemy.getX() >= 73) && yValueOfDude < currentEnemy.getY()){
				if(currentEnemy.getY() < 210){
					points += 5;
					plusFiveInt = plusFiveInt + 50;
				}
				else{
					points++;
					plusOneInt = plusOneInt + 50;			
				}	
			}
		}
	}

	public void paint(Graphics g) {
		if(lost) System.exit(0);
		if (isCurrentlyInJump==false && dude.dy == 1)
		{
			isCurrentlyInJump = true;
			animator = new Thread(this);
			animator.start();
		}

		super.paint(g);
		Graphics2D graphics2d = (Graphics2D) g;

		if ((dude.getnx() - 1280) == 0)
			dude.nx = 0;
		if ((dude.getnx2() - 1280) == 0)
			dude.nx2 = 0;
		graphics2d.drawImage(img, 640-dude.nx2, 0, null);
		graphics2d.drawImage(img, 640-dude.nx, 0, null);


		if (yValueOfDude < 272)
			graphics2d.drawImage(dude.getJumpImage(),75, yValueOfDude, null);
		else graphics2d.drawImage(dude.getImage(),75, yValueOfDude, null);

		for(int i =0; i < bullets.size(); i++){
			Bullet bullet = (Bullet) bullets.get(i);
			graphics2d.drawImage(bullet.img, bullet.getX(), bullet.getY(), null);
		}

		graphics2d.setFont(font);
		graphics2d.setColor(Color.BLUE);
		graphics2d.drawString("Ammo left " + ammo, 400, 20);
		graphics2d.setFont(new Font("Book Antiqua", Font.BOLD, 26));
		graphics2d.setColor(Color.RED);
		graphics2d.drawString("Points " + points, 100, 20);
		
		if(instructionsCountdown > 0){
			graphics2d.setColor(Color.GREEN);
			graphics2d.drawString("Press Up Arrow Key To Jump", 250, 150);
			graphics2d.drawString("Press Space To Shoot", 250, 250);
			instructionsCountdown--;
		}

		if(plusOneInt > 0){
			plusOneInt--;
			graphics2d.drawImage(plusOne.getImage(), 175, 272, null);
		}

		if(plusFiveInt > 0){
			plusFiveInt--;
			graphics2d.drawImage(plusFive.getImage(), 175, 200, null);

		}
		for(int i = 0; i < enemies.size(); i++){
			Enemy em = (Enemy) enemies.get(i);
			if(em.isAlive() == true)
				graphics2d.drawImage(em.getImg(), em.getX(), em.getY(), null);
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
		while(isJumpFinished == false){
			if (yValueOfDude <= 272 && secondjump == false) yValueOfDude=yValueOfDude-15;
			if (yValueOfDude <= 20) secondjump = true;
			if (yValueOfDude >= 10) {yValueOfDude=yValueOfDude+5;};
			if (yValueOfDude == 272) isJumpFinished = true;
			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = 10 - timeDiff;
			if (sleep < 0) sleep =  2;
			try{Thread.sleep(sleep);}
			catch(Exception e){}
			beforeTime = System.currentTimeMillis();
		}
		isJumpFinished = false;
		isJumpPositionAtTop = false;
		isCurrentlyInJump = false;
	}

	public void cycle(){
		if (isJumpPositionAtTop == false) 
			if (yValueOfDude == 0) isJumpPositionAtTop = true;
		if (isJumpPositionAtTop == true && yValueOfDude <= 272) yValueOfDude++;
		if (yValueOfDude == 272) isJumpFinished = true;
	}

	public void ekeyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_SPACE)
			fire(); 
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_SPACE)
		{
		}
	}

	public void checkCollisions(){

		for(int a = 0; a < enemies.size(); a++){
			Enemy enemy = (Enemy) enemies.get(a);
			Rectangle enemyRec = enemy.getBounds();
			Rectangle dudeRec = new Rectangle(75, yValueOfDude, 90, 73);
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