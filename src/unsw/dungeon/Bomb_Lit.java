package unsw.dungeon;

import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * A class that implements the lit bomb entity described in spec
 * Lasts 8 ticks of game movement and explodes, killing surrounding entities
 */
public class Bomb_Lit extends EntityBlocking implements GameTickSubscriber {

    private IntegerProperty timeLeft;
    private Dungeon dungeon;

    public Bomb_Lit(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.timeLeft = new SimpleIntegerProperty(8);
        this.dungeon = dungeon;
    }

    public IntegerProperty timeLeft() {
    	return this.timeLeft;
    }
    
    public int getTimeLeft() {
    	return this.timeLeft.get();
    }
    
    public void setTimeLeft(int x) {
    	this.timeLeft.set(x);
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
    	// Visibility is controlled by the controller
    	int time = getTimeLeft();
    	time--;
        setTimeLeft(time);
        if (time == 0) {
            collideSurrounding();
            dungeon.removeEntity(this);
        }
    }

}