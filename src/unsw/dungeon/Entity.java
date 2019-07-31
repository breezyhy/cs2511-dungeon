package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * An entity in the dungeon.
 * 
 * @author Robert Clifton-Everest
 *
 */
public abstract class Entity {

    // IntegerProperty is used so that changes to the entities position can be
    // externally observed.
    private IntegerProperty x, y;
    private BooleanProperty visible;
    
    /**
     * Create an entity positioned in square (x,y)
     * 
     * @param x
     * @param y
     */
    public Entity(int x, int y) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.visible = new SimpleBooleanProperty(true);
    }

    public IntegerProperty x() {
        return x;
    }

    public IntegerProperty y() {
        return y;
    }
    
    public BooleanProperty visible() {
    	return visible;
    }
    
    public void setVisibility(boolean visibility) {
    	this.visible.set(visibility);
    }

    public int getY() {
        return y().get();
    }

    public int getX() {
        return x().get();
    }

    public boolean resolveCollision(EntityConsumable obj) {
        return false;
    }

    public boolean resolveCollision(EntityBlocking obj) {
        return false;
    }

    public boolean resolveCollision(EntitySemiblocking obj) {
        return false;
    }

    public boolean resolveCollision(EntityNonblocking obj) {
        return true;
    }

    public boolean resolveCollision(EntityMoveable obj) {
        return false;
    }
}
