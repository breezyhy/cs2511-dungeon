package unsw.dungeon;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

class DungeonApplicationTest {

	@Test
	void test1() {
		// Collision method between player enemy and sword / potion
		// Game ticker is not enabled for testing purpose
		// Backpack is hidden but exists in player
		Dungeon dungeon = new Dungeon(3, 2);
		Player q1 = new Player(dungeon, 0, 0);
		Player q2 = new Player(dungeon, 1, 0);
		Player q3 = new Player(dungeon, 2, 0);
		dungeon.addEntity(q1);
		dungeon.addEntity(q2);
		dungeon.addEntity(q3);
		Sword sword1 = new Sword(0,0);
		Sword sword2 = new Sword(2,0);
		Potion potion = new Potion(2,0);
		dungeon.addEntity(sword1);
		dungeon.addEntity(sword2);
		dungeon.addEntity(potion);
		q1.collide(0, 0);
		q3.collide(2, 0);
		Enemy e1 = new Enemy(dungeon, 0, 1);
		Enemy e2 = new Enemy(dungeon, 1, 1);
		Enemy e3 = new Enemy(dungeon, 2, 1);
		Enemy e4 = new Enemy(dungeon, 2, 1);
		Enemy e5 = new Enemy(dungeon, 2, 1);
		Enemy e6 = new Enemy(dungeon, 2, 1);
		Enemy e7 = new Enemy(dungeon, 2, 1);
		Enemy e8 = new Enemy(dungeon, 2, 1);
		
		dungeon.addEntity(e1);
		dungeon.addEntity(e2);
		dungeon.addEntity(e3);
		dungeon.addEntity(e4);
		dungeon.addEntity(e5);
		dungeon.addEntity(e6);
		dungeon.addEntity(e7);
		dungeon.addEntity(e8);
		q1.moveDown();
		q2.moveDown();
		q3.moveDown();
		assertEquals(q1.alive(), true);
		assertEquals(q2.alive(), false);
		assertEquals(q3.alive(), true);
		assertEquals(e1.alive(), false);
		assertEquals(e2.alive(), true);
		assertEquals(e3.alive(), false);
		
		// Sword should exist and still has 5 charges
		assertTrue(sword2.getBackpack() != null);
	}
	
	
	@Test
	void test2() {
		// Collision method between player-boulder-enemy or player-boulder-consumable
		Dungeon dungeon = new Dungeon(3, 1);
		Player q1 = new Player(dungeon, 0, 0);
		dungeon.addEntity(q1);
		Boulder b1 = new Boulder(dungeon, 1, 0);
		dungeon.addEntity(b1);
		Enemy e1 = new Enemy(dungeon, 2, 0);
		dungeon.addEntity(e1);
		q1.moveRight();
		assertTrue(q1.getX() == 0);
		assertTrue(b1.getX() == 1);
		assertTrue(e1.getX() == 2);

		Dungeon dungeon2 = new Dungeon(3, 1);
		Player q2 = new Player(dungeon2, 0, 0);
		dungeon2.addEntity(q2);
		Boulder b2 = new Boulder(dungeon2, 1, 0);
		dungeon2.addEntity(b2);
		Bomb_Unlit r1 = new Bomb_Unlit(2, 0);
		dungeon2.addEntity(r1);
		q2.moveRight();
		assertTrue(q2.getX() == 0);
		assertTrue(b2.getX() == 1);
		assertTrue(r1.getX() == 2);
	}
	
