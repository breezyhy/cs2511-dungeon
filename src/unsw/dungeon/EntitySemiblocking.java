package unsw.dungeon;
/**
 * Implements the EntitySemiblocking abstract class, used with entities in the dungeon that are semiblocking
 */
public abstract class EntitySemiblocking extends Entity {

    public EntitySemiblocking(int x, int y) {
        super(x, y);
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
        return false;
    }

    public boolean resolveCollision(EntityMoveable obj) {
        return false;
    }

}