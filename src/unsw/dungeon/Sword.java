package unsw.dungeon;
/**
 * Implements the sword entity that a player is able to pick up and use 
 * Once picked up, has charges that are able to kill players
 */
public class Sword extends EntityConsumable {

    private int useCount;

    public Sword(int x, int y) {
        super(x, y);
        this.useCount = 5;
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
    
    /**
     * Decrements the count of a sword and when it reaches 0
     * removes the sword from players inventory
     * @return
     */
    public boolean useSword() {
        if (this.useCount > 1) {
            this.useCount--;
            return true;
        } else if (this.useCount == 1) {
        	// setStorage(getBackpack().getStoringColumn() + 1, 0);
        	used();
            this.useCount--;
            return true;
        }

        return false;
    }

}
