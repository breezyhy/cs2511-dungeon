package unsw.dungeon;
/**
 * Implements the EntityMoveable abstract class, used with entities in the dungeon that are moveable
 */
public abstract class EntityMoveable extends Entity {

    private Dungeon dungeon = null;
    private boolean alive;

    public EntityMoveable(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.alive = true;
    }

    public Dungeon dungeon() {
        return this.dungeon;
    }

    public void die() {
        // Placeholder of dead moveable
    	this.setVisibility(false);
    	dungeon.removeEntity(this);
        //x().set(dungeon.getWidth() + 1);
        //y().set(dungeon.getHeight() + 1);
        this.alive = false;
    }

    public boolean alive() {
        return this.alive;
    }
    
    public void setAlive(boolean alive) {
    	this.alive = alive;
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