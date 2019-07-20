package unsw.dungeon;

import java.util.List;

public class Bomb_Lit extends EntityBlocking implements GameTickSubscriber {

    private int timeLeft = 8;
    private Dungeon dungeon;

    public Bomb_Lit(Dungeon dungeon, int x, int y){
        super(x, y);
        this.timeLeft = 8;
    }

    private void collideSurrounding() {
        List<Entity> colliding = dungeon.getSurroundingEntity(getX(), getY());
        if (colliding == null) return;
        for (Entity f : colliding){
            f.resolveCollision(this);
        }
    }

	@Override
	public void trigger() {
        this.timeLeft--;
        if (this.timeLeft == 0) {
            collideSurrounding();
            // Insert a method to make this disappear
        }
	}

}