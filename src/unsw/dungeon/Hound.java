package unsw.dungeon;

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
