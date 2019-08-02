package unsw.dungeon;
/**
 * Class that implements the potion as described in the spec. 
 */
public class Potion extends EntityConsumable implements GameTickSubscriber {

    private int useCount;

    // Backpack is stored here because the potion needs to be detached from backpack
    // once it expires
    public Potion(int x, int y) {
        super(x, y);
        this.useCount = 10;
    }

    /**
     * A player will pick up a potion if they step onto it, other entities will
     * pass over it
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
     * Each tick decrements time, and once it reaches 0 it will call used()
     */
    public void decrementTime() {
        // Do not decrement if there is no backpack
        if (getBackpack() == null)
            return;
        this.useCount--;
        if (useCount < 0) {
            used();
        }
    }
    
    /**
     * @return true if potion is still active
     */
    public boolean stillActive() {
        return (useCount > 0);
    }

    /**
     * Update function called by subject, calls decrementTimer()
     */
    @Override
    public void trigger() {
        decrementTime();
    }

}
