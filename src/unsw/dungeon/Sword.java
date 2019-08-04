package unsw.dungeon;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Implements the sword entity that a player is able to pick up and use 
 * Once picked up, has charges that are able to kill players
 */
public class Sword extends EntityConsumable {

    private IntegerProperty useCount;

    public Sword(int x, int y) {
        super(x, y);
        this.useCount = new SimpleIntegerProperty(5);
    }

    /**
     * If the player collides with a sword, it gets picked up
     * Any other entities will pass through sword without picking it up
     */
    @Override
    public boolean resolveCollision(EntityMoveable obj) {
        if (!(obj instanceof Player))
            return true;

        Backpack backpack = ((Player) obj).getBackpack();
        if (backpack.getConsumable(this)) {
            System.out.println("Item disappears");
        }

        return true;
    }
    
    public IntegerProperty useCount() {
    	return this.useCount;
    }
    
    /**
     * Decrements the count of a sword and when it reaches 0
     * removes the sword from players inventory
     * @return
     */
    public boolean useSword() {
    	int usec = this.useCount.get();
        if (usec > 1) {
        	usec--;
        	this.useCount.set(usec);
            return true;
        } else if (usec == 1) {
        	// setStorage(getBackpack().getStoringColumn() + 1, 0);
        	used();
        	usec--;
        	this.useCount.set(usec);
            return true;
        }

        return false;
    }

}