	@Test
	void test3() {
		// Collision method between player enemy and sword
		// Game ticker is not enabled for testing purpose
		Dungeon dungeon = new Dungeon(3, 2);
		// Player 1 and 2 gets both potion, but 2 expires before moving down
		// Player 3 has potion on enemy cell
		Player q1 = new Player(dungeon, 0, 0);
		Player q2 = new Player(dungeon, 1, 0);
		Player q3 = new Player(dungeon, 1, 0);
		dungeon.addEntity(q1);
		dungeon.addEntity(q2);
		dungeon.addEntity(q3);
		Potion p1 = new Potion(0, 0);
		Potion p2 = new Potion(1, 0);
		Potion p3 = new Potion(2, 1);
		dungeon.addEntity(p1);
		dungeon.addEntity(p2);
		dungeon.addEntity(p3);
		q1.collide(0, 0);
		q2.collide(1, 0);
		Enemy e1 = new Enemy(dungeon, 0, 1);
		Enemy e2 = new Enemy(dungeon, 1, 1);
		Enemy e3 = new Enemy(dungeon, 2, 1);
		dungeon.addEntity(e1);
		dungeon.addEntity(e2);
		dungeon.addEntity(e3);
		// Make the first potion to expire
		// after 10 tick, the enemy should die
		for (int i = 0; i < 10; i++) {
			p2.trigger();
		}
		q1.moveDown();
		q2.moveDown();
		q3.moveDown();
		assertEquals(q1.alive(), true);
		assertEquals(q2.alive(), false);
		assertEquals(q3.alive(), false);
		assertEquals(e1.alive(), false);
		assertEquals(e2.alive(), true);
		assertEquals(e3.alive(), true);
	}
	
	@Test
	void test4() {
		// Boulder-switch move test
		// Also switch goal test
		Dungeon dungeon = new Dungeon(4, 2);
		Player q1 = new Player(dungeon, 0, 0);
		Player q2 = new Player(dungeon, 0, 1);
		Boulder b1 = new Boulder(dungeon, 1, 0);
		Boulder b2 = new Boulder(dungeon, 1, 1);
		FloorSwitch s1 = new FloorSwitch(2, 0);
		FloorSwitch s2 = new FloorSwitch(2, 1);
		dungeon.addEntity(q1);
		dungeon.addEntity(q2);
		dungeon.addEntity(b1);
		dungeon.addEntity(b2);
		dungeon.addEntity(s1);
		dungeon.addEntity(s2);
		dungeon.attachBoulderObservers();
		dungeon.updateAllBoulders();
		
		FloorSwitchGoal goal = new FloorSwitchGoal("boulders");
		
		ArrayList<FloorSwitch> fs = dungeon.getFloorSwitch();
        if (fs.size() == 0) throw new UnsupportedOperationException();
        // Deal with floorswitches and goal
        for (FloorSwitch flSwitch : fs){
            flSwitch.registerObserver(goal);
        }
        
		q1.moveRight();
		q2.moveRight();
		assertEquals(s1.showState(), true);
		assertEquals(s2.showState(), true);
		assertEquals(goal.achieved(), true);
		q2.moveRight();
		assertEquals(s2.showState(), false);
		assertEquals(goal.achieved(), false);
	}
	
	@Test
	void test6() {
		// Player-door-key with blocking-semiblocking interactions test
		// Included the key-backpack interaction
		Dungeon dungeon = new Dungeon(4, 3);
		Player q1 = new Player(dungeon, 0, 0);
		Player q2 = new Player(dungeon, 0, 1);
		Player q3 = new Player(dungeon, 0, 2);
		Door d1 = new Door(2, 0, 1);
		Door d2 = new Door(2, 1, 2);
		Door d3 = new Door(2, 2, 3);
		Boulder b1 = new Boulder(dungeon, 1, 2);
		Key x1a = new Key(0, 0, 1);
		Key x1b = new Key(1, 0, 1);
		Key x3 = new Key(0, 2, 3);
		dungeon.addEntity(q1);
		dungeon.addEntity(q2);
		dungeon.addEntity(q3);
		dungeon.addEntity(d1);
		dungeon.addEntity(d2);
		dungeon.addEntity(d3);
		dungeon.addEntity(b1);
		dungeon.addEntity(x1a);
		dungeon.addEntity(x1b);
		dungeon.addEntity(x3);
		q1.collide(0, 0);
		q3.collide(0, 2);
		for (int i = 0; i < 5; i++) {
			q1.moveRight();
			q2.moveRight();
			q3.moveRight();
		}
		// Player 1 should be stuck on the edge
		assertTrue(q1.getX() == 3);
		// Player 2 should be stuck on the left side of the door
		assertTrue(q2.getX() == 1);
		// Player 3 should be stuck on the left side of the boulder
		assertTrue(q3.getX() == 0);
		// Key x1b should have no backpack (not taken by the player)
		assertTrue(x1b.getBackpack() == null);
	}
	
