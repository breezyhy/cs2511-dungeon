package unsw.dungeon;

public class Witch extends Enemy {

	private MultipleObserver enemyObserver = null;
    
	public Witch(Dungeon dungeon, int x, int y) {
		super(dungeon, x, y);
	}

	@Override
	public boolean resolveCollision(EntityMoveable e) {
		if (e instanceof Enemy)
            return true;
        if (!(e instanceof Player))
            return true;

        // If player is going to die, player will die and return false
        Player p = (Player) e;
        if (!p.isImmune(this) && p.getHP().get() <= 1) {
        	System.out.println("1");
        	p.die();
            return false;
        }else if(!p.isImmune(this) && p.getHP().get() > 1) {
        	System.out.println("2");
        	p.loseHp();
        }else {
	        // Else, this entity will die
	        die();
	        notifyObservers();
        }
        return true;
    }
	
	@Override
    public void trigger() {
        // Witch won't move
    }
}
