package unsw.dungeon;

public abstract class EntityNonblocking extends Entity {

    public EntityNonblocking(int x, int y) {
        super(x, y);
        // TODO Auto-generated constructor stub
    }

    public boolean resolveCollision(EntityConsumable obj){
        return true;
    }

    public boolean resolveCollision(EntityBlocking obj) {
        return false;
    }

    public boolean resolveCollision(EntitySemiblocking obj){
        return true;
    }

    public boolean resolveCollision(EntityNonblocking obj){
        return true;
    }

    public boolean resolveCollision(EntityMoveable obj){
        return true;
    }
}