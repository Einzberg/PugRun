package tests;

import static org.junit.Assert.*;

import javax.swing.ImageIcon;

import org.junit.Before;
import org.junit.Test;

import OurGame.Board;
import OurGame.Enemy;
import OurGame.Bullet;

public class BoardTest {
	Board board;


	@Before
	public void testSetUp(){
		board = new Board();
	}

	@Test
	public void testEnemiesArePopulated(){
		assertEquals(30, board.enemies.size());		
	}

	@Test
	public void testThatBoardExistsAndGameStarts(){
		assertFalse(board.lost);
	}

	@Test
	public void testThereExistBullets(){
		board.fire();
		board.fire();
		board.fire();
		assertEquals(3, board.bullets.size());
	}

	@Test
	public void testTwoFiresDecreaseAmmo(){
		board.fire();
		board.fire();
		assertEquals(1, board.ammo);
	}

	@Test
	public void testGameEndsWhenCollidingWithEnemy(){
		ImageIcon enemy1 = new ImageIcon(this.getClass().getResource("/Images/enemy.gif"));
		Enemy emi = new Enemy(75, 272, enemy1);
		board.enemies.add(emi);
		board.checkCollisions();
		assertTrue(board.lost);
	}

	@Test
	public void testThatBulletsKillsEnemy(){
		ImageIcon enemy1 = new ImageIcon(this.getClass().getResource("/Images/enemy.gif"));
		Enemy emi = new Enemy(175, 272, enemy1);
		board.enemies.add(emi);
		Bullet z = new Bullet(175, 272);
		board.bullets.add(z);
		board.checkCollisions();
		assertEquals(30, board.enemies.size());
	}

	@Test
	public void testThatJumpingOverLowEnemyGivesPlus1(){
		ImageIcon enemy1 = new ImageIcon(this.getClass().getResource("/Images/enemy.gif"));
		Enemy emi = new Enemy(75, 272, enemy1);
		board.enemies.add(emi);
		board.yValueOfDude = 100;
		board.calculatePoints();
		assertEquals(1, board.getPoints());
	}

	@Test
	public void testThatJumpingOverHighEnemyGivesPlus5(){
		ImageIcon enemy1 = new ImageIcon(this.getClass().getResource("/Images/enemy.gif"));
		Enemy emi = new Enemy(75, 200, enemy1);
		board.enemies.add(emi);
		board.yValueOfDude = 100;
		board.calculatePoints();
		assertEquals(5, board.getPoints());
	}
}