	@Test
	void test7() {
		// Simple wall collision method
		// Also treasure collected goal test
		Dungeon dungeon = new Dungeon(4, 1);
		Player q1 = new Player(dungeon, 0, 0);
		Wall w1 = new Wall(3, 0);
		Treasure t1 = new Treasure(1, 0);
		Treasure t2 = new Treasure(2, 0);
		dungeon.addEntity(q1);
		dungeon.addEntity(w1);
		dungeon.addEntity(t1);
		dungeon.addEntity(t2);
		
		TreasureGoal goal = new TreasureGoal("treasure");
		
		ArrayList<Treasure> treasures = dungeon.getTreasures();
        if (treasures.size() == 0) throw new UnsupportedOperationException();
        // Deal with treasures and goal
        for (Treasure t : treasures){
            t.registerObserver(goal);
        }
        
        q1.moveRight();
        assertTrue(t1.getBackpack() != null);
        assertTrue(t2.getBackpack() == null);
        assertTrue(t1.collected() == true);
        assertTrue(t2.collected() == false);
        assertEquals(goal.achieved(), false);
        q1.moveRight();
        assertTrue(t1.collected() == true);
        assertTrue(t2.collected() == true);
        assertEquals(goal.achieved(), true);
        
        for (int i = 0; i < 5; i++) {
        	q1.moveRight();
        }
        
        assertTrue(q1.getX() == 2);
		
	}
	
	@Test
	void test8() {
		// Simple exit achieved test
		Dungeon dungeon = new Dungeon(4, 1);
		Player q1 = new Player(dungeon, 0, 0);
		Exit e = new Exit(3, 0);
		dungeon.addEntity(q1);
		dungeon.addEntity(e);
		ExitGoal goal = new ExitGoal("exit");
		e.registerObserver(goal);
		// Exit has to be attached to player as observer
		q1.registerObserver(e);
		assertTrue(!goal.achieved());
		q1.moveRight();
		assertTrue(!goal.achieved());
		q1.moveRight();
		q1.moveRight();
		assertTrue(goal.achieved());
		q1.moveRight();
		q1.moveRight();
		assertTrue(goal.achieved());
	}
	
	@Test
	void test9() {
		// Lit bomb test
		Dungeon dungeon = new Dungeon(4, 2);
		Player q1 = new Player(dungeon, 0, 0);
		Player q2 = new Player(dungeon, 3, 0);
		Enemy e = new Enemy(dungeon, 1, 1);
		Boulder b = new Boulder(dungeon, 2, 0);
		Bomb_Lit bomb = new Bomb_Lit(dungeon, 1, 0);
		dungeon.addEntity(q1);
		dungeon.addEntity(q2);
		dungeon.addEntity(b);
		dungeon.addEntity(bomb);
		dungeon.addEntity(e);
		bomb.trigger();
		// All entity should be in the same place / still alive
		assertTrue(q1.alive());
		assertTrue(e.alive());
		assertTrue(b.getX() == 2);
		for (int i = 0; i < 9; i++) {
			bomb.trigger();
		}
		// Player is not alive when bomb collides with player directly beside it
		assertTrue(!q1.alive());
		assertTrue(q2.alive());
		// Nor the enemy
		assertTrue(!e.alive());
		// And the boulder is not there anymore
		assertTrue(b.getX() != 2);
	}
	
}