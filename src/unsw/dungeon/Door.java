package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Implements the door entity as specified, with a state and an id
 */
public class Door extends EntitySemiblocking {

    private DoorState accessible;
    private BooleanProperty accessibleProperty;
    private int id;

    public Door(int x, int y, int id) {
        super(x, y);
        this.accessible = new Door_Locked();
        this.accessibleProperty = new SimpleBooleanProperty(accessible.showState());
        this.id = id;
    }

    /**
     * When interacted with, the door switches to its next state
     */
    public void switchState() {
        accessible.trigger(this);
        accessibleProperty.set(accessible());
    }
    /**
   	 * Gives the id related to the door/key combo
     * @return id
     */
    public int getId(){
        return this.id;
    }
    
    /**
     * Sets the state of the door as an object
     * @param accessible is door state that it is changing to
     */
    public void setState(DoorState accessible) {
        this.accessible = accessible;
    }
    
    /**
     * @returnn true if door can be passed through
     * @return false if door cannot be passed through
     */
    public boolean accessible() {
        return accessible.showState();
    }
    
    public BooleanProperty accessibleProperty() {
    	return this.accessibleProperty;
    }

    /**
     * Resolves collision with a moveable object. 
     * @return true if door is accesible and is a player, or if player has a key
     * @return false otherwise
     */
    @Override
    public boolean resolveCollision(EntityMoveable obj) {
    	System.out.println(accessible());
    	
        if (!(obj instanceof Player))
            return false;
        if (accessible())
            return true;
        Player player = (Player) obj;
        if (player.resolveCollision(this)) {
            switchState();
            return true;
        }
        return false;
    }
}
