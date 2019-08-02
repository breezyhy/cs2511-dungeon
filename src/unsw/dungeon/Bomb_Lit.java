package unsw.dungeon;

import java.util.List;

/**
 * A class that implements the lit bomb entity described in spec
 * Lasts 8 ticks of game movement and explodes, killing surrounding entities
 */
public class Bomb_Lit extends EntityBlocking implements GameTickSubscriber {

    private int timeLeft = 8;
    private Dungeon dungeon;

    public Bomb_Lit(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.timeLeft = 8;
        this.dungeon = dungeon;
    }

    /**
     * Once blown up, deals damage to all surrounding entities
     */
    private void collideSurrounding() {
        List<Entity> colliding = dungeon.getSurroundingEntity(getX(), getY());
        System.out.println("Collide with items: " + String.valueOf(colliding.size()));
        if (colliding == null)
            return;
        for (Entity f : colliding) {
            f.resolveCollision(this);
        }
    }
    
    /**
     * Implement the update method from the subscriber list
     */
    @Override
    public void trigger() {
        this.timeLeft--;
        if (this.timeLeft == 0) {
            collideSurrounding();
            this.setVisibility(false);
            dungeon.removeEntity(this);
            // Insert a method to make this disappear
        }
    }

}