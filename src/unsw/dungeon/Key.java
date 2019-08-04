package unsw.dungeon;

public class Key extends EntityConsumable {

    private int id;

    public Key(int x, int y, int id) {
        super(x, y);
        this.id = id;
    }
    
    /**
     * A player can only carry one key at a time
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
     * Get id of the key. The corresponding door must have the same ID in order to unlock the door
     * @return id of the key
     */
    public int getID() {
    	return this.id;
    }
    
    /**
     * Use key on Door d, if the id of the key and the door matches, and switches the state of the 
     * door
     * @param Door d
     * @return boolean dependent on use of key
     */
    public boolean useKey(Door d) {
        if (getBackpack() == null)
            return false;
        if (d.getId() == this.id) {
        	// setStorage(getBackpack().getStoringColumn() + 1, 0);
            used();
            d.switchState();
            return true;
        }
        return false;
    }
    
    public void drop(int x, int y) {
    	x().set(x);
    	y().set(y);
    }
}
