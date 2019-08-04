package unsw.dungeon;
/**
 * Implements the EntityNonblocking abstract class, used with entities in the dungeon that are nonblocking
 */
public abstract class EntityNonblocking extends Entity {

    public EntityNonblocking(int x, int y) {
        super(x, y);
    }

    public boolean resolveCollision(EntityConsumable obj) {
        return true;
    }

    public boolean resolveCollision(EntityBlocking obj) {
        return false;
    }

    public boolean resolveCollision(EntitySemiblocking obj) {
        return true;
    }

    public boolean resolveCollision(EntityNonblocking obj) {
        return true;
    }

    public boolean resolveCollision(EntityMoveable obj) {
        return true;
    }
}