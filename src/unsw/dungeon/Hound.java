package unsw.dungeon;
/**
 * An extension of the enemy class that moves twice as fast as a normal enemy
 * @author z5161251
 *
 */
public class Hound extends Enemy {

	private MultipleObserver enemyObserver = null;
    
	public Hound(Dungeon dungeon, int x, int y) {
		super(dungeon, x, y);
	}
	
	@Override
    public void trigger() {
        moveToPlayer();
        moveToPlayer();
    }
}
